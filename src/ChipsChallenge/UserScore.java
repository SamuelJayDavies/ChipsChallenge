package ChipsChallenge;

/**
 * Class that represents an entry into the high score table. Each entry has a username and score attached.
 */
public class UserScore {

    /**
     * The username associated with the score.
     */
    private String username;

    /**
     * The score associated with the username.
     */
    private double score;

    /**
     * Constructs a new UserScore object including the username and the score achieved.
     *
     * @param username stores the username of the user related to the score
     * @param score    stores the actual score achieved by the user
     */
    UserScore(String username, double score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Returns the username associated with the score.
     *
     * @return The username associated with the score.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the score associated with the username.
     *
     * @return The score associated with the username.
     */
    public double getScore() {
        return score;
    }

}
