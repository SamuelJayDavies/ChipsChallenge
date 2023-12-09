package ChipsChallenge;

import java.util.ArrayList;

public class SavedLevel {

    private String username;

    private int currentTime;

    private int chipCount;

    private ArrayList<Key> inventory;

    private Level level;

    public SavedLevel(String username, int currentTime, int chipCount, ArrayList<Key> inventory, Level level) {
        this.username = username;
        this.currentTime = currentTime;
        this.chipCount = chipCount;
        this.inventory = inventory;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getChipCount() {
        return chipCount;
    }

    public void setChipCount(int chipCount) {
        this.chipCount = chipCount;
    }

    public ArrayList<Key> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Key> inventory) {
        this.inventory = inventory;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
