package ChipsChallenge;

import javafx.scene.image.Image;

public class Door extends Tile {

    private Colour colour;

    public Door(char colour) {
        super(TileType.DOOR, new Image("images/stuff/door" + colour + ".png"));
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
