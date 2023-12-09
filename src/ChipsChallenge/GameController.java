package ChipsChallenge;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameController extends Application {

    @FXML
    public static Stage stage;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Canvas inventoryCanvas;

    @FXML
    private HBox levelNameBox;

    @FXML
    private Label levelNumTxt;

    @FXML
    private Label levelNameTxt;

    @FXML
    private Label chipNumber;

    @FXML
    private Label timeLbl;

    @FXML
    private HBox gameBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button saveReturnBtn;

    private Level currentLevel;

    private Timeline tickTimeline;

    private KeyCode nextMove;

    private ArrayList<Key> inventory; // should probably move this

    private int chipCount = 0;

    private double currentTime;

    public static User currentUser;

    private int currentTick = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        gameStart();
    }

    @FXML
    private void gameStart() {
        testFileLoad();
        levelNumTxt.setText("level " + currentLevel.getLevelNum());
        levelNameTxt.setText(currentLevel.getLevelDesc());
        inventory = new ArrayList<>();
        currentTime = currentLevel.getLevelTime();
        drawGame();
        drawInventory();
        // For this tick timeline to have varying speed we need to store an internal tick count.
        // Each tick will increment the tick count and certain actors will move on specific tick counts
        // Player and Pink Ball will move on tick count 2 -> 500ms
        // Bug will move on tick count 3 -> 750ms
        // Frog will move on tick count 4 -> 1000ms
        // After this the count is reset back to 1.
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(250), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    @FXML
    private void reset() {
        tickTimeline.stop();
        gameStart();
    }

    public void tick() {
        currentTick = currentTick == 4 ? 0 : currentTick+1;
        ArrayList<ActorType> actorsToMove = new ArrayList<>();
        actorsToMove.add(ActorType.PINKBALL);
        if(currentTick % 2 == 0) {
            actorsToMove.add(ActorType.BUG);
        }

        if(currentTick == 4) {
            currentTime = currentTime - 1;
            if(currentTime == 0) {
                try {
                    switchToDeathScreen(new ActionEvent());
                    AfterScreenController.stage = stage;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        movePlayer();
        moveMonsters(actorsToMove);
        // Reset actors to move for next tick
        nextMove = null;
        drawGame();
    }

    @FXML
    public void testMethod() {
        Game.testFunction();
    }

    @FXML
    public void testFileLoad()  {
        int currentLevel = currentUser.getHighestLevelNum() + 1;
        File myFile = new File("src/levels/level" + currentLevel + ".txt");
        Scanner myReader = null;
        try {  // Change this to just throw fileNotFoundException and crash program
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] infoArr = splitFile(myReader ,1)[0].split(",");
        String[][] layers = new String[5][1];
        for(int i=0; i<5; i++) {
            layers[i] = splitFile(myReader, Integer.parseInt(infoArr[1]));
        }
        this.currentLevel = new Level(Integer.parseInt(infoArr[0]), Integer.parseInt(infoArr[1]), Integer.parseInt(infoArr[2]), Integer.parseInt(infoArr[3]), infoArr[4], layers);
    }

    private String[] splitFile(Scanner levelFile, int height) {
        String[] tileArr = new String[height];
        boolean layerFinished = false;
        int i = 0;
        while(!layerFinished) {
            String nextLine = levelFile.nextLine();
            if(!(nextLine.equals(""))) {
                tileArr[i] = nextLine;
                i++;
            } else {
                layerFinished = true;
            }
        }
        return tileArr;
    }

    @FXML
    private void drawGame() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        Tile[][] tileLayerGraphics = currentLevel.getTileLayer().getTiles();
        for(int i=0; i<tileLayerGraphics.length; i++) {
            for(int j=0; j<tileLayerGraphics[i].length; j++) {
                gc.drawImage(tileLayerGraphics[i][j].getImage(), j * 50, i * 50);
            }
        } // Make these for loops into a method
        Item[][] itemLayerGraphics = currentLevel.getItemLayer().getItems(); // Need to make this more efficient, far to slow
        for(int i=0; i<itemLayerGraphics.length; i++) {
            for(int j=0; j<itemLayerGraphics[i].length; j++) {
                Image currentGraphic = itemLayerGraphics[i][j].getImage();
                if(currentGraphic != null) {
                    gc.drawImage(currentGraphic, j * 50, i * 50);
                }
            }
        }
        Actor[][] actorLayerGraphics = currentLevel.getActorLayer().getActors();
        for(int i=0; i<actorLayerGraphics.length; i++) {
            for(int j=0; j<actorLayerGraphics[i].length; j++) {
                Image currentGraphic = actorLayerGraphics[i][j].getImage();
                if(currentGraphic != null) {
                    gc.drawImage(currentGraphic, j * 50, i * 50);
                }
            }
        }
        chipNumber.setText("Chips: " + chipCount);
        timeLbl.setText("Time: " + Math.ceil(currentTime));
    }

    @FXML
    public void storeKeyEvent(KeyEvent event) {
        // We change the behaviour depending on the actual key that was pressed.
        nextMove = event.getCode();
        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    private void movePlayer() {
        Player currentPlayer = currentLevel.getActorLayer().getPlayer();
        if(nextMove == KeyCode.W || nextMove == KeyCode.A
                || nextMove == KeyCode.S || nextMove == KeyCode.D) {
            int[] originalPosition = new int[]{currentPlayer.getX(), currentPlayer.getY()};
            int[] nextPosition = getNewPosition(originalPosition[0], originalPosition[1], nextMove);
            Tile possibleTrap = currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]);
            if(possibleTrap.getType() == TileType.TRAP) {
                Trap foundTrap = (Trap) possibleTrap;
                if (foundTrap.isActive()) {
                    return;
                }
            }
            if(isValidMove(nextPosition)) {
                // Check for tile interactions
                int[] finalPosition = collisionOccurredTile(nextPosition, originalPosition,
                        checkForTileCollision(nextPosition), currentPlayer.getType());
                // Check for actor interaction
                finalPosition = collisionOccuredActor(finalPosition, originalPosition, checkForActorCollision(finalPosition), currentPlayer.getType());
                currentLevel.getActorLayer().updateActor(currentPlayer, finalPosition[0], finalPosition[1]);
                collisionOccurredItem(finalPosition);
                // Move this somewhere nicer later
                TileType lastTileType = currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]).getType();
                if (lastTileType == TileType.DIRT) {
                    currentLevel.getTileLayer().setTileAt(originalPosition[0], originalPosition[1], new Path());
                    // The last tile was a button, and they aren't currently still standing on the button
                } else if (lastTileType == TileType.BUTTON && originalPosition != finalPosition) {
                    ChipsChallenge.Button previousButton = (ChipsChallenge.Button) currentLevel.getTileLayer().
                            getTileAt(originalPosition[0], originalPosition[1]);
                    previousButton.getLinkedTrap().setActive(false);
                }
            }
        }
    }

    private int[] getNewPosition(int x, int y, KeyCode nextMove) {
        switch (nextMove) {
            case D:
                return new int[]{x+1,y};
            case A:
                return new int[]{x-1,y};
            case W:
                return new int[]{x, y-1};
            case S:
                return new int[]{x, y+1};
            default:
                return new int[]{x,y};
        }
    }

    private boolean isValidMove(int[] position) {
        return (position[0] < currentLevel.getWidth() && position[0] >= 0)
                && (position[1] < currentLevel.getHeight() && position[1] >= 0);
    }

    private TileType checkForTileCollision(int[] position) {
        Tile currentTile = currentLevel.getTileLayer().getTileAt(position[0], position[1]);
        return currentTile.getType();
    }

    private ActorType checkForActorCollision(int[] position) {
        Actor currentActor = currentLevel.getActorLayer().getActor(position[0], position[1]);
        return currentActor.getType();
    }

    private boolean onActiveTrap(Actor actor) {
        Tile possibleTrap = currentLevel.getTileLayer().getTileAt(actor.getX(), actor.getY());
        if(possibleTrap.getType() == TileType.TRAP) {
            Trap foundTrap = (Trap) possibleTrap;
            return foundTrap.isActive();
        }
        return false;
    }

    private void moveMonsters(ArrayList<ActorType> movingActors) {
        for(Actor monster: currentLevel.getActorLayer().getMonsters()) {
            if(!onActiveTrap(monster)) {
                if(monster.getType() == ActorType.PINKBALL && movingActors.contains(monster.getType())) {
                    PinkBall currentMonster = (PinkBall) monster;
                    int[] originalPosition = new int[]{currentMonster.getX(), currentMonster.getY()};
                    int[] ballNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(), currentMonster.getDirection());
                    if(isValidMove(ballNextMove)) {
                        int[] finalPosition = collisionOccurredTile(ballNextMove, originalPosition, checkForTileCollision(ballNextMove), currentMonster.getType());
                        finalPosition = collisionOccuredActor(finalPosition, originalPosition, checkForActorCollision(finalPosition), monster.getType());
                        if(originalPosition == finalPosition) {
                            currentMonster.reverseDirection();
                        } else {
                            currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(originalPosition[0], originalPosition[1]),
                                    finalPosition[0], finalPosition[1]);
                            TileType lastTileType = currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]).getType();
                            if(lastTileType == TileType.BUTTON) {
                                ChipsChallenge.Button previousButton = (ChipsChallenge.Button) currentLevel.getTileLayer().
                                        getTileAt(originalPosition[0], originalPosition[1]); // Refactor this later
                                previousButton.getLinkedTrap().setActive(false);
                            }
                        }
                    } else {
                        // Pink ball doesn't move
                        currentMonster.reverseDirection();
                    }
                } else if(monster.getType() == ActorType.BUG && movingActors.contains(monster.getType())) {
                    Bug currentMonster = (Bug) monster;
                    int[] originalPosition = new int[]{currentMonster.getX(), currentMonster.getY()};
                    currentMonster.turnBug(1);
                    int[] bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(), currentMonster.getDirection());
                    //currentMonster.turnBug(1);
                    int[] finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                    // Not sure if it's a problem but when a bug is left in open space 4x4 he will just circle around himself
                    // This is technically how he is expected to act though
                    // Nice feature would be to check his position after certain amount of game ticks, if he is in the same position
                    // Make him move forward or backwards next turn instead of turn his desired direction
                    if(finalPosition == originalPosition) {
                        currentMonster.turnBug(3);
                        bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(), currentMonster.getDirection());
                        finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                        if(finalPosition == originalPosition) {
                            currentMonster.turnBug(1);
                            bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(), currentMonster.getDirection());
                            finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                            if(finalPosition == originalPosition) {
                                currentMonster.turnBug(2);
                                bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(), currentMonster.getDirection());
                                finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                                if(finalPosition == originalPosition) {
                                    // Don't Move
                                    return;
                                }
                            }
                        }
                    }
                    currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(originalPosition[0], originalPosition[1]),
                            finalPosition[0], finalPosition[1]);
                    TileType lastTileType = currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]).getType();
                    if(lastTileType == TileType.BUTTON) {
                        ChipsChallenge.Button previousButton = (ChipsChallenge.Button) currentLevel.getTileLayer().
                                getTileAt(originalPosition[0], originalPosition[1]); // Refactor this later
                        previousButton.getLinkedTrap().setActive(false);
                    }
                }
            }
        }
    }

    private int[] moveMonster(int[] newPosition, int[] originalPosition, Actor monster) {
        if(isValidMove(newPosition)) {
            int[] finalPosition = collisionOccurredTile(newPosition, originalPosition, checkForTileCollision(newPosition), monster.getType());
            finalPosition = collisionOccuredActor(finalPosition, originalPosition, checkForActorCollision(finalPosition), monster.getType());
            if (originalPosition != finalPosition) {
                return newPosition;
            }
        }
        return originalPosition;
    }

    private int[] collisionOccuredActor(int[] position, int[] originalPosition, ActorType actorTypeCollision, ActorType originalActor) {
        switch (actorTypeCollision) {
            case BUG,PINKBALL,FROG,PLAYER:
                if(!(actorTypeCollision == originalActor) && originalActor != ActorType.BLOCK && (actorTypeCollision == ActorType.PLAYER || originalActor == ActorType.PLAYER)) {
                    try {
                        switchToDeathScreen(new ActionEvent());
                        AfterScreenController.stage = stage;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return originalPosition;
            case BLOCK:
                if(originalActor == ActorType.PLAYER) {
                    int[] blockNextMove = getNewPosition(position[0], position[1], nextMove);
                    if(isValidMove(blockNextMove)) {
                        int[] finalPosition = collisionOccurredTile(blockNextMove, position, checkForTileCollision(blockNextMove), actorTypeCollision);
                        finalPosition = collisionOccuredActor(finalPosition, originalPosition, checkForActorCollision(finalPosition), actorTypeCollision);
                        // Checking for case where block is being pushed onto another actor
                        // This prevents the block from killing the player
                        if(checkForActorCollision(blockNextMove) != ActorType.NOACTOR && checkForActorCollision(blockNextMove) != ActorType.PLAYER) {
                            return originalPosition;
                        }
                        TileType blockNextTileType = currentLevel.getTileLayer().getTileAt(blockNextMove[0], blockNextMove[1]).getType();
                        switch(blockNextTileType) {
                            case PATH, DIRT, ICE, ICEBL, ICEBR, ICETL, ICETR,BUTTON:
                                // If the moving block is now on-top of the player
                                if(finalPosition == originalPosition) {
                                    try {
                                        switchToDeathScreen(new ActionEvent());
                                        AfterScreenController.stage = stage;
                                        // Big problem here, game logic is continuing when game should be finished
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(position[0], position[1]),
                                            finalPosition[0], finalPosition[1]);
                                }
                                return position;
                            case WALL,CHIPSOCKET,DOOR:
                                return originalPosition;
                            case TRAP:
                                Trap currentTrap = (Trap) currentLevel.getTileLayer().getTileAt(blockNextMove[0], blockNextMove[1]);
                                if (currentTrap.isActive()) {
                                    return originalPosition;
                                } else {
                                    currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(position[0], position[1]),
                                            finalPosition[0], finalPosition[1]);
                                    return position;
                                }
                            case WATER:
                                // Replace next position with path and remove block from actor layer
                                currentLevel.getTileLayer().setTileAt(finalPosition[0], finalPosition[1], new Path());
                                currentLevel.getActorLayer().setActor(position[0], position[1], new NoActor());
                                // Return next move for Player as they should now be next to where the block was placed
                                return position;
                        }
                    }
                } else {
                    return originalPosition;
                }
                return originalPosition;
            default:
                return position; // No Actor
        }
    }

    private int[] collisionOccurredTile(int[] position, int[] originalPosition, TileType collisionTileType, ActorType actorType) {
        switch (collisionTileType) {
            case WALL:
                return originalPosition;
            case DIRT:
                if(actorType != ActorType.PLAYER && actorType != ActorType.BLOCK) {
                    return originalPosition;
                } else {
                    return position;
                }
            case ICE, ICEBL, ICEBR, ICETL, ICETR:
                if(actorType == ActorType.PLAYER || actorType == ActorType.BLOCK) {
                    nextMove = convertIceDirection(nextMove, collisionTileType);
                    int[] newerPosition = getNewPosition(position[0], position[1], nextMove);
                    if(!isValidMove(newerPosition)) {
                        return originalPosition;
                    }
                    if(actorType == ActorType.PLAYER) {
                        collisionOccurredItem(position); // Bit of code repetition here
                    }
                    // Make this nicer later
                    return collisionOccurredTile(newerPosition, originalPosition, checkForTileCollision(newerPosition), actorType);
                } else {
                    return originalPosition;
                }
            case WATER:
                if(actorType == ActorType.PLAYER) {
                    try {
                        switchToDeathScreen(new ActionEvent());
                        AfterScreenController.stage = stage;
                        // Big problem here, game logic is continuing when game should be finished
                        tickTimeline.stop();
                        return originalPosition;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if(actorType == ActorType.BLOCK) {
                    return position;
                } else {
                    return originalPosition;
                }
            case DOOR:
                if(actorType == ActorType.PLAYER) {
                    Door currentDoor = (Door) currentLevel.getTileLayer().getTileAt(position[0], position[1]);
                    for(Key key : inventory) {
                        if(key.getColour() == currentDoor.getColour()) {
                            currentLevel.getTileLayer().setTileAt(position[0], position[1], new Path());
                            return position;
                        }
                    }
                }
                return originalPosition;
            case CHIPSOCKET:
                if(actorType == ActorType.PLAYER) {
                    ChipSocket chipSocket = (ChipSocket) currentLevel.getTileLayer().getTileAt(position[0], position[1]);
                    if (chipCount >= chipSocket.getChipsRequired()) {
                        this.chipCount = chipCount - chipSocket.getChipsRequired();
                        currentLevel.getTileLayer().setTileAt(position[0], position[1], new Path());
                        return position;
                    }
                    return originalPosition;
                }
                return originalPosition;
            case BUTTON:
                ChipsChallenge.Button currentButton = (ChipsChallenge.Button) currentLevel.getTileLayer().
                        getTileAt(position[0], position[1]);
                currentButton.getLinkedTrap().setActive(true);
                return position;
            case EXIT:
                // Calculate score here
                // Put this in its own method
                if(actorType == ActorType.PLAYER) {
                    currentUser.setHighestLevelNum(currentLevel.getLevelNum());
                    currentUser.setCurrentLevel(null);
                    double score = currentTime * (chipCount + 1);
                    HighScore currentHighScore = new HighScore(currentLevel.getLevelNum());
                    currentHighScore.uploadNewScore(score, currentUser.getUserName());
                    System.out.println(currentHighScore.getScoreboard());
                    LogIn.updateUser(currentUser.getUserName(), currentUser.getHighestLevelNum(), currentUser.getCurrentLevel());
                    try {
                        switchToVictoryScreen(new ActionEvent());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // For when block is pushed onto exit via ice
                } else if(actorType == ActorType.BLOCK) {
                    return originalPosition;
                }
            default:
                return position;
        }
    }

    // This can be made far nicer by having the directions being in a set order, then you can just offset the ordering
    // based on the ice direction
    private KeyCode convertIceDirection(KeyCode currentDirection, TileType tileType) {
        if (tileType != TileType.ICE) {
            switch (currentDirection) {
                case D:
                    if (tileType.equals(TileType.ICETR)) {
                        return KeyCode.S;
                    } else if (tileType.equals(TileType.ICEBR)) {
                        return KeyCode.W;
                    }
                    break;
                case A:
                    if (tileType.equals(TileType.ICETL)) {
                        return KeyCode.S;
                    } else if (tileType.equals(TileType.ICEBL)) {
                        return KeyCode.W;
                    }
                    break;
                case W:
                    if (tileType.equals(TileType.ICETR)) {
                        return KeyCode.A;
                    } else if (tileType.equals(TileType.ICETL)) {
                        return KeyCode.D;
                    }
                    break;
                case S:
                    if (tileType.equals(TileType.ICEBR)) {
                        return KeyCode.A;
                    } else if (tileType.equals(TileType.ICEBL)) {
                        return KeyCode.D;
                    }
                    break;
                default:
                    return currentDirection;
            }
        }
        return currentDirection;
    }

    private void collisionOccurredItem(int[] position) {
        Item possibleItem = currentLevel.getItemLayer().getItemAt(position[0], position[1]);
        if (possibleItem.getType() != ItemType.NOTHING) {
            currentLevel.getItemLayer().removeItem(position[0], position[1]);
            if(possibleItem.getType() == ItemType.CHIP) {
                chipCount++;
            } else {
                inventory.add((Key) possibleItem);
                drawInventory();
            }
        }
    }

    @FXML
    private void drawInventory() {
        GraphicsContext inventoryGc = inventoryCanvas.getGraphicsContext2D();
        inventoryGc.clearRect(0, 0, inventoryCanvas.getWidth(), inventoryCanvas.getHeight());
        Image pathImg = new Path().getImage();
        for(int i=0; i<4; i++) {
            inventoryGc.drawImage(pathImg, i*50, 0);
        }
        // COME BACK LATER CAN PICK UP MULTIPLE KEYS
        for(int i=0; i<inventory.size(); i++) {
            inventoryGc.drawImage(inventory.get(i).getImage(), i* 50, 0);
        }
    }

    public void switchToDeathScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/after-game.fxml"));
        tickTimeline.stop(); // Fix this later
        AfterScreenController.isDead = false;
        AfterScreenController.currentUser = currentUser;
        AfterScreenController.stage = stage;
        AfterScreenController.titleMsg = "Unlucky!";
        AfterScreenController.message = "Would you like to play the try again";
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Unlucky");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToVictoryScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/after-game.fxml"));
        tickTimeline.stop(); // Fix this later
        AfterScreenController.currentUser = currentUser;
        AfterScreenController.isDead = true;
        // Could also add here high score passed into the victory screen, and if they beat any high scores what position
        // they are now in
        AfterScreenController.stage = stage;
        AfterScreenController.titleMsg = "Congratulations!";
        AfterScreenController.message = "Would you like to play the next level";
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Congratulations!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHighScore(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/highscore.fxml"));
        tickTimeline.stop();
        HighscoreController.levelNum = currentLevel.getLevelNum();
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Need this line for initalize
        stage.setTitle("Highscore Table");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainMenuController.stage = stage;
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void saveCurrentLevel() {
        String tempCurrentLevels = "tempCurrentLevels.txt";
        File oldFile = new File("SavedLevels.txt");
        File newFile = new File(tempCurrentLevels);
        try{
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            TileLayer tileLayer = currentLevel.getTileLayer();
            for(int i=0; i<tileLayer.getTiles().length; i++) {
                for(int j=0; j<tileLayer.getTiles()[0].length; j++) {
                    String tileStr = convertTileToString(tileLayer.getTileAt(j,i));
                    printWriter.write(tileStr + ",");
                }
                printWriter.write("\n");
            }

            printWriter.write("\n");

            ItemLayer itemLayer = currentLevel.getItemLayer();
            for(int i=0; i<itemLayer.getItems().length; i++) {
                for(int j=0; j<itemLayer.getItems()[0].length; j++) {
                    String itemStr = convertItemToString(itemLayer.getItemAt(j,i));
                    printWriter.write(itemStr + ",");
                }
                printWriter.write("\n");
            }

            printWriter.write("\n");

            ActorLayer actorLayer = currentLevel.getActorLayer();
            for(int i=0; i<actorLayer.getActors().length; i++) {
                for(int j=0; j<actorLayer.getActors()[0].length; j++) {
                    String actorStr = convertActorToString(actorLayer.getActor(j,i));
                    printWriter.write(actorStr + ",");
                }
                printWriter.write("\n");
            }

            printWriter.write("\n");

            printWriter.flush();
            printWriter.close();
            if(oldFile.delete()) {
                File dump = new File("SavedLevels.txt");
                if(!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch(IOException e) {
            System.out.println("Problem");
        }
    }

    public String convertTileToString(Tile tile) {
        switch(tile.getType()) {
            case PATH:
                return "p";
            case DIRT:
                return "di";
            case WALL:
                return "w";
            case EXIT:
                return "e";
            case BUTTON:
                return "b";
            case TRAP:
                return "t";
            case WATER:
                return "wt";
            case ICE:
                return "i";
            case ICETR:
                return "itr";
            case ICETL:
                return "itl";
            case ICEBR:
                return "ibr";
            case ICEBL:
                return "ibl";
            case CHIPSOCKET:
                ChipSocket currentTile = (ChipSocket) tile;
                return "cs" + currentTile.getChipsRequired();
            case DOOR:
                Door currentDoor = (Door) tile;
                char doorColour = currentDoor.getColour().toString().charAt(0);
                return "d" + Character.toLowerCase(doorColour);
            default:
                // That should have covered every tile
                return "ERROR";
        }
    }

    public String convertItemToString(Item item) {
        switch(item.getType()) {
            case NOTHING:
                return "n";
            case KEY:
                Key currentKey = (Key) item;
                char doorColour = currentKey.getColour().toString().charAt(0);
                return "k" + Character.toLowerCase(doorColour);
            case CHIP:
                return "c";
            default:
                // Every case should have been covered
                return "ERROR";
        }
    }

    public String convertActorToString(Actor actor) {
        switch(actor.getType()) {
            case NOACTOR:
                return "n";
            case PLAYER:
                return "p";
            case PINKBALL:
                return "pb";
            case BLOCK:
                return "bl";
            case BUG:
                return "b";
            case FROG:
                return "f";
            default:
                throw new IllegalArgumentException();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
