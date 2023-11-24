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

class PathTile extends Tile {
    public PathTile() {
        super(TileType.PATH, new Image("../images/stuff/path.png"));
    }
}

class WallTile extends Tile {
    public WallTile() {
        super(TileType.WALL, new Image("../images/stuff/wall.png"));
    }
}

class WaterTile extends Tile {
    public WaterTile() {
        super(TileType.WATER, new Image("../images/stuff/lava.png"));
    }
}