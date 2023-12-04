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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class GameController extends Application {

    @FXML
    public static Stage stage;

    @FXML
    private Canvas mainCanvas;

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

    private ArrayList<Item> inventory; // should probably move this

    private int chipCount = 0;

    private double currentTime = 120;

    public static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        levelNumTxt.setText("level 1");
        levelNameTxt.setText("They hunt in packs");
        inventory = new ArrayList<>();
        testFileLoad();
        drawGame();
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    @FXML
    public void testMethod() {
        Game.testFunction();
    }

    @FXML
    public void testFileLoad()  {
        File myFile = new File("src/levels/level" + currentUser.getHighestLevelNum() + ".txt");
        Scanner myReader = null;
        try {  // Change this to just throw fileNotFoundException and crash program
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] dimensionsArr = splitFile(myReader ,1)[0].split(",");
        String[][] layers = new String[3][1];
        for(int i=0; i<3; i++) {
            layers[i] = splitFile(myReader, Integer.parseInt(dimensionsArr[1]));
        }
        this.currentLevel = new Level(Integer.parseInt(dimensionsArr[0]), Integer.parseInt(dimensionsArr[1]), layers);
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

    public void tick() {
        // Put this into player method
        movePlayer();
        currentTime = currentTime - 0.5;
        nextMove = null;
        drawGame();
    }

    private void movePlayer() {
        Player currentPlayer = currentLevel.getActorLayer().getPlayer();
        if(nextMove == KeyCode.W || nextMove == KeyCode.A
                || nextMove == KeyCode.S || nextMove == KeyCode.D) {
            int[] originalPosition = new int[]{currentPlayer.getX(), currentPlayer.getY()};
            int[] nextPosition = getNewPosition(originalPosition[0], originalPosition[1]);
            if(isValidMove(nextPosition)) {
                int[] finalPosition = collisionOccurredTile(nextPosition, originalPosition, checkForTileCollision(nextPosition));
                currentLevel.getActorLayer().updateActor(currentPlayer, finalPosition[0], finalPosition[1]);
                collisionOccurredItem(nextPosition);
                // Move this somewhere nicer later
                if (currentLevel.getTileLayer().getTileAt(originalPosition[0], originalPosition[1]).getType() == TileType.DIRT) {
                    currentLevel.getTileLayer().setTileAt(originalPosition[0], originalPosition[1], new Path());
                }
            }
        }
    }

    private int[] getNewPosition(int x, int y) {
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

    private int[] collisionOccurredTile(int[] position, int[] originalPosition, TileType tileType) {
        switch (tileType) {
            case WALL:
                return originalPosition;
            case ICE, ICEBL, ICEBR, ICETL, ICETR:
                nextMove = convertIceDirection(nextMove, tileType);
                int[] newerPosition = getNewPosition(position[0], position[1]);
                collisionOccurredItem(position); // Bit of code repetition here
                // Make this nicer later
                return collisionOccurredTile(newerPosition, originalPosition, checkForTileCollision(newerPosition));
            case WATER:
                try {
                    switchToMainMenu(new ActionEvent());
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
            inventory.add(possibleItem);
            currentLevel.getItemLayer().removeItem(position[0], position[1]);
            if(possibleItem.getType() == ItemType.CHIP) {
                chipCount++;
            }
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main-menu.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
