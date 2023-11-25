package ChipsChallenge;

import javafx.scene.image.Image;

public class Tile {
    private final TileType type;

    private final Image image;

    public Tile(TileType type, Image image) {
        this.type = type;
        this.image = image;
    }

    public TileType getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }
}