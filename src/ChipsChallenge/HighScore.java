package ChipsChallenge;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class HighScore {

    private int levelNum;

    private ArrayList<UserScore> highScore;

    public HighScore(int levelNum) {
        this.levelNum = levelNum;
        highScore = new ArrayList<>();
        highScore = readInScoreFile();
        highScore.sort(Comparator.comparingDouble(UserScore::getScore).reversed());
    }

    // Return the current highscore
    public double getCurrentHighscore(){
        return highScore.get(0).getScore();
    }

    // Return the player with the current highscore
    public String getCurrentHighscorePlayer(){
        return highScore.get(0).getUsername();
    }
    
    // Check if a new score from a user beats the current highscore.
    // Also checks if the array already has 10 elements and removes the smallest element
    public void uploadNewScore(double score, String user){
        UserScore newUserScore = new UserScore(user, score);
        if (highScore.size() == 10 && highScore.get(9).getScore() < score) {
            highScore.remove(9);
        }
        if (highScore.size() < 10) {
            int i = 0;
            while (i < highScore.size() && highScore.get(i).getScore() > score) {
                i++;
            }
            highScore.add(i, newUserScore);
        }
        writeToScoreFile();
    }

    // Return the entire scoreboard as an array of objects, storing the username and score.
    public ArrayList<UserScore> getScoreboard(){
        return highScore;
    }

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
            while(in.hasNextLine()) {
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

    public void writeToScoreFile() {
        String tempHighScores = "tempHighScores.txt";
        File oldFile = new File("HighscoreTableLevel" + levelNum + ".txt");
        File newFile = new File(tempHighScores);
        try{
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            for(UserScore score: highScore) {
                printWriter.write(score.getUsername() + "," + score.getScore() + "\n");
            }
            printWriter.flush();
            printWriter.close();
            if(oldFile.delete()) {
                File dump = new File("HighscoreTableLevel" + levelNum + ".txt");
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


}
