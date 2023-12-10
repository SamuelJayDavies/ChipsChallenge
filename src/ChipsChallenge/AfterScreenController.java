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

public class AfterScreenController extends Application {

    public static Stage stage;

    public static User currentUser;

    public static String titleMsg;
    public static String message;

    public static boolean isDead;

    @FXML
    public Label titleNameForeground;

    @FXML
    public Label titleNameBackground;

    @FXML
    public Label messageForeground;

    @FXML
    public Label messageBackground;

    @FXML
    public VBox titleVBox;

    @FXML
    public HBox buttonHBox;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/after-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        if(titleMsg != null) {
            titleNameForeground.setText(titleMsg);
            titleNameBackground.setText(titleMsg);
            messageForeground.setText(message);
            messageBackground.setText(message);
            if(isDead) {
                titleVBox.setLayoutX(titleVBox.getLayoutX() - 20);
            } else {
                titleVBox.setLayoutX(titleVBox.getLayoutX() - 75);
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
                playAgain.setPadding(new Insets(10,10,10,10));
                buttonHBox.getChildren().add(playAgain);
            }
        }
    }

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

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainMenuController.stage = stage;
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
