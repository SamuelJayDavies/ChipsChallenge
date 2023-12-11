package ChipsChallenge;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The HighScore class is used to keep track of the top 10 scores per level, as well as the user who set the scores.
 * It also reads and writes the score to a file so that they are persistent.
 *
 * @author Alfie May, Samuel Davies
 */
public class HighScore {

    /**
     * Maximum amount of scores to be stored in high score table.
     */
    private static final int MAX_ENTRIES = 10;

    /**
     * The level number the highScores are associated with.
     */
    private int levelNum;

    /**
     * The list of high scores for the level.
     */
    private ArrayList<UserScore> highScore;

    /**
     * Constructor to create a new HighScore, creating a new arraylist for the specified level number.
     *
     * @param levelNum The number of the level the HighScore table refers to.
     */
    public HighScore(int levelNum) {
        this.levelNum = levelNum;
        highScore = new ArrayList<>();
        highScore = readInScoreFile();
        highScore.sort(Comparator.comparingDouble(UserScore::getScore).reversed());
    }

    /**
     * Returns the highest score of the current level.
     *
     * @return the current high score for the level.
     */
    public double getCurrentHighscore() {
        return highScore.get(0).getScore();
    }

    /**
     * Returns the user who set the highest score of the current level.
     *
     * @return The username of the player who set the current high score.
     */
    public String getCurrentHighscorePlayer() {
        return highScore.get(0).getUsername();
    }

    /**
     * Checks if the provided score is in the top 10 for that level and writes
     * it to file if it does beat another score.
     *
     * @param score The score the user achieved on the level.
     * @param user  The user who achieved the score.
     */
    public void uploadNewScore(double score, String user) {
        UserScore newUserScore = new UserScore(user, score);
        // If scoreboard is already full, remove the lowest score and replace with new score.
        if (highScore.size() == MAX_ENTRIES && highScore.get(9).getScore() < score) {
            highScore.remove(9);
        }
        // If scoreboard has less than 10 elements, add new score to the list in the corresponding position.
        if (highScore.size() < MAX_ENTRIES) {
            int i = 0;
            while (i < highScore.size() && highScore.get(i).getScore() > score) {
                i++;
            }
            highScore.add(i, newUserScore);
        }
        writeToScoreFile();
    }

    /**
     * Returns an array of the current scoreboard for the level, consisting of
     * UserScores.
     *
     * @return An array of the current levels top 10 UserScores.
     */
    public ArrayList<UserScore> getScoreboard() {
        return highScore;
    }

    /**
     * Loads the highscore table for the current level from the respective text file.
     *
     * @return An array of UserScore objects containing usernames and scores of the top 10 scores.
     */
    public ArrayList<UserScore> readInScoreFile() {
        String fileName = "HighscoreTableLevel" + levelNum + ".txt";
        File highScoreFile = new File(fileName);
        try {
            highScoreFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<UserScore> userScores = new ArrayList<>();
        try {
            Scanner in = new Scanner(highScoreFile);
            while (in.hasNextLine()) {
                String[] parts = in.nextLine().split(",");
                UserScore newScore = new UserScore(parts[0], Double.parseDouble(parts[1]));
                userScores.add(newScore);
            }
            in.close();
            return userScores;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new file and writes the current user scores to the levels high score table.
     */
    public void writeToScoreFile() {
        String tempHighScores = "tempHighScores.txt";
        File oldFile = new File("HighscoreTableLevel" + levelNum + ".txt");
        File newFile = new File(tempHighScores);
        try {
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            for (UserScore score : highScore) {
                printWriter.write(score.getUsername() + "," + score.getScore() + "\n");
            }
            printWriter.flush();
            printWriter.close();
            if (oldFile.delete()) {
                File dump = new File("HighscoreTableLevel" + levelNum + ".txt");
                if (!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
