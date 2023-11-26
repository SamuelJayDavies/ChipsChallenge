package ChipsChallenge;

import javafx.scene.image.Image;

public class Door extends Tile {

    private Colour colour;

    public Door(Colour colour) {
        super(TileType.DOOR, new Image("images/stuff/doors.png"));
        this.colour = colour;
    }
}
