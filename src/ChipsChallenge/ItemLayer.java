package ChipsChallenge;

/**
 * The ItemLayer class represents the layer containing various items in the game.
 * It includes methods to initialize items, identify item types, and retrieve items at specific positions.
 *
 * @author Samuel Davies
 */
class ItemLayer {

    /**
     * A matrix representing the grid of items in the layer.
     */
    private Item[][] items;

    /**
     * Constructor to create an ItemLayer with a specified number of rows and columns and a String
     * representation of the item's layout.
     *
     * @param width   The width (number of columns) of the item layer.
     * @param height  The height (number of rows) of the item layer.
     * @param itemArr An array representing the initial configuration of items.
     */
    public ItemLayer(int width, int height, String[] itemArr) {
        this.items = new Item[height][width];
        initialiseItems(itemArr);
    }

    /**
     * Creates the items matrix with the supplied arrangement of items.
     *
     * @param itemArr An array representing the initial configuration of items.
     */
    private void initialiseItems(String[] itemArr) {
        int j = 0;
        for (String itemRow : itemArr) {
            String[] currentItems = itemRow.split(",");
            for (int i = 0; i < items[j].length; i++) {
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
        if (type.charAt(0) == 'n') {
            return new Blank();
        } else if (type.charAt(0) == 'k') {
            return new Key(type.charAt(1));
        } else if (type.charAt(0) == 'c') {
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

    /**
     * Converts an item into its String representation.
     *
     * @param item The item to be converted to its letter form.
     * @return The letter that represents the item.
     */
    public static String convertItemToString(Item item) {
        switch (item.getType()) {
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
                throw new IllegalArgumentException();
        }
    }
}
