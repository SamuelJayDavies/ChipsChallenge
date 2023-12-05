package ChipsChallenge;

import javafx.scene.image.Image;

import java.util.Scanner;

public class TileLayer {
    private Tile[][] tiles;

    public TileLayer(int width, int height, String[] tileArr) {
        this.tiles = new Tile[height][width];
        newInitialiseTiles(tileArr);
    }

    private void initialiseTiles(Scanner levelScanner) {
        int j=0;
        while (levelScanner.hasNextLine()) {
            String currentRow = levelScanner.nextLine();
            String[] currentTiles = currentRow.split(","); // Should probably have this in game controller or a
            for (int i=0; i<tiles[j].length; i++) {              // specialised file
                tiles[j][i] = identifyTile(currentTiles[i]);
            }
            j++;
        }
    }

    private void newInitialiseTiles(String[] tileArr) {
        int j=0;
        for (String tileRow: tileArr) {
            String[] currentTiles = tileRow.split(","); // Should probably have this in game controller or a
            for (int i=0; i<tiles[j].length; i++) {              // specialised file
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
                    return new ChipSocket(Integer.parseInt(type.substring(2)));
                } else if(type.charAt(0) == 'd') {
                    return new Door(type.charAt(1));
                } else {
                    return null;
                }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return tiles[y][x];
        }
        return null;
    }

    public void setTileAt(int x, int y, Tile newTile) {
        tiles[y][x] = newTile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length;
    }

    public void updateTile(Tile currentTile, int x, int y) {
        if(currentTile.getType() == TileType.BUTTON) {
            // We can use typecasting here because we know their type
            Button currentButton = (Button) currentTile;
            Trap currentTrap = (Trap) getTileAt(x, y);
            currentButton.setLinkedTrap(currentTrap);
            currentTrap.setLinkedButton(currentButton);
        }
    }
}