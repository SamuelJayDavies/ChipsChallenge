package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a door in the game CaveQuest. A specific door can only be opened by a key with the
 * same colour, otherwise the door will not let the player move forward.
 *
 * @author Samuel Davies
 */
public class Door extends Tile {

    /**
     * Colour of the door.
     */
    private Colour colour;

    /**
     * Creates a new door with an image tailored to the specific colour passed into the constructor.
     *
     * @param colour The colour of the door as a char.
     */
    public Door(final char colour) {
        super(TileType.DOOR, new Image("images/stuff/door" + colour + ".png"));
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
