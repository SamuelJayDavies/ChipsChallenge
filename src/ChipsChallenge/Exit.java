package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents the exit tile that the player is attempting to reach. Once the player touches this tile within
 * a game, the level is completed.
 *
 * @author Samuel Davies
 */
public class Exit extends Tile {

    /**
     * Creates a new exit tile with a default image.
     */
    public Exit() {
        super(TileType.EXIT, new Image("images/stuff/exit.png"));
    }
}
