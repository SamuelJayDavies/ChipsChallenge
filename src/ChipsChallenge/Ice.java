package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents an ice tile that the player can slide across. Only the player and block can use
 * the ice tile. If the player pushes a block onto ice, it will also slide across the ice.
 *
 * @author Samuel Davies
 */
public class Ice extends Tile {

    /**
     * Creates a new ice tile with the default image.
     */
    public Ice() {
        super(TileType.ICE, new Image("images/stuff/ice_no_corners.png"));
    }
}
