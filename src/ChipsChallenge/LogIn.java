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

/**
 * The LogIn class provides the main functionality for user login or to add a new player.
 */
public class LogIn extends Application {
    // Instance variables
    String player;
    int level;
    Stage stage;
    TextField txtUsername;
    TextField txtNewUser;
    Scene sceneAddPlayer;
    Scene sceneWelcome;
    Background bg = new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * The start method is called when the application is launched.This is also where all the buttons
     * and text boxes etc. are created to be used in the made log in stage.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an exception occurs during the execution.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        //creating username label
        Label lb1Username = new Label("Username;");
        txtUsername = new TextField();
        txtUsername.setMinWidth(100);
        txtUsername.setPrefWidth(200);
        txtUsername.setMaxWidth(300);
        txtUsername.setPromptText("Enter username here");

        // Create the log-in and add player buttons
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

        //add player
        Label lb1newUsername = new Label("Username;");
        txtNewUser = new TextField();
        txtNewUser.setMinWidth(100);
        txtNewUser.setPrefWidth(200);
        txtNewUser.setMaxWidth(300);
        txtNewUser.setPromptText("Enter player name here");

        // Create the log-in button
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

        //scene to show where the game would start for testing
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

    /**
     * Handles the login button click event. opens the file storing all the usernames
     * and when user is found it gets what level they are on.
     *
     * @param txtUsername The TextField for entering the username.
     */
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

    /**
     * Handles the add player button click event. if they want to add a player
     * it checks if the player name exists and if not it add them to the file
     * and starts them at level 1.
     *
     * @param txtNewUser The TextField for entering the new player's name.
     */
    public void btnLogin2_Click(TextField txtNewUser) {
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

    /**
     * Logs in a user and returns a User object. Similar idea to the others it checks if the
     * player exists if they do then it gets the level if not it adds the player
     * to the file and starts them at level 1
     *
     * @param userName The username of the user to log in.
     * @return A User object representing the logged-in user.
     */
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
                    info.write(userName + ",0");
                    info.newLine();
                    info.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                in.close();
                return new User(userName, 0);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return null;
    }


    /**
     * Updates user information in the "Users.txt" file.
     *
     * @param userName         The username of the user to update.
     * @param highestLevelNum The highest level reached by the user.
     * @param currentLevel     The current level of the user.
     */
    public static void updateUser(String userName, int highestLevelNum, Level currentLevel) {
        String tempUsers = "tempUsers.txt";
        File oldFile = new File("Users.txt");
        File newFile = new File(tempUsers);
        try{
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            Scanner scanner = new Scanner(new File("Users.txt"));

            while(scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if(parts[0].equals(userName)) {
                    printWriter.write(userName + "," + highestLevelNum);
                    // Add level here later
                } else {
                    printWriter.write(parts[0] + "," + parts[1]);
                }
                printWriter.write("\n");
            }
            scanner.close();
            printWriter.flush();
            printWriter.close();
            if(oldFile.delete()) {
                File dump = new File("Users.txt");
                if(!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch(IOException e) {
            System.out.println("Problem");
        }
    }

    /**
     * Handles the Add Player button click event.
     */
    public void btnAddPlayer_Click() {
        stage.setScene(sceneAddPlayer);
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

