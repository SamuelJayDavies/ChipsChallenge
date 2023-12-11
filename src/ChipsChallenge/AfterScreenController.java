package ChipsChallenge;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the GUI screen that appears after the player has won or lost their current level.
 * The GUI changes depending on if the user won or lost the level they were originally playing.
 *
 * @author Samuel Davies
 */
public class AfterScreenController extends Application {

    /**
     * Button offset required for clear visualisation.
     */
    private static final double BUTTON_OFFSET = 10;

    /**
     * The main stage of the GUI runtime.
     */
    private static Stage stage;

    /**
     * The current user logged into the game.
     */
    private static User currentUser;

    /**
     * The title message that will display in the center of the screen.
     */
    private static String titleMsg;

    /**
     * The side message that will appear below the main title message.
     */
    private static String message;

    /**
     * Boolean for if the user died in the previous level.
     */
    private static boolean isDead;

    /**
     * Foreground component of the main title message.
     */
    @FXML
    public Label titleNameForeground;

    /**
     * Background component of the main title message.
     */
    @FXML
    public Label titleNameBackground;

    /**
     * Foreground component of the side message.
     */
    @FXML
    public Label messageForeground;

    /**
     * Background component of the main title message.
     */
    @FXML
    public Label messageBackground;

    /**
     * VBox that holds the title labels.
     */
    @FXML
    public VBox titleVBox;

    /**
     * HBox that holds the buttons in line.
     */
    @FXML
    public HBox buttonHBox;

    /**
     * Main start method that creates and initialises the after screen scene.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException When the resource file can't be found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/after-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialises components needed for the GUI to function as intended.
     */
    @FXML
    private void initialize() {
        // If the player has died, the title message would have changed
        if (titleMsg != null) {
            titleNameForeground.setText(titleMsg);
            titleNameBackground.setText(titleMsg);
            messageForeground.setText(message);
            messageBackground.setText(message);
            if (isDead) {
                titleVBox.setLayoutX(titleVBox.getLayoutX() - 20);
            } else {
                titleVBox.setLayoutX(titleVBox.getLayoutX() - 75);
                // Create button for player to try level again on loss
                Button playAgain = new Button("Play Again?");
                playAgain.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        currentUser.setHighestLevelNum(currentUser.getHighestLevelNum() - 1);
                        try {
                            switchToGame(event);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                playAgain.setPadding(new Insets(BUTTON_OFFSET, BUTTON_OFFSET, BUTTON_OFFSET, BUTTON_OFFSET));
                buttonHBox.getChildren().add(playAgain);
            }
        }
    }


    /**
     * Changes the current scene to the main game scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException For when the resource file can't be found.
     */
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
     * Changes the current scene to the main game scene.
     *
     * @param event The action event that caused the method to fire.
     * @throws IOException For when the resource file can't be found.
     */
    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainMenuController.setStage(stage);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets the current JavaFX Stage.
     *
     * @return The JavaFX Stage object.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the current JavaFX Stage.
     *
     * @param stage The JavaFX Stage to set.
     */
    public static void setStage(Stage stage) {
        AfterScreenController.stage = stage;
    }

    /**
     * Gets the current user associated with the controller.
     *
     * @return The User object representing the current user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user associated with the controller.
     *
     * @param currentUser The User object to set as the current user.
     */
    public static void setCurrentUser(User currentUser) {
        AfterScreenController.currentUser = currentUser;
    }

    /**
     * Gets the title message used in the controller.
     *
     * @return The title message as a String.
     */
    public static String getTitleMsg() {
        return titleMsg;
    }

    /**
     * Sets the title message for the controller.
     *
     * @param titleMsg The String to set as the title message.
     */
    public static void setTitleMsg(String titleMsg) {
        AfterScreenController.titleMsg = titleMsg;
    }

    /**
     * Gets the general message used in the controller.
     *
     * @return The message as a String.
     */
    public static String getMessage() {
        return message;
    }

    /**
     * Sets the general message for the controller.
     *
     * @param message The String to set as the message.
     */
    public static void setMessage(String message) {
        AfterScreenController.message = message;
    }

    /**
     * Checks if the user died during their gameplay.
     *
     * @return {@code true} if the user died, {@code false} otherwise.
     */
    public static boolean isIsDead() {
        return isDead;
    }

    /**
     * Sets the state of the after screen controller to be dead or not.
     *
     * @param isDead {@code true} to set the controller in the "dead" state, {@code false} otherwise.
     */
    public static void setIsDead(boolean isDead) {
        AfterScreenController.isDead = isDead;
    }

}
