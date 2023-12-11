package ChipsChallenge;

import java.util.ArrayList;

/**
 * Represents a saved state of a level in the Chips Challenge game, including player information,
 * level details, and inventory items.
 *
 * @author Samuel Davies
 */
public class SavedLevel {

    /**
     * The username associated with the saved level.
     */
    private String username;

    /**
     * The current time elapsed in the saved level.
     */
    private int currentTime;

    /**
     * The count of chips collected in the saved level.
     */
    private int chipCount;

    /**
     * The inventory of keys held by the player in the saved level.
     */
    private ArrayList<Key> inventory;

    /**
     * The level object representing the layout and state of the game level.
     */
    private Level level;

    /**
     * Constructs a new SavedLevel instance.
     *
     * @param username    The username associated with the saved level.
     * @param currentTime The current time elapsed in the saved level.
     * @param chipCount   The count of chips collected in the saved level.
     * @param inventory   The inventory of keys held by the player in the saved level.
     * @param level       The level object representing the layout and state of the game level.
     */
    public SavedLevel(String username, int currentTime, int chipCount, ArrayList<Key> inventory, Level level) {
        this.username = username;
        this.currentTime = currentTime;
        this.chipCount = chipCount;
        this.inventory = inventory;
        this.level = level;
    }

    /**
     * Gets the username associated with the saved level.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the current time elapsed in the saved level.
     *
     * @return The current time.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Gets the count of chips collected in the saved level.
     *
     * @return The chip count.
     */
    public int getChipCount() {
        return chipCount;
    }

    /**
     * Gets the inventory of keys held by the player in the saved level.
     *
     * @return The inventory of keys.
     */
    public ArrayList<Key> getInventory() {
        return inventory;
    }

    /**
     * Gets the level object representing the layout and state of the game level.
     *
     * @return The Level object.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the level object representing the layout and state of the game level.
     *
     * @param level The Level object to set.
     */
    public void setLevel(Level level) {
        this.level = level;
    }
}
