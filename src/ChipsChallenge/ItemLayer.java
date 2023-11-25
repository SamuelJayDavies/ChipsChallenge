package ChipsChallenge;

import java.util.HashMap;
import java.util.Map;

class ItemLayer {
    private Item[][] cells; // Matrix to represent the item layer
    private Map<String, Key> keyMap; // Map to store keys and their colors

    // Constructor to create an ItemLayer with a specified number of rows and columns
    public ItemLayer(int numRows, int numCols) {
        cells = new Item[numRows][numCols];
        keyMap = new HashMap<>();

    }

    // Get the item at a specific cell
    public Item getItem(int row, int col) {
        return cells[row][col];
    }

    // Set an item at a specific cell
    public void setItem(int row, int col, Item item) {
        cells[row][col] = item;
        // If the item is a Key, add it to the keyMap for easy retrieval by color
        if (item instanceof Key) {
            Key key = (Key) item;
            keyMap.put(key.getColor(), key);
        }
    }

    // Retrieve a key by its color from the keyMap
    public Key getKeyByColor(String color) {
        return keyMap.get(color);
    }
}
