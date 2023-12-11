package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a wall tile that cannot be passed through by any actor.
 */
public class Wall extends Tile {

    /**
     * Creates a new wall tile with a default image.
     */
    public Wall() {
        super(TileType.WALL, new Image("images/stuff/wall.png"));
    }
}
