package ChipsChallenge;

import ChipsChallenge.Item;

class Key extends Item {
    private String color; // Color of the key

    // Constructor to create a Key with a specific color at a certain location
    public Key(String color, int row, int col) {
        // Calls the constructor of the Item class with the name "Key" and the specified location
        super("Key", row, col);
        this.color = color;
    }

    // Getter to retrieve the color of the key
    public String getColor() {
        return color;
    }
}
