package ChipsChallenge;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private TileLayer testLayer;

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
    }

    @FXML
    public void testMethod() {
        Game.testFunction();
    }

    @FXML
    public void testFileLoad() throws FileNotFoundException {
        File myFile = new File("src/levels/level2.txt");
        Scanner myReader = new Scanner(myFile);
        testLayer = new TileLayer(15,7,myReader);
        System.out.println(testLayer);
    }

    @FXML
    private void drawGame() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        Tile[][] tileLayer = testLayer.getTiles();
        for(int i=0; i<tileLayer.length; i++) {
            for(int j=0; j<tileLayer[i].length; j++) {
                gc.drawImage(tileLayer[i][j].getImage(), j * 50, i * 50);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
