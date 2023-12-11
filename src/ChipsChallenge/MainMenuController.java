package ChipsChallenge;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller class for the GUI screen that appear when the game is opened. Identifies the user that
 * is going to be playing and redirects them to the game.
 *
 * @author Samuel Davies
 */
public class MainMenuController extends Application {

    /**
     * The main stage of the GUI runtime.
     */
    private static Stage stage;

    /**
     * Label that specifies the title name.
     */
    @FXML
    private Label titleName;

    /**
     * VBox that holds the main portion of the menu screen.
     */
    @FXML
    private VBox mainVBox;

    /**
     * Input box where the username is read.
     */
    @FXML
    private TextField inputBox;

    /**
     * Main start method that creates and initialises the main menu scene.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException When the resource file can't be found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
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

    }

    /**
     * Method that identifies the current user and passes them into the game scene. If the
     * username doesn't exist, it will be created via the LogIn class.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException For when the resource file can't be found.
     */
    public void logIn(ActionEvent event) throws IOException {
        if (!Objects.equals(inputBox.getText(), "")) {
            switchToGame(event, LogIn.logInUser(inputBox.getText()));
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

    /**
     * Changes the current scene to the main game scene.
     *
     * @param event The action event that caused the method to fire.
     * @param user  A valid user in the system.
     * @throws IOException For when the resource file can't be found.
     */
    public void switchToGame(ActionEvent event, User user) throws IOException {
        GameController.setCurrentUser(user);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setScene(scene);
        stage.show();
        GameController.setStage(stage);
    }

    /**
     * Sets the main stage.
     *
     * @param stage The JavaFX Stage object.
     */
    public static void setStage(Stage stage) {
        MainMenuController.stage = stage;
    }
}
