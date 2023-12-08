package ChipsChallenge;

/*
 * Constructs the ChipsChallenge.UserScore class
 * @param username stores the username of the user related to the score
 * @param score stores the actual score achieved by the user
 */
public class UserScore {

    String username;
    double score;

    UserScore(String username, double score){
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
