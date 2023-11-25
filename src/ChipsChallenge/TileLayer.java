package ChipsChallenge;

import java.util.Scanner;

public class TileLayer {
    private Tile[][] tiles;

    public TileLayer(int width, int height, Scanner levelScanner) {
        this.tiles = new Tile[width][height];
        initialiseTiles(levelScanner);
    }

    private void initialiseTiles(Scanner levelScanner) {
        while (levelScanner.hasNextLine()) {
            String currentRow = levelScanner.nextLine();
            String[] tiles = currentRow.split(",");
            for (int i=0; i<tiles.length; i++) {

            }
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                String type = (i + j) % 3 == 0 ? "path" : (i + j) % 3 == 1 ? "wall" : "water";
                tiles[i][j] = createTile(type);
            }
        }
    }

    private Tile createTile(String type) {
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