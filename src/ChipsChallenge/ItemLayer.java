package ChipsChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ItemLayer {
    private Item[][] items;
    private Map<String, Key> keyMap; // Map to store keys and their colors

    // Constructor to create an ItemLayer with a specified number of rows and columns
    public ItemLayer(int width, int height, String[] itemArr) {
        this.items = new Item[height][width];
        keyMap = new HashMap<>();
        newInitialiseTiles(itemArr);
    }

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

    private Item identifyItem(String type) {
        if(type.charAt(0) == 'n') {
            return new Blank(); // Can probably remove this
        } else if(type.charAt(0) == 'k') {
            return new Key(type.charAt(1));
        } else if(type.charAt(0) == 'c') {
            return new Chip();
        } else {
            return null;
        }
    }

    // Get the item at a specific cell
    public Item getItemAt(int row, int col) {
        return items[col][row];
    }

    public Item[][] getItems() {
        return items;
    }

    public void removeItem(int x, int y) {
        items[y][x] = new Blank();
    }
}
