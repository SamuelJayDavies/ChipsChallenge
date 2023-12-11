package ChipsChallenge;

/**
 * Represents a user including their username, the highest level they've achieved and the currentLevel they
 * may have saved.
 */
public class User {

    /**
     * The User's username.
     */
    private String userName;

    /**
     * The highest level the user has reached.
     */
    private int highestLevelNum;

    /**
     * The current level a user may have saved.
     */
    private Level currentLevel;

    /**
     * Creates a new user object with their username and highest level.
     *
     * @param userName        The username of the user.
     * @param highestLevelNum The highest level the user has achieved.
     */
    public User(String userName, int highestLevelNum) {
        this.userName = userName;
        this.highestLevelNum = highestLevelNum;
    }

    /**
     * Returns the user's username.
     *
     * @return The user's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the highest level the user has achieved.
     *
     * @return The highest level the user has achieved.
     */
    public int getHighestLevelNum() {
        return highestLevelNum;
    }

    /**
     * Sets the highest level the user has achieved.
     *
     * @param highestLevelNum The highest level the user has now achieved.
     */
    public void setHighestLevelNum(int highestLevelNum) {
        this.highestLevelNum = highestLevelNum;
    }

    /**
     * Gets the current level the user may have saved.
     *
     * @return The current level the user may have saved.
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level the user has saved.
     *
     * @param currentLevel The current level the user has saved.
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
}
