package ChipsChallenge;

public class User {

    private String userName;

    private int highestLevelNum;

    private Level currentLevel;

    public User(String userName, int highestLevelNum) {
        this.userName = userName;
        this.highestLevelNum = highestLevelNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHighestLevelNum() {
        return highestLevelNum;
    }

    public void setHighestLevelNum(int highestLevelNum) {
        this.highestLevelNum = highestLevelNum;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
}
