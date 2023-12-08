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

public class MainMenuController extends Application {

    @FXML
    private Label titleName;

    @FXML
    private VBox mainVBox;

    @FXML
    private TextField inputBox;

    public static Stage stage;

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

    @FXML
    private void initialize() {

    }

    public void logIn(ActionEvent event) throws IOException {
        if(!Objects.equals(inputBox.getText(), "")) {
            switchToGame(event, LogIn.logInUser(inputBox.getText()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void switchToGame(ActionEvent event, User user) throws IOException {
        GameController.currentUser = user;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        GameController.currentUser = user;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CaveQuest");
        stage.setScene(scene);
        stage.show();
        GameController.stage = stage;
    }
}
