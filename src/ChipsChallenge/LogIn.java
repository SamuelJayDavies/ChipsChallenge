package ChipsChallenge;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class LogIn extends Application {
    String player;
    int level;
    Stage stage;
    TextField txtUsername;
    TextField txtNewUser;
    Scene sceneAddPlayer;
    Scene sceneWelcome;
    Background bg = new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        // log in
        //creating username label
        Label lb1Username = new Label("Username;");
        txtUsername = new TextField();
        txtUsername.setMinWidth(100);
        txtUsername.setPrefWidth(200);
        txtUsername.setMaxWidth(300);
        txtUsername.setPromptText("Enter username here");

        // Create the log in and sign up buttons
        Button btnLogin = new Button("Log in");
        btnLogin.setPrefWidth(80);
        btnLogin.setOnAction(e -> btnLogin_Click(txtUsername));
        Button btnaddPlayer = new Button("Add Player");
        btnaddPlayer.setPrefWidth(80);
        btnaddPlayer.setOnAction(e -> btnAddPlayer_Click());
        HBox paneButtons = new HBox(10, btnLogin, btnaddPlayer);

        //Create the grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMinWidth(500);
        grid.setPrefWidth(500);
        grid.setMaxWidth(800);

        //add nodes to the pane
        grid.addRow(0, lb1Username, txtUsername);
        grid.add(paneButtons, 1, 2);

        //set alignments
        grid.setHalignment(lb1Username, HPos.RIGHT);
        grid.setColumnSpan(txtUsername, 2);
        grid.setBackground(bg);

        //create scene and stage
        Scene sceneLogin = new Scene(grid);
        primaryStage.setScene(sceneLogin);
        primaryStage.setTitle("logging in");
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(900);
        primaryStage.show();

        //sign up
        Label lb1newUsername = new Label("Username;");
        txtNewUser = new TextField();
        txtNewUser.setMinWidth(100);
        txtNewUser.setPrefWidth(200);
        txtNewUser.setMaxWidth(300);
        txtNewUser.setPromptText("Enter player name here");

        // Create the log in and sign up buttons
        Button btnLogin2 = new Button("Add player");
        btnLogin2.setPrefWidth(80);
        btnLogin2.setOnAction(e -> btnLogin2_Click(txtNewUser));
        HBox paneButtons2 = new HBox(10, btnLogin2);

        //Create the grid layout
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10));
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setMinWidth(500);
        grid2.setPrefWidth(500);
        grid2.setMaxWidth(800);

        //add nodes to the pane
        grid2.addRow(0, lb1newUsername, txtNewUser);
        grid2.add(paneButtons2, 2, 2);

        //set alignments
        grid2.setHalignment(lb1newUsername, HPos.RIGHT);
        grid2.setColumnSpan(txtNewUser, 2);
        grid2.setBackground(bg);

        sceneAddPlayer = new Scene(grid2);

        Text txtWelcome = new Text("Game");
        txtWelcome.setCache(true);
        txtWelcome.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        txtWelcome.setFill(Color.BLACK);
        StackPane root = new StackPane();
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().addAll(txtWelcome);
        StackPane.setAlignment(txtWelcome, Pos.CENTER);
        root.setBackground(bg);

        sceneWelcome = new Scene(root, 500, 500);
    }


    public void btnLogin_Click(TextField txtUsername) {
        String username = txtUsername.getText();
        String fileName = "Users.txt";
        try {
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int playerLevel = Integer.parseInt(parts[1]);
                if (username.equals(name)) {
                    System.out.println("Name found! Level: " + playerLevel);
                    stage.setScene(sceneWelcome);
                    player = username;
                    level = playerLevel;
                    break;
                } else {
                    stage.setScene(sceneAddPlayer);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public void btnLogin2_Click(TextField txtNewUser) {//check if playername already exists ??
        String username = txtNewUser.getText();
        String fileName = "Users.txt";
        Boolean found = false;
        try {//check if player exists
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int playerLevel = Integer.parseInt(parts[1]);
                if (username.equals(name)) {
                    System.out.println("Name found! Level: " + playerLevel);
                    stage.setScene(sceneWelcome);
                    player = username;
                    level = playerLevel;
                    found = true;
                    break;
                }
            }
            if (found != true) {
                try {// if player doesnt exist add a new player
                    FileWriter fileWriter = new FileWriter(fileName, true);
                    BufferedWriter info = new BufferedWriter(fileWriter);
                    info.write(username + ",1");
                    info.newLine();
                    info.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                player = username;
                level = 1;
                stage.setScene(sceneWelcome);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public static User logInUser(String userName) {
        String fileName = "Users.txt";
        boolean found = false;
        try {//check if player exists
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            while (in.hasNextLine() && !found) {
                String line = in.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int playerLevel = Integer.parseInt(parts[1]);
                if (userName.equals(name)) {
                    System.out.println("Name found! Level: " + playerLevel);
                    in.close();
                    return new User(name, playerLevel);
                }
            }
            if (!found && !Objects.equals(userName, "")) {
                try {// if player doesnt exist add a new player
                    FileWriter fileWriter = new FileWriter(fileName, true);
                    BufferedWriter info = new BufferedWriter(fileWriter);
                    info.write(userName + ",1");
                    info.newLine();
                    info.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                in.close();
                return new User(userName, 1);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return null;
    }

    public void btnAddPlayer_Click() {
        stage.setScene(sceneAddPlayer);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

