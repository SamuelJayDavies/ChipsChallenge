package ChipsChallenge;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.Scanner;

public class GameController extends Application {

    @FXML
    private Canvas mainCanvas;

    @FXML
    private HBox levelNameBox;

    @FXML
    private Label levelNumTxt;

    @FXML
    private Label levelNameTxt;

    @FXML
    private HBox gameBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button saveReturnBtn;

    private Level currentLevel;

    private Timeline tickTimeline;

    private KeyCode nextMove;

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
        testFileLoad();
        drawGame();
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    @FXML
    public void testMethod() {
        Game.testFunction();
    }

    @FXML
    public void testFileLoad()  {
        File myFile = new File("src/levels/level2.txt");
        Scanner myReader = null;
        try {  // Change this to just throw fileNotFoundException and crash program
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] dimensionsArr = splitFile(myReader)[0].split(",");
        String[][] layers = new String[3][1];
        for(int i=0; i<3; i++) {
            layers[i] = splitFile(myReader);
        }
        this.currentLevel = new Level(Integer.parseInt(dimensionsArr[0]), Integer.parseInt(dimensionsArr[1]), layers);
    }

    private String[] splitFile(Scanner levelFile) {
        String[] tileArr = new String[7];
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
    }

    @FXML
    public void storeKeyEvent(KeyEvent event) {
        // We change the behaviour depending on the actual key that was pressed.
        nextMove = event.getCode();
        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    public void tick() {
        Player currentPlayer = currentLevel.getActorLayer().getPlayer();
        if(nextMove == KeyCode.W || nextMove == KeyCode.A
                || nextMove == KeyCode.S || nextMove == KeyCode.D) {
            int[] newPosition = getNewPosition(currentPlayer.getX(), currentPlayer.getY());
            currentLevel.getActorLayer().updateActor(currentPlayer, newPosition[0], newPosition[1]);
        }
        nextMove = null;
        drawGame();
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

    public static void main(String[] args) {
        launch(args);
    }
}
