package ChipsChallenge;

import javafx.scene.image.Image;

import java.util.Scanner;

public class TileLayer {
    private Tile[][] tiles;

    public TileLayer(int width, int height, Scanner levelScanner) {
        this.tiles = new Tile[height][width];
        initialiseTiles(levelScanner);
    }

    private void initialiseTiles(Scanner levelScanner) {
        int j=0;
        while (levelScanner.hasNextLine()) {
            String currentRow = levelScanner.nextLine();
            String[] currentTiles = currentRow.split(",");
            for (int i=0; i<tiles[j].length; i++) {
                tiles[j][i] = identifyTile(currentTiles[i]);
            }
            j++;
        }
    }

    private Tile identifyTile(String type) {
        switch (type) {
            case "p":
                return new Path();
            case "di":
                return new Dirt();
            case "w":
                return new Wall();
            case "e":
                return new Exit();
            case "b":
                return new Button();
            case "t":
                return new Trap();
            case "wt":
                return new Water();
            case "i":
                return new Ice();
            case "itr":
                return new Tile(TileType.ICETR, new Image("images/stuff/iceTR.png"));
            case "itl":
                return new Tile(TileType.ICETL, new Image("images/stuff/iceTL.png"));
            case "ibr":
                return new Tile(TileType.ICEBR, new Image("images/stuff/iceBR.png"));
            case "ibl":
                return new Tile(TileType.ICEBL, new Image("images/stuff/iceBL.png"));
            default:
                if(type.charAt(0) == 'c' && type.charAt(1) == 's') {
                    return new ChipSocket();
                } else if(type.charAt(0) == 'd') {
                    return new Door(Colour.RED);
                } else {
                    return null;
                }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return tiles[x][y];
        }
        return null;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length;
    }
}