package ChipsChallenge;

import java.util.Scanner;

public class TileLayer {
    private Tile[][] tiles;

    public TileLayer(int width, int height, Scanner levelScanner) {
        this.tiles = new Tile[width][height];
        initialiseTiles(levelScanner);
    }

    private void initialiseTiles(Scanner levelScanner) {
        int j=0;
        while (levelScanner.hasNextLine()) {
            String currentRow = levelScanner.nextLine();
            String[] currentTiles = currentRow.split(",");
            for (int i=0; i<tiles.length; i++) {
                tiles[j][i] = identifyTile(currentTiles[i]);
            }
            j++;
        }
        /**
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                String type = (i + j) % 3 == 0 ? "path" : (i + j) % 3 == 1 ? "wall" : "water";
                tiles[i][j] = createTile(type);
            }
        }
         */
    }

    private Tile identifyTile(String type) {
        switch (type) {
            case "p":
                return new PathTile();
            case "w":
                return new WallTile();
            case "wt":
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