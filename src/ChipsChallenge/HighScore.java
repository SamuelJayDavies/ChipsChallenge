package ChipsChallenge;
import java.util.ArrayList;

public class HighScore {
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
    public void uploadNewScore(int score, String user){
        UserScore newUserScore = new UserScore(user, score);
        int i = 0;
        while (i < highScore.size() && highScore.get(i).score > score) {
            i++;
        }
        highScore.add(i, newUserScore);
    }
}
