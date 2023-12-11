package ChipsChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The ItemLayer class represents the layer containing various items in the game.
 * It includes methods to initialize items, identify item types, and retrieve items at specific positions.
 */

class ItemLayer {
    private Item[][] items;
    private Map<String, Key> keyMap; // Map to store keys and their colors

    /**
     * Constructor to create an ItemLayer with a specified number of rows and columns.
     *
     * @param width   The width (number of columns) of the item layer.
     * @param height  The height (number of rows) of the item layer.
     * @param itemArr An array representing the initial configuration of items.
     */
     
    public ItemLayer(int width, int height, String[] itemArr) {
        this.items = new Item[height][width];
        keyMap = new HashMap<>();
        newInitialiseTiles(itemArr);
    }
     /**
     * Initialize items using a Scanner.
     *
     * @param levelScanner The scanner providing input for initializing items.
     */

    private void initialiseItems(Scanner levelScanner) {
        int j=0;
        while (levelScanner.hasNextLine()) {
            String currentRow = levelScanner.nextLine();
            String[] currentItems = currentRow.split(",");
            for (int i=0; i<items[j].length; i++) {
                items[j][i] = identifyItem(currentItems[i]);
            }
            j++;
        }
    }
    /**
     * Initialize items using a String array.
     *
     * @param itemArr An array representing the initial configuration of items.
     */

    private void newInitialiseTiles(String[] itemArr) {
        int j=0;
        for (String itemRow: itemArr) {
            String[] currentItems = itemRow.split(","); // Should probably have this in game controller or a
            for (int i=0; i<items[j].length; i++) {              // specialised file
                items[j][i] = identifyItem(currentItems[i]);
            }
            j++;
        }
    }

    /**
     * Identify an item based on its type.
     *
     * @param type The type of the item.
     * @return The corresponding item object.
     */

    private Item identifyItem(String type) {
        if(type.charAt(0) == 'n') {
            return new Blank(); // Can probably remove this
        } else if(type.charAt(0) == 'k') {
            return new Key(type.charAt(1));
        } else if(type.charAt(0) == 'c') {
            return new Chip();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the item at a specific cell.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The item at the specified cell.
     */

    // Get the item at a specific cell
    public Item getItemAt(int row, int col) {
        return items[col][row];
    }

    /**
     * Get the 2D array of items representing the item layer.
     *
     * @return The 2D array of items.
     */

    public Item[][] getItems() {
        return items;
    }

    /**
     * Remove the item at a specific position in the item layer.
     *
     * @param x The x-coordinate (column index) of the item to be removed.
     * @param y The y-coordinate (row index) of the item to be removed.
     */

    public void removeItem(int x, int y) {
        items[y][x] = new Blank();
    }

    public static String convertItemToString(Item item) {
        switch(item.getType()) {
            case NOTHING:
                return "n";
            case KEY:
                Key currentKey = (Key) item;
                char doorColour = currentKey.getColour().toString().charAt(0);
                return "k" + Character.toLowerCase(doorColour);
            case CHIP:
                return "c";
            default:
                // Every case should have been covered
                return "ERROR";
        }
    }
}
