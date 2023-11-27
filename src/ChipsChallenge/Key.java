package ChipsChallenge;

import ChipsChallenge.Item;
import javafx.scene.image.Image;

class Key extends Item {
    private char color; // Color of the key

    // Constructor to create a Key with a specific color at a certain location
    public Key(char color) {
        // Calls the constructor of the Item class with the name "Key" and the specified location
        super(ItemType.KEY, new Image("images/stuff/keys.png"));
        this.color = color;
    }
}
