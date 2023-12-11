package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a key item that can be picked up by the player to open corresponding doors. If the
 * door colour and key colour are the same, the door can be opened with that key.
 *
 * @author Samuel Davies
 */
class Key extends Item {

    /**
     * Colour of the key.
     */
    private Colour colour; // Color of the key

    /**
     * Creates a new key with an image tailored to the specific colour passed into the constructor.
     *
     * @param colour The colour of the key as a char.
     */
    public Key(char colour) {
        super(ItemType.KEY, new Image("images/stuff/key" + colour + ".png"));
        switch (colour) {
            case 'r' -> this.colour = Colour.RED;
            case 'b' -> this.colour = Colour.BLUE;
            case 'y' -> this.colour = Colour.YELLOW;
            case 'g' -> this.colour = Colour.GREEN;
        }
    }

    /**
     * Returns the colour of the door as a type.
     *
     * @return The colour of the door.
     */
    public Colour getColour() {
        return colour;
    }
}
