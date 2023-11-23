package ChipsChallenge;

public class TileLayer {
    private Tile[][] tiles;

    public TileLayer(int width, int height) {
        tiles = new Tile[width][height];
        initializeTiles();
    }

    private void initializeTiles() {
        // You can customize this method to initialize tiles based on your game's requirements.
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                String type = (i + j) % 3 == 0 ? "path" : (i + j) % 3 == 1 ? "wall" : "water";
                tiles[i][j] = createTile(type);
            }
        }
    }

    private Tile createTile(String type) {
        // You can expand this method to create different types of tiles based on the type string.
        switch (type) {
            case "path":
                return new PathTile();
            case "wall":
                return new WallTile();
            case "water":
                return new WaterTile();
            default:
                return null;
        }
    }

    public Tile getTileAt(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return tiles[x][y];
        }
        return null;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length;
    }
}