package ChipsChallenge;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    @FXML
    private javafx.scene.control.Button returnBtn;

    private HighScore highScore;

    public static int levelNum;

    public static User currentUser;

    public static Stage stage;

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
        usernameID.setCellValueFactory(new PropertyValueFactory<UserScore, String>("Username"));
        scoreID.setCellValueFactory(new PropertyValueFactory<UserScore, Double>("Score"));
        highScore = new HighScore(levelNum);
        ObservableList<UserScore> userScores = FXCollections.observableArrayList(highScore.getScoreboard());
        highscoreTable.getItems().addAll(userScores);
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        GameController.currentUser = currentUser;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setScene(scene);
        stage.show();
        GameController.stage = stage;
    }
}
