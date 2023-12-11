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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

/**
 * Creates and manages the game logic, flow and GUI. The GameController will determine actors next moves,
 * update their positions and draw them to the screen. It handles the tick-rate of the game and identifies
 * win and loss conditions. It stores all current game data such as the current time, chip count and
 * positions of every actor, item and tile.
 *
 * @author Samuel Davies
 */
public class GameController extends Application {

    /**
     * The time in ms for the game to update the screen and read in user inputs.
     */
    private static final int TICK_RATE = 250;

    /**
     * The gap required between tiles, actors and items when they are drawn to the screen.
     */
    private static final int TILE_OFFSET = 50;

    /**
     * The maximum amount of keys a user can have.
     */
    private static final int MAX_KEYS = 4;

    /**
     * Number of layers needed to fully describe level.
     */
    public static final int NUM_OF_LAYERS = 5;

    /**
     * Position of width in layer array.
     */
    public static final int WIDTH_POSITION = 0;

    /**
     * Position of height in layer array.
     */
    public static final int HEIGHT_POSITION = 1;

    /**
     * Position of time in layer array.
     */
    public static final int LEVEL_TIME_POSITION = 2;

    /**
     * Position of level number in layer array.
     */
    public static final int LEVEL_NUM_POSITION = 3;

    /**
     * Position of level description in layer array.
     */
    private static final int LEVEL_DESC_POSITION = 4;

    /**
     * The main game stage.
     */
    @FXML
    private static Stage stage;

    /**
     * The current user playing the game.
     */
    private static User currentUser;

    /**
     * The main drawing canvas for the game's GUI.
     */
    @FXML
    private Canvas mainCanvas;

    /**
     * The drawing canvas for the inventory view.
     */
    @FXML
    private Canvas inventoryCanvas;

    /**
     * The HBox for the levelName.
     */
    @FXML
    private HBox levelNameBox;

    /**
     * The label element for the level's number.
     */
    @FXML
    private Label levelNumTxt;

    /**
     * The label element for the level's name/desc.
     */
    @FXML
    private Label levelNameTxt;

    /**
     * The label element showing the player's current chip number.
     */
    @FXML
    private Label chipNumber;

    /**
     * The label element showing the current time on the level.
     */
    @FXML
    private Label timeLbl;

    /**
     * The current level that is being played.
     */
    private Level currentLevel;

    /**
     * The current timeline the games tick rate is based on.
     */
    private Timeline tickTimeline;

    /**
     * The next move the player intended to make on their next tick.
     */
    private KeyCode nextMove;

    /**
     * The player's current inventory.
     */
    private ArrayList<Key> inventory;

    /**
     * The amount of chips the player currently possess.
     */
    private int chipCount = 0;

    /**
     * The current time of the level.
     */
    private int currentTime;

    /**
     * If the level being played is a saved level, or a new level.
     */
    private boolean isSavedLevel = false;

    /**
     * The current tick the game is on. Each increment of this tick value will result in different
     * actors being able to move.
     */
    private int currentTick = 0;

    /**
     * If the level is finished. This is used later on to remove saved levels from file if they have
     * now been completed.
     */
    private boolean levelFinished = false;

    /**
     * A list of the saved levels on file.
     */
    private ArrayList<SavedLevel> savedLevels;

    /**
     * The games dedicated GameCollision Handler. Used to check for collisions between actors
     * and tiles.
     */
    private GameCollision collisionHandler;

    /**
     * Main start method that creates and initialises the game screen scene.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialises components needed for the GUI to function as intended.
     */
    @FXML
    private void initialize() {
        gameStart();
    }

    /**
     * The game start method creates the level to be played and sets up the GUI to create the level.
     * It also starts the tick system and initialises the level's information on the UI.
     */
    @FXML
    private void gameStart() {
        if (currentLevel != null) {
            nextLevelLoad();
        } else {
            loadLevelFile();
        }
        levelNumTxt.setText("level " + currentLevel.getLevelNum());
        levelNameTxt.setText(currentLevel.getLevelDesc());
        if (!isSavedLevel) {
            currentTime = currentLevel.getLevelTime();
            inventory = new ArrayList<>();
        }
        collisionHandler = new GameCollision(currentLevel);
        drawGame();
        drawInventory();
        // Begin ticks
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICK_RATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    /**
     * Resets the current level's progress including inventory, time and chip count.
     */
    @FXML
    private void reset() {
        tickTimeline.stop();
        gameStart();
    }

    /**
     * Creates the GUI of the game by loading in each layer of the level and drawing it to the main canvas.
     * It will also update the time and current chip number.
     */
    @FXML
    private void drawGame() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        // Read in and draw tile layer
        Tile[][] tileLayerGraphics = currentLevel.getTileLayer().getTiles();
        for (int i = 0; i < tileLayerGraphics.length; i++) {
            for (int j = 0; j < tileLayerGraphics[i].length; j++) {
                gc.drawImage(tileLayerGraphics[i][j].getImage(), j * TILE_OFFSET, i * TILE_OFFSET);
                if (tileLayerGraphics[i][j].getType() == TileType.CHIPSOCKET) {
                    ChipSocket cs = (ChipSocket) tileLayerGraphics[i][j];
                    // If it is a chip socket, draw the number of chips required on the same tile
                    gc.drawImage(new Image("images/stuff/" + cs.getChipsRequired() + ".png"),
                            j * TILE_OFFSET, i * TILE_OFFSET);
                }
            }
        }

        // Read in and draw item layer
        Item[][] itemLayerGraphics = currentLevel.getItemLayer().getItems();
        for (int i = 0; i < itemLayerGraphics.length; i++) {
            for (int j = 0; j < itemLayerGraphics[i].length; j++) {
                Image currentGraphic = itemLayerGraphics[i][j].getImage();
                if (currentGraphic != null) {
                    gc.drawImage(currentGraphic, j * TILE_OFFSET, i * TILE_OFFSET);
                }
            }
        }

        // Read in and draw actor layer
        Actor[][] actorLayerGraphics = currentLevel.getActorLayer().getActors();
        for (int i = 0; i < actorLayerGraphics.length; i++) {
            for (int j = 0; j < actorLayerGraphics[i].length; j++) {
                Image currentGraphic = actorLayerGraphics[i][j].getImage();
                if (currentGraphic != null) {
                    gc.drawImage(currentGraphic, j * TILE_OFFSET, i * TILE_OFFSET);
                }
            }
        }
        chipNumber.setText("Chips: " + chipCount);
        timeLbl.setText("Time: " + Math.ceil(currentTime));
    }

    /**
     * Method to draw and update inventory GUI. It will also remove any duplicate
     * keys during this process.
     */
    @FXML
    private void drawInventory() {
        GraphicsContext inventoryGc = inventoryCanvas.getGraphicsContext2D();
        inventoryGc.clearRect(0, 0, inventoryCanvas.getWidth(), inventoryCanvas.getHeight());
        Image pathImg = new Path().getImage();
        for (int i = 0; i < MAX_KEYS; i++) {
            inventoryGc.drawImage(pathImg, i * TILE_OFFSET, 0);
        }

        // Iterate over inventory and remove any duplicate keys
        Iterator<Key> condensedInventory = this.inventory.iterator();
        ArrayList<Colour> seenColours = new ArrayList<>();
        while (condensedInventory.hasNext()) {
            Key key = condensedInventory.next();
            if (seenColours.contains(key.getColour())) {
                condensedInventory.remove();
            } else {
                seenColours.add(key.getColour());
            }
        }
        for (int i = 0; i < inventory.size(); i++) {
            inventoryGc.drawImage(inventory.get(i).getImage(), i * TILE_OFFSET, 0);
        }
    }

    /**
     * Changes the current scene to the death after screen scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException For when the resource file can't be found.
     */
    @FXML
    public void switchToDeathScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/after-game.fxml"));
        tickTimeline.stop();
        // Changing the GUI elements for death screen
        AfterScreenController.setIsDead(true);
        AfterScreenController.setCurrentUser(currentUser);
        AfterScreenController.setStage(stage);
        AfterScreenController.setTitleMsg("Unlucky!");
        AfterScreenController.setMessage("Would you like to play the try again");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Unlucky");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the current scene to the victory after screen scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException When the resource file can't be found.
     */
    @FXML
    public void switchToVictoryScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/after-game.fxml"));
        tickTimeline.stop(); // Fix this later
        AfterScreenController.setCurrentUser(currentUser);
        AfterScreenController.setIsDead(false);
        AfterScreenController.setStage(stage);
        AfterScreenController.setTitleMsg("Congratulations!");
        AfterScreenController.setMessage("Would you like to play the next level");

        // If the user had this level saved, remove it from the saved levels file
        SavedLevel currentSavedLevel = null;
        for (SavedLevel saved : savedLevels) {
            if (saved.getUsername().equals(currentUser.getUserName())) {
                currentSavedLevel = saved;
            }
        }
        if (currentSavedLevel != null) {
            savedLevels.remove(currentSavedLevel);
        }
        levelFinished = true;
        saveCurrentLevel();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Congratulations!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the current scene to the high score scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException When the resource file can't be found.
     */
    @FXML
    public void switchToHighScore(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/highscore.fxml"));
        tickTimeline.stop();
        HighscoreController.setCurrentUser(currentUser);
        HighscoreController.setStage(stage);
        HighscoreController.setLevelNum(currentLevel.getLevelNum());
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Highscore Table");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the current scene to the main menu scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException When the resource file can't be found.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main-menu.fxml"));
        tickTimeline.stop();
        Scene scene = new Scene(fxmlLoader.load());
        MainMenuController.setStage(stage);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stores the players key pressed to be stored for the next available tick.
     *
     * @param event The key event that fired when the user pressed a key.
     */
    @FXML
    public void storeKeyEvent(KeyEvent event) {
        nextMove = event.getCode();
        event.consume();
    }

    /**
     * Kills the player by ending the game ticks and changing the seen to the death after screen.
     */
    @FXML
    private void killPlayer() {
        try {
            switchToDeathScreen(new ActionEvent());
            tickTimeline.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the level currently being played to file.
     */
    @FXML
    private void saveCurrentLevel() {
        SavedLevel newestSave = new SavedLevel(currentUser.getUserName(), currentTime, chipCount,
                inventory, currentLevel);
        FileHandling.saveCurrentLevel(this.savedLevels, newestSave, levelFinished);
    }

    /**
     * Sets the current stage of the GameController
     *
     * @param stage The JavaFX stage to be set.
     */
    public static void setStage(Stage stage) {
        GameController.stage = stage;
    }

    /**
     * Returns the current user that logged in.
     *
     * @return The current user playing.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     *
     * @param currentUser The current user.
     */
    public static void setCurrentUser(User currentUser) {
        GameController.currentUser = currentUser;
    }

    /**
     * Tracks the time of the game and manages which actors can move on any given tick.
     * It also updates the GUI on every tick.
     */
    public void tick() {
        currentTick = currentTick == 4 ? 0 : currentTick + 1;
        ArrayList<ActorType> actorsToMove = new ArrayList<>();
        actorsToMove.add(ActorType.PINKBALL);
        if (currentTick % 2 == 0) {
            actorsToMove.add(ActorType.BUG);
        }

        if (currentTick == 3) {
            actorsToMove.add(ActorType.FROG);
        }

        // Each tick is 250ms each second is 4 ticks
        if (currentTick == 4) {
            currentTime = currentTime - 1;
            if (currentTime == 0) {
                killPlayer();
            }
        }
        movePlayer();
        moveMonsters(actorsToMove);
        // Reset player to move for next tick
        nextMove = null;
        drawGame();
    }

    /**
     * Loads a level into the game to be played. If no saved level can be found, the player's
     * next level past their highest level will be selected.
     */
    public void loadLevelFile() {
        ArrayList<SavedLevel> savedLevels = FileHandling.readInSavedLevels();
        this.savedLevels = savedLevels;
        for (SavedLevel savedLevel : savedLevels) {
            if (savedLevel.getUsername().equals(currentUser.getUserName())) {
                this.currentLevel = savedLevel.getLevel();
                isSavedLevel = true;
                inventory = savedLevel.getInventory();
                currentTime = savedLevel.getCurrentTime();
                chipCount = savedLevel.getChipCount();
            }
        }
        // If they haven't saved a level on file
        if (currentLevel == null) {
            nextLevelLoad();
        }
    }

    /**
     * Loads in the level after the users current highest level achieved.
     */
    public void nextLevelLoad() {
        int currentLevel = currentUser.getHighestLevelNum() + 1;
        File myFile = new File("src/levels/level" + currentLevel + ".txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] infoArr = FileHandling.splitFile(myReader, 1)[0].split(",");
        String[][] layers = new String[NUM_OF_LAYERS][1];
        for (int i = 0; i < NUM_OF_LAYERS; i++) {
            layers[i] = FileHandling.splitFile(myReader, Integer.parseInt(infoArr[1]));
        }
        this.currentLevel = new Level(Integer.parseInt(infoArr[WIDTH_POSITION]),
                Integer.parseInt(infoArr[HEIGHT_POSITION]), Integer.parseInt(infoArr[LEVEL_TIME_POSITION]),
                Integer.parseInt(infoArr[LEVEL_NUM_POSITION]), infoArr[LEVEL_DESC_POSITION], layers);
    }

    /**
     * Returns the actors next position based on their coordinates and their current moving direction.
     *
     * @param x         The x-coordinate of the actor.
     * @param y         The y-coordinate of the actor.
     * @param direction The direction the actor is currently heading.
     * @return The new coordinates for the actor.
     */
    private int[] getNewPosition(int x, int y, KeyCode direction) {
        switch (direction) {
            case D:
                return new int[]{x + 1, y};
            case A:
                return new int[]{x - 1, y};
            case W:
                return new int[]{x, y - 1};
            case S:
                return new int[]{x, y + 1};
            default:
                return new int[]{x, y};
        }
    }

    /**
     * Method to move the player and update their position in the actor layer. It also checks for trap
     * and button interactions occurring on the player. If the user has inputted and invalid move i.e.
     * on a blocking tile or an invalid direction, the player will not move.
     */
    private void movePlayer() {
        Player currentPlayer = currentLevel.getActorLayer().getPlayer();
        if (nextMove == KeyCode.W || nextMove == KeyCode.A
                || nextMove == KeyCode.S || nextMove == KeyCode.D) {
            int[] originalPosition = new int[]{currentPlayer.getX(), currentPlayer.getY()};
            // Get the next position the player would be
            int[] nextPosition = getNewPosition(originalPosition[0], originalPosition[1], nextMove);
            Tile possibleTrap = currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]);
            if (possibleTrap.getType() == TileType.TRAP) {
                Trap foundTrap = (Trap) possibleTrap;
                // If the trap is active, the player can't move so the method is ended
                if (foundTrap.isActive()) {
                    return;
                }
            }
            if (collisionHandler.isValidMove(nextPosition)) {
                // Check for tile interactions
                int[] finalPosition = collisionOccurredTile(nextPosition, originalPosition,
                        collisionHandler.checkForTileCollision(nextPosition), currentPlayer.getType());
                // Check for actor interaction
                finalPosition = collisionOccurredActor(finalPosition, originalPosition,
                        collisionHandler.checkForActorCollision(finalPosition), currentPlayer.getType());

                // Update players position to new position
                currentLevel.getActorLayer().updateActor(currentPlayer, finalPosition[0], finalPosition[1]);
                collisionOccurredItem(finalPosition);
                TileType lastTileType = currentLevel.getTileLayer().getTileAt(originalPosition[0],
                        originalPosition[1]).getType();

                if (lastTileType == TileType.DIRT) {
                    currentLevel.getTileLayer().setTileAt(originalPosition[0], originalPosition[1],
                            new Path());
                    // The last tile was a button, and they aren't currently still standing on the button
                } else if (lastTileType == TileType.BUTTON && originalPosition != finalPosition) {
                    resetButton(lastTileType, originalPosition);
                }
            }
        }
    }

    /**
     * Takes ina list of ActorType that can move on this tick and will find each monster's new position and
     * update it on the actorLayer. If the monster isn't on the movingActors, it will be ignored.
     *
     * @param movingActors The list of actors that can move this tick.
     */
    private void moveMonsters(ArrayList<ActorType> movingActors) {
        for (Actor monster : currentLevel.getActorLayer().getMonsters()) {
            TileLayer tileLayer = currentLevel.getTileLayer();
            // The monster doesn't move if they are on an active trap
            if (!collisionHandler.onActiveTrap(monster)) {
                if (monster.getType() == ActorType.PINKBALL && movingActors.contains(monster.getType())) {

                    PinkBall currentMonster = (PinkBall) monster;
                    int[] originalPosition = new int[]{currentMonster.getX(), currentMonster.getY()};
                    int[] ballNextMove = getNewPosition(currentMonster.getX(),
                            currentMonster.getY(), currentMonster.getDirection());

                    if (collisionHandler.isValidMove(ballNextMove)) {
                        // Check for tile collision
                        int[] finalPosition = collisionOccurredTile(ballNextMove, originalPosition,
                                collisionHandler.checkForTileCollision(ballNextMove), currentMonster.getType());

                        // Check for actor collision
                        finalPosition = collisionOccurredActor(finalPosition, originalPosition,
                                collisionHandler.checkForActorCollision(finalPosition), monster.getType());

                        if (originalPosition == finalPosition) {
                            // if the ball didn't move, reverse its direction
                            currentMonster.reverseDirection();
                        } else {
                            // Update pink balls position in the ActorLayer
                            currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(
                                            originalPosition[0], originalPosition[1]),
                                    finalPosition[0], finalPosition[1]);

                            TileType lastTileType = tileLayer.getTileAt(originalPosition[0],
                                    originalPosition[1]).getType();

                            resetButton(lastTileType, originalPosition);
                        }
                    } else {
                        // Pink ball doesn't move and so the direction is reverse
                        currentMonster.reverseDirection();
                    }
                } else if (monster.getType() == ActorType.BUG && movingActors.contains(monster.getType())) {
                    Bug currentMonster = (Bug) monster;
                    int[] originalPosition = new int[]{currentMonster.getX(), currentMonster.getY()};
                    currentMonster.turnBug(1);
                    int[] bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(),
                            currentMonster.getDirection());

                    int[] finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);

                    // These series of if statements check if the bug can move forward firstly in his followEdge
                    // direction, if not then forward, then backward and then the other side.
                    // If the bug can't move in any direction, it simply doesn't move
                    finalPosition = bugMoves(finalPosition, originalPosition, currentMonster);
                    // If the bug was able to move, update its position
                    if (finalPosition != originalPosition) {
                        currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(
                                        originalPosition[0], originalPosition[1]),
                                finalPosition[0], finalPosition[1]);

                        TileType lastTileType = currentLevel.getTileLayer().getTileAt(
                                originalPosition[0], originalPosition[1]).getType();
                        resetButton(lastTileType, originalPosition);
                    }

                } else if (monster.getType() == ActorType.FROG && movingActors.contains(monster.getType())) {
                    int[] originalPosition = new int[]{monster.getX(), monster.getY()};
                    Frog currentMonster = (Frog) monster;
                    int[] nextMove = moveFrog(originalPosition, currentMonster);
                    currentLevel.getActorLayer().updateActor(currentLevel.getActorLayer().getActor(
                                    originalPosition[0], originalPosition[1]),
                            nextMove[0], nextMove[1]);

                    TileType lastTileType = currentLevel.getTileLayer().getTileAt(
                            originalPosition[0], originalPosition[1]).getType();
                    resetButton(lastTileType, originalPosition);
                }
            }
        }
    }

    /**
     * Takes in a position and will turn the bug in every direction and try to make it move forward.
     * The direction is priority based with the follow edge being prioritised over the initial direction.
     *
     * @param finalPosition    The first position the bug tried to move to.
     * @param originalPosition The original position the bug started in.
     * @param currentMonster   The bug that is being moved.
     * @return The bugs final position.
     */
    private int[] bugMoves(int[] finalPosition, int[] originalPosition, Bug currentMonster) {
        int[] bugNextMove;
        if (finalPosition == originalPosition) {
            // Turn the bug so they are straight
            currentMonster.turnBug(3);
            bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(),
                    currentMonster.getDirection());

            finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
            if (finalPosition == originalPosition) {
                // Turn the bug so that they are looking behind them
                currentMonster.turnBug(1);
                bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(),
                        currentMonster.getDirection());

                finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                if (finalPosition == originalPosition) {
                    // Turn the bug so that they are looking opposite their following edge
                    currentMonster.turnBug(2);
                    bugNextMove = getNewPosition(currentMonster.getX(), currentMonster.getY(),
                            currentMonster.getDirection());

                    finalPosition = moveMonster(bugNextMove, originalPosition, currentMonster);
                    if (finalPosition == originalPosition) {
                        // Don't Move
                        return originalPosition;
                    }
                }
            }
        }
        return finalPosition;
    }

    /**
     * Checks if an actor has moved off a button. If they have, it will deactivate the button.
     *
     * @param lastTileType     The TileType of the last tile the actor was standing on.
     * @param originalPosition The original position of the last tile and actor.
     */
    private void resetButton(TileType lastTileType, int[] originalPosition) {
        if (lastTileType == TileType.BUTTON) {
            ChipsChallenge.Button previousButton = (ChipsChallenge.Button) currentLevel.getTileLayer().
                    getTileAt(originalPosition[0], originalPosition[1]);
            previousButton.getLinkedTrap().setActive(false);
        }
    }


   /*
    private int[] moveFrog(Frog frog, int[] originalPosition) {
        ArrayList<int[]> visitedPositions = new ArrayList<>();
        FrogMove left = moveFrogRecursive(originalPosition, frog, visitedPositions,
                new FrogMove(0, KeyCode.A));

        FrogMove up = moveFrogRecursive(originalPosition, frog, visitedPositions,
                new FrogMove(0, KeyCode.W));

        FrogMove right = moveFrogRecursive(originalPosition, frog, visitedPositions,
                new FrogMove(0, KeyCode.D));

        FrogMove down = moveFrogRecursive(originalPosition, frog, visitedPositions,
                new FrogMove(0, KeyCode.S));

        int minMove = Math.min(left.getStepsTaken(), Math.min(up.getStepsTaken(),
                Math.min(right.getStepsTaken(), down.getStepsTaken())));

        FrogMove minFrogMove = null;
        if(!(minMove >= 100)) {
            if(minMove == left.getStepsTaken()) {
                minFrogMove = left;
            } else if(minMove == up.getStepsTaken()) {
                minFrogMove = up;
            } else if(minMove == right.getStepsTaken()) {
                minFrogMove = right;
            } else {
                minFrogMove = down;
            }
            int[] nextMove = getNewPosition(originalPosition[0], originalPosition[1],
                minFrogMove.getDirection());
            int[] finalPosition = moveMonster(nextMove, originalPosition, frog);
            if(isValidMove(finalPosition)) {
                return finalPosition;
            } else {
                return originalPosition;
            }
        } else {
            return originalPosition;
        }

    }
    */


    /**
     * Takes in the position the frog starts in and returns a position that brings the frog closer to
     * the player. If the frog can't move directly to the player, they will attempt to stay at the same
     * range as the player.
     *
     * @param originalPosition The position the frog started in.
     * @param frog             The frog that is moving.
     * @return The frog's final position of their turn.
     */
    private int[] moveFrog(int[] originalPosition, Frog frog) {

        int[] finalPosition;

        Player currentPlayer = currentLevel.getActorLayer().getPlayer();
        if (frog.getX() > currentPlayer.getX()) {
            finalPosition = moveMonster(getNewPosition(originalPosition[0], originalPosition[1], KeyCode.A),
                    originalPosition, frog);

        } else if (frog.getX() < currentPlayer.getX()) {
            finalPosition = moveMonster(getNewPosition(originalPosition[0], originalPosition[1], KeyCode.D),
                    originalPosition, frog);
        } else {
            if (frog.getY() > currentPlayer.getY()) {
                finalPosition = moveMonster(getNewPosition(originalPosition[0], originalPosition[1], KeyCode.W),
                        originalPosition, frog);
            } else {
                finalPosition = moveMonster(getNewPosition(originalPosition[0], originalPosition[1], KeyCode.S),
                        originalPosition, frog);
            }
        }
        if (finalPosition == originalPosition) {
            return randomMoveFrog(originalPosition, frog);
        } else {
            return finalPosition;
        }
    }

    /**
     * Takes in the frogs original position and finds an available path to take if any is available.
     *
     * @param originalPosition The position the frog started in.
     * @param frog             The frog that's attempting to move.
     * @return The frog's next position.
     */
    private int[] randomMoveFrog(int[] originalPosition, Frog frog) {
        ArrayList<KeyCode> directions = new ArrayList<>();
        directions.add(KeyCode.A);
        directions.add(KeyCode.W);
        directions.add(KeyCode.D);
        directions.add(KeyCode.S);

        Collections.shuffle(directions);
        Iterator<KeyCode> directionsIterator = directions.iterator();
        // Iterate and remove each direction until either none are left or one is found
        while (directionsIterator.hasNext()) {
            KeyCode currentDirection = directionsIterator.next();
            int[] finalPosition = moveMonster(getNewPosition(originalPosition[0], originalPosition[1],
                    currentDirection), originalPosition, frog);
            if (finalPosition != originalPosition) {
                return finalPosition;
            } else {
                directionsIterator.remove();
            }
        }
        return originalPosition;
    }

    /*
    private FrogMove moveFrogRecursive(int[] position, Frog frog, ArrayList<int[]> visitedPositions,
                                       FrogMove frogMove) {
        if(visitedPositions.contains(position)) {
            return new FrogMove(100, frogMove.getDirection());
        } else {
            int[][] newPositions = new int[4][2];
            KeyCode currentDirection = KeyCode.A;
            for(int i=0; i<4; i++) {
                newPositions[i] = getNewPosition(position[0], position[1], currentDirection);
                currentDirection = turn(currentDirection);
            }
            for(int[] currentPosition: newPositions) {
                if(isValidMove(currentPosition)) {
                    int[] finalPosition = collisionOccurredTile(currentPosition, position,
                            checkForTileCollision(currentPosition), frog.getType());
                    finalPosition = collisionOccuredActor(finalPosition, position,
                            checkForActorCollision(finalPosition), frog.getType());
                    if(checkForActorCollision(finalPosition).equals(ActorType.PLAYER)) {
                        return frogMove;
                    } else if(currentPosition == finalPosition) {
                        return new FrogMove(100, frogMove.getDirection());
                    } else {
                        visitedPositions.add(finalPosition);
                        FrogMove newFrogMove = new FrogMove(frogMove.getStepsTaken() + 1,
                                frogMove.getDirection());
                        return moveFrogRecursive(finalPosition, frog, visitedPositions, newFrogMove);
                    }
                } else {
                    return new FrogMove(1000, frogMove.getDirection());
                }
            }
        }
        // Shouldn't happen
        return new FrogMove(1000, frogMove.getDirection());
    }

    // Move this into frog later
    private KeyCode turn(KeyCode direction) {
        switch (direction) {
            case A:
                return KeyCode.W;
            case W:
                return KeyCode.D;
            case D:
                return KeyCode.S;
            case S:
                return KeyCode.A;
            default:
                throw new IllegalArgumentException();
        }
    }

     */


    /**
     * Checks for actor and tile collisions when a monster is attempting to move. If the position is
     * different from the original position it is returned, else the original is returned.
     *
     * @param newPosition      The position the monster is attempting to move to.
     * @param originalPosition The original position the monster came from.
     * @param monster          The monster that is moving.
     * @return The monsters final position of its turn.
     */
    private int[] moveMonster(int[] newPosition, int[] originalPosition, Actor monster) {
        if (collisionHandler.isValidMove(newPosition)) {

            int[] finalPosition = collisionOccurredTile(newPosition, originalPosition,
                    collisionHandler.checkForTileCollision(newPosition), monster.getType());

            finalPosition = collisionOccurredActor(finalPosition, originalPosition,
                    collisionHandler.checkForActorCollision(finalPosition), monster.getType());
            if (originalPosition != finalPosition) {
                return newPosition;
            }
        }
        return originalPosition;
    }

    /**
     * Checks for a collision occurring between an actor and an actor. Depending on either actor the
     * behaviour changes significantly. It will return the original actor's new position taking into account
     * any actors it may have interacted with.
     *
     * @param position           The new position of the original actor.
     * @param originalPosition   The original position of the original actor.
     * @param actorTypeCollision The actor type of the actor the original actor collided with.
     * @param originalActor      The original actors type.
     * @return The new coordinates for the original actor.
     */
    private int[] collisionOccurredActor(int[] position, int[] originalPosition, ActorType actorTypeCollision,
                                         ActorType originalActor) {
        switch (actorTypeCollision) {
            case BUG, PINKBALL, FROG, PLAYER:
                if (!(actorTypeCollision == originalActor) && originalActor != ActorType.BLOCK
                        && (actorTypeCollision == ActorType.PLAYER || originalActor == ActorType.PLAYER)) {
                    killPlayer();
                    return originalPosition;
                }
                return originalPosition;
            case BLOCK:
                if (originalActor == ActorType.PLAYER) {
                    int[] blockNextMove = getNewPosition(position[0], position[1], nextMove);
                    if (collisionHandler.isValidMove(blockNextMove)) {
                        int[] finalPosition = collisionOccurredTile(blockNextMove, position,
                                collisionHandler.checkForTileCollision(blockNextMove), actorTypeCollision);

                        finalPosition = collisionOccurredActor(finalPosition, originalPosition,
                                collisionHandler.checkForActorCollision(finalPosition), actorTypeCollision);

                        // Checking for case where block is being pushed onto another actor
                        // This prevents the block from killing the player
                        if (collisionHandler.checkForActorCollision(blockNextMove) != ActorType.NOACTOR
                                && collisionHandler.checkForActorCollision(blockNextMove) != ActorType.PLAYER) {
                            return originalPosition;
                        }
                        TileType blockNextTileType = currentLevel.getTileLayer().getTileAt(blockNextMove[0],
                                blockNextMove[1]).getType();
                        switch (blockNextTileType) {
                            case PATH, DIRT, ICE, ICEBL, ICEBR, ICETL, ICETR, BUTTON:
                                // If the moving block is now on-top of the player
                                if (finalPosition == originalPosition) {
                                    killPlayer();
                                } else {
                                    currentLevel.getActorLayer().updateActor(currentLevel.
                                                    getActorLayer().getActor(position[0], position[1]),
                                            finalPosition[0], finalPosition[1]);
                                }
                                return position;
                            case WALL, CHIPSOCKET, DOOR:
                                return originalPosition;
                            case TRAP:
                                Trap currentTrap = (Trap) currentLevel.getTileLayer().getTileAt(
                                        blockNextMove[0], blockNextMove[1]);

                                if (currentTrap.isActive()) {
                                    return originalPosition;
                                } else {
                                    currentLevel.getActorLayer().updateActor(
                                            currentLevel.getActorLayer().getActor(position[0], position[1]),
                                            finalPosition[0], finalPosition[1]);
                                    return position;
                                }
                            case WATER:
                                // Replace next position with path and remove block from actor layer
                                currentLevel.getTileLayer().setTileAt(finalPosition[0],
                                        finalPosition[1], new Path());
                                currentLevel.getActorLayer().setActor(position[0],
                                        position[1], new NoActor());
                                // Return next move for Player as they should now be
                                // next to where the block was placed
                                return position;
                        }
                    }
                } else {
                    return originalPosition;
                }
                return originalPosition;
            default:
                // No Actor collision
                return position;
        }
    }

    /**
     * Checks for a collision occurring between an actor and a tile. Depending on either the actor
     * or the tile the interaction changes significantly. It will return the original actor's new
     * position taking into account any tiles it may have interacted with.
     *
     * @param position          The new position of the actor.
     * @param originalPosition  The original position of the actor.
     * @param collisionTileType The tile type of the tile the actor collided with.
     * @param actorType         The actors type.
     * @return The new coordinates for the actor.
     */
    private int[] collisionOccurredTile(int[] position, int[] originalPosition, TileType collisionTileType,
                                        ActorType actorType) {
        switch (collisionTileType) {
            case WALL:
                return originalPosition;
            case DIRT:
                if (actorType != ActorType.PLAYER && actorType != ActorType.BLOCK) {
                    return originalPosition;
                } else {
                    return position;
                }
            case ICE, ICEBL, ICEBR, ICETL, ICETR:
                if (actorType == ActorType.PLAYER || actorType == ActorType.BLOCK) {
                    nextMove = TileLayer.convertIceDirection(nextMove, collisionTileType);
                    int[] newerPosition = getNewPosition(position[0], position[1], nextMove);
                    if (!collisionHandler.isValidMove(newerPosition)) {
                        return originalPosition;
                    }
                    if (actorType == ActorType.PLAYER) {
                        collisionOccurredItem(position);
                    }
                    // Recursively run through collisionOccurredTile until the player is no longer on ice
                    return collisionOccurredTile(newerPosition, originalPosition,
                            collisionHandler.checkForTileCollision(newerPosition), actorType);
                } else {
                    return originalPosition;
                }
            case WATER:
                if (actorType == ActorType.PLAYER) {
                    killPlayer();
                    return originalPosition;
                } else if (actorType == ActorType.BLOCK) {
                    return position;
                } else {
                    return originalPosition;
                }
            case DOOR:
                if (actorType == ActorType.PLAYER) {
                    Door currentDoor = (Door) currentLevel.getTileLayer().getTileAt(position[0], position[1]);
                    for (Key key : inventory) {
                        if (key.getColour() == currentDoor.getColour()) {
                            currentLevel.getTileLayer().setTileAt(position[0], position[1], new Path());
                            return position;
                        }
                    }
                }
                return originalPosition;
            case CHIPSOCKET:
                // Checks if the player currently has enough chips to unlock the chip socket
                if (actorType == ActorType.PLAYER) {
                    ChipSocket chipSocket = (ChipSocket) currentLevel.getTileLayer().getTileAt(
                            position[0], position[1]);

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
                if (actorType == ActorType.PLAYER) {
                    exitReached();
                    // For when block is pushed onto exit via ice
                } else if (actorType == ActorType.BLOCK) {
                    return originalPosition;
                }
            default:
                return position;
        }
    }

    /**
     * Calculates the players high score and inputs it to the high score file, (if the score is higher than
     * at least the 10th player). It will also send the user to the victory after screen and update
     * their current highest level reached.
     */
    private void exitReached() {
        currentUser.setHighestLevelNum(currentLevel.getLevelNum());
        currentUser.setCurrentLevel(null);
        double score = currentTime * (chipCount + 1);
        HighScore currentHighScore = new HighScore(currentLevel.getLevelNum());
        currentHighScore.uploadNewScore(score, currentUser.getUserName());
        System.out.println(currentHighScore.getScoreboard());
        LogIn.updateUser(currentUser.getUserName(), currentUser.getHighestLevelNum(),
                currentUser.getCurrentLevel());

        try {
            switchToVictoryScreen(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the player collided with any items at their position. If they have it will either
     * add a chip to their chip count or add the key to their inventory.
     *
     * @param position
     */
    private void collisionOccurredItem(int[] position) {
        Item possibleItem = currentLevel.getItemLayer().getItemAt(position[0], position[1]);
        if (possibleItem.getType() != ItemType.NOTHING) {
            currentLevel.getItemLayer().removeItem(position[0], position[1]);
            if (possibleItem.getType() == ItemType.CHIP) {
                chipCount++;
            } else {
                inventory.add((Key) possibleItem);
                drawInventory();
            }
        }
    }

    /**
     * Main method required to start application window.
     *
     * @param args Command line arguments to pass into main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
