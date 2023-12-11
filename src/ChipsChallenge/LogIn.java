package ChipsChallenge;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * The LogIn class provides the main functionality for user login or to add a new player.
 *
 * @author Emma Bowman
 */
public class LogIn {

    /**
     * Logs in a user and returns a User object. Checks if the user is already in the system,
     * if not then a new User is created with their highest level = 0.
     *
     * @param userName The username of the user to log in.
     * @return A User object representing the logged-in user.
     */
    public static User logInUser(String userName) {
        String fileName = "Users.txt";
        boolean found = false;
        try {
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
                try {
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
     * @param userName        The username of the user to update.
     * @param highestLevelNum The highest level reached by the user.
     * @param currentLevel    The current level of the user.
     */
    public static void updateUser(String userName, int highestLevelNum, Level currentLevel) {
        String tempUsers = "tempUsers.txt";
        File oldFile = new File("Users.txt");
        File newFile = new File(tempUsers);
        try {
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            Scanner scanner = new Scanner(new File("Users.txt"));

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts[0].equals(userName)) {
                    printWriter.write(userName + "," + highestLevelNum);
                } else {
                    printWriter.write(parts[0] + "," + parts[1]);
                }
                printWriter.write("\n");
            }
            scanner.close();
            printWriter.flush();
            printWriter.close();
            if (oldFile.delete()) {
                File dump = new File("Users.txt");
                if (!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch (IOException e) {
            System.out.println("Problem");
        }
    }
}