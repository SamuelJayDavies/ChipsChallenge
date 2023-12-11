package ChipsChallenge;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The TileLayer class represents a layer of tiles in the game CaveQuest.
 * It manages the arrangement of tiles as a matrix and has various helper methods
 * to deal with updating and changing the tiles.
 *
 * @author Samuel Davies
 */
public class TileLayer {

    /**
     * A matrix representing the grid of tiles in the tileLayer.
     */
    private Tile[][] tiles;

    /**
     * Constructs a new TileLayer with the specified width, height and the
     * initial arrangements of the tiles.
     *
     * @param width   The width of the tile layer grid.
     * @param height  The height of the tile layer grid.
     * @param tileArr An array representing the initial arrangement of tiles in the grid.
     */
    public TileLayer(int width, int height, String[] tileArr) {
        this.tiles = new Tile[height][width];
        initialiseTiles(tileArr);
    }

    /**
     * Creates the tiles matrix with the supplied arrangement of the tiles.
     *
     * @param tileArr An array representing the initial coordinates for the tiles.
     */
    private void initialiseTiles(String[] tileArr) {
        int j = 0;
        for (String tileRow : tileArr) {
            String[] currentTiles = tileRow.split(",");
            for (int i = 0; i < tiles[j].length; i++) {
                tiles[j][i] = identifyTile(currentTiles[i]);
            }
            j++;
        }
    }

    /**
     * Identifies a tile by its equivalent letter and returns it.
     *
     * @param type The letter representing the tile.
     * @return A new instance of the tile.
     */
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
                if (type.charAt(0) == 'c' && type.charAt(1) == 's') {
                    return new ChipSocket(Integer.parseInt(type.substring(2)));
                } else if (type.charAt(0) == 'd') {
                    return new Door(type.charAt(1));
                } else {
                    return null;
                }
        }
    }

    /**
     * Gets the tile at the specified coordinates.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return The actor at the specified coordinates, or null if out of bounds.
     */
    public Tile getTileAt(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return tiles[y][x];
        }
        return null;
    }

    /**
     * Sets the tile at the specified coordinates in the grid.
     *
     * @param x       The X-coordinate.
     * @param y       The Y-coordinate.
     * @param newTile The new tile to set.
     */
    public void setTileAt(int x, int y, Tile newTile) {
        tiles[y][x] = newTile;
    }

    /**
     * Gets the matrix of tiles.
     *
     * @return The 2D array of tiles.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Checks if the specified coordinates are within
     * the valid bounds of the tile layer grid.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return True if the position is valid, false otherwise.
     */
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < tiles[0].length && y >= 0 && y < tiles.length;
    }

    /**
     * Updates the information about a specified tile in the matrix.
     *
     * @param currentTile The tile to update.
     * @param x           The new X-coordinate.
     * @param y           The new Y-coordinate.
     */
    public void updateTile(Tile currentTile, int x, int y) {
        if (currentTile.getType() == TileType.BUTTON) {
            // We can use typecasting here because we know their type
            Button currentButton = (Button) currentTile;
            Trap currentTrap = (Trap) getTileAt(x, y);
            currentButton.setLinkedTrap(currentTrap);
            currentTrap.setLinkedButton(currentButton);
            currentTrap.setX(x);
            currentTrap.setY(y);
        }
    }

    /**
     * Converts a tile into its String representation.
     *
     * @param tile The tile to be converted to its letter form.
     * @return The letter that represents the tile.
     */
    public static String convertTileToString(Tile tile) {
        switch (tile.getType()) {
            case PATH:
                return "p";
            case DIRT:
                return "di";
            case WALL:
                return "w";
            case EXIT:
                return "e";
            case BUTTON:
                return "b";
            case TRAP:
                return "t";
            case WATER:
                return "wt";
            case ICE:
                return "i";
            case ICETR:
                return "itr";
            case ICETL:
                return "itl";
            case ICEBR:
                return "ibr";
            case ICEBL:
                return "ibl";
            case CHIPSOCKET:
                ChipSocket currentTile = (ChipSocket) tile;
                return "cs" + currentTile.getChipsRequired();
            case DOOR:
                Door currentDoor = (Door) tile;
                char doorColour = currentDoor.getColour().toString().charAt(0);
                return "d" + Character.toLowerCase(doorColour);
            default:
                // That should have covered every tile
                throw new IllegalArgumentException();
        }
    }

    /**
     * Takes a direction and ice type and returns which direction it would send the actor. This
     * direction is represented in KeyCode form.
     *
     * @param currentDirection The KeyCode direction the actor is currently heading in.
     * @param tileType         The type of ice they are standing on.
     * @return The new direction the actor will now be facing.
     */
    public static KeyCode convertIceDirection(KeyCode currentDirection, TileType tileType) {
        if (tileType != TileType.ICE) {
            switch (currentDirection) {
                case D:
                    if (tileType.equals(TileType.ICETR)) {
                        return KeyCode.S;
                    } else if (tileType.equals(TileType.ICEBR)) {
                        return KeyCode.W;
                    }
                    break;
                case A:
                    if (tileType.equals(TileType.ICETL)) {
                        return KeyCode.S;
                    } else if (tileType.equals(TileType.ICEBL)) {
                        return KeyCode.W;
                    }
                    break;
                case W:
                    if (tileType.equals(TileType.ICETR)) {
                        return KeyCode.A;
                    } else if (tileType.equals(TileType.ICETL)) {
                        return KeyCode.D;
                    }
                    break;
                case S:
                    if (tileType.equals(TileType.ICEBR)) {
                        return KeyCode.A;
                    } else if (tileType.equals(TileType.ICEBL)) {
                        return KeyCode.D;
                    }
                    break;
                default:
                    return currentDirection;
            }
        }
        return currentDirection;
    }
}