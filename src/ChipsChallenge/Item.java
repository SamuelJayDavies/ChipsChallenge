package ChipsChallenge;

class Item {
    // Common properties
    private String name;
    private int row;
    private int col;

    // Constructor to initialize item properties
    public Item(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    // Getters
    // Retrieve the name of the item
    public String getName() {
        return name;
    }

    // Retrieve the row coordinate of the item
    public int getRow() {
        return row;
    }

    // Retrieve the column coordinate of the item
    public int getCol() {
        return col;
    }
}
