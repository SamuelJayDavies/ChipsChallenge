package ChipsChallenge;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DeathScreenController extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("../fxml/death-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Unlucky");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        //GameController.currentUser = user;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        //GameController.currentUser = user;
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
        stage.setTitle("Unlucky");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
