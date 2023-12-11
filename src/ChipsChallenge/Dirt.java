package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a dirt tile in the game CaveQuest. Once the player steps off this tile, it will become a path.
 * Monsters cannot walk on dirt.
 *
 * @author Samuel Davies
 */
public class Dirt extends Tile {

    /**
     * Creates a new dirt object with a default image.
     */
    public Dirt() {
        super(TileType.DIRT, new Image("images/stuff/dirt.png"));
    }
}
