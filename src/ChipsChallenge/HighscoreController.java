package ChipsChallenge;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HighscoreController extends Application {

    @FXML
    private TableView<UserScore> highscoreTable;

    @FXML
    private TableColumn<UserScore, String> usernameID;

    @FXML
    private TableColumn<UserScore, Double> scoreID;

    private HighScore highScore;

    public static int levelNum;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/highscore.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Highscore Table");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("images/stuff/menu.png"));
        stage.show();
    }

    @FXML
    private void initialize() {
        highScore = new HighScore(levelNum);
        ObservableList<UserScore> userScores = FXCollections.observableArrayList(highScore.getScoreboard());
        highscoreTable.getItems().addAll(userScores);
    }
}