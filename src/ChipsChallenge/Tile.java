package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The tile class is a base class for tiles that are created within the tileLayer.
 * It provides a foundation for individual tiles to build upon.
 *
 * @author Mohammed Ahmed, Samuel Davies
 */
public class Tile {

    /**
     * The type of the tile, represented by the TileType enum.
     */
    private final TileType type;

    /**
     * The image associated with the tile.
     */
    private final Image image;

    /**
     * Constructs a new Tile with the specified type and image.
     *
     * @param type  The type of the tile, represented by the TileType enum.
     * @param image The image associated with the tile.
     */
    public Tile(TileType type, Image image) {
        this.type = type;
        this.image = image;
    }

    /**
     * Gets the type of the tile.
     *
     * @return The type of the tile.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Gets the image associated with the tile.
     *
     * @return The image associated with the tile.
     */
    public Image getImage() {
        return image;
    }
}