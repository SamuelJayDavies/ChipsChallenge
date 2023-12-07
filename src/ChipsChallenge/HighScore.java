package ChipsChallenge;
import java.util.ArrayList;

public class HighScore {

    /* 
     * Constructs the UserScore class
     * @param username stores the username of the user related to the score
     * @param score stores the actual score achieved by the user
     */
    private class UserScore {
        String username;
        int score;

        UserScore(String username, int score){
            this.username = username;
            this.score = score;
        }
    }

    private ArrayList<UserScore> highScore = new ArrayList<>();

    // Return the current highscore
    public int getCurrentHighscore(){
        return highScore.get(0).score;
    }

    // Return the player with the current highscore
    public String getCurrentHighscorePlayer(){
        return highScore.get(0).username;
    }
    
    // Check if a new score from a user beats the current highscore.
    // Also checks if the array already has 10 elements and removes the smallest element
    public void uploadNewScore(int score, String user){
        UserScore newUserScore = new UserScore(user, score);
        if (highScore.size() == 10 && highScore.get(9).score < score) {
            highScore.remove(9);
        }
        if (highScore.size() < 10) {
            int i = 0;
            while (i < highScore.size() && highScore.get(i).score > score) {
                i++;
            }
            highScore.add(i, newUserScore);
        }
    }

    // Return the entire scoreboard as an array of objects, storing the username and score.
    public ArrayList<UserScore> getScoreboard(){
        return highScore;
    }
}
