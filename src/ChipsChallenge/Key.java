package ChipsChallenge;

import ChipsChallenge.Item;
import javafx.scene.image.Image;

class Key extends Item {
    private Colour colour; // Color of the key

    // Constructor to create a Key with a specific color at a certain location
    public Key(char colour) {
        // Calls the constructor of the Item class with the name "Key" and the specified location
        super(ItemType.KEY, new Image("images/stuff/keys.png"));
        switch (colour) {
            case 'r' -> this.colour = Colour.RED;
            case 'b' -> this.colour = Colour.BLUE;
            case 'y' -> this.colour = Colour.YELLOW;
            case 'g' -> this.colour = Colour.GREEN;
        }
    }

    public Colour getColour() {
        return colour;
    }
}
