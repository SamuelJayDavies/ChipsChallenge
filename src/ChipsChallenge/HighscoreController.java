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

    /**
     * Tableview for the highscore table. Shows the users name and their score.
     */
    @FXML
    private TableView<UserScore> highscoreTable;

    /**
     * Tablecolumn for the username of the UserScore.
     */
    @FXML
    private TableColumn<UserScore, String> usernameID;

    /**
     * Tablecolumn for the score of the UserScore.
     */
    @FXML
    private TableColumn<UserScore, Double> scoreID;

    /**
     * JavaFX button for returning to the game scene.
     */
    @FXML
    private javafx.scene.control.Button returnBtn;

    /**
     * The highscore object that is storing all the UserScores.
     */
    private HighScore highScore;

    /**
     * The current level number the high score table is related to.
     */
    private static int levelNum;

    /**
     * The current user that is needed to be passed back into the level.
     */
    private static User currentUser;

    /**
     * The main stage for the UI.
     */
    private static Stage stage;

    /**
     * Main start method that creates and initialises the game screen scene.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException When the resource file can't be found.
     */
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

    /**
     * Initialises components needed for the GUI to function as intended.
     */
    @FXML
    private void initialize() {
        usernameID.setCellValueFactory(new PropertyValueFactory<UserScore, String>("Username"));
        scoreID.setCellValueFactory(new PropertyValueFactory<UserScore, Double>("Score"));
        highScore = new HighScore(levelNum);
        ObservableList<UserScore> userScores = FXCollections.observableArrayList(highScore.getScoreboard());
        highscoreTable.getItems().addAll(userScores);
    }

    /**
     * Changes the current scene to the main game scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException When the resource file can't be found.
     */
    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        GameController.setCurrentUser(currentUser);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setScene(scene);
        stage.show();
        GameController.setStage(stage);
    }

    /**
     * Sets the level number of the high score table.
     *
     * @param levelNum The level number for the high score table.
     */
    public static void setLevelNum(int levelNum) {
        HighscoreController.levelNum = levelNum;
    }

    /**
     * Sets the current user that is logged in.
     *
     * @param currentUser The current user in the system.
     */
    public static void setCurrentUser(User currentUser) {
        HighscoreController.currentUser = currentUser;
    }

    /**
     * The current stage being used by all the controllers.
     *
     * @param stage The current JavaFX stage.
     */
    public static void setStage(Stage stage) {
        HighscoreController.stage = stage;
    }
}
