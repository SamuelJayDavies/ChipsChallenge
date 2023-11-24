package ChipsChallenge;

public class Tile {
    private String type;

    public Tile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

class PathTile extends Tile {
    public PathTile() {
        super("path");
    }
}

class WallTile extends Tile {
    public WallTile() {
        super("wall");
    }
}

class WaterTile extends Tile {
    public WaterTile() {
        super("water");
    }
}