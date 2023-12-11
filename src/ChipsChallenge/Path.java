package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a path tile in the tileLayer. Any actor can walk on this tile.
 *
 * @author Samuel Davies
 */
public class Path extends Tile {

    /**
     * Creates a new path with the default image.
     */
    public Path() {
        super(TileType.PATH, new Image("images/stuff/path.png"));
    }
}
