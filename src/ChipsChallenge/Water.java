package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a water tile that will kill the player if stepped into.
 */
public class Water extends Tile {

    /**
     * Creates a new water tile with the default image.
     */
    public Water() {
        super(TileType.WATER, new Image("images/stuff/lava.png"));
    }
}
