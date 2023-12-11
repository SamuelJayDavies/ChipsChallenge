package ChipsChallenge;

import javafx.scene.input.KeyCode;


/**
 * Represents a level of CaveQuest including the tile, item and actor layers. The level class acts as a
 * medium between all the layers and the game logic. It also stores information about the level
 * including the time to beat the level, the level number and a description.
 *
 * @author Samuel Davies
 */
public class Level {

    /**
     * The level number of the level.
     */
    private int levelNum;

    /**
     * The level description of the level.
     */
    private String levelDesc;

    /**
     * The time to complete the level.
     */
    private int levelTime;

    /**
     * The width of the level.
     */
    private int width;

    /**
     * The height of the level.
     */
    private int height;

    /**
     * The tileLayer associated with the overall level.
     */
    private TileLayer tileLayer;

    /**
     * The itemLayer associated with the overall level.
     */
    private ItemLayer itemLayer;

    /**
     * The actorLayer associated with the overall level.
     */
    private ActorLayer actorLayer;

    /**
     * Creates a new level object.
     *
     * @param width     The width of the level.
     * @param height    The height of the level.
     * @param levelTime The amount of time to complete the level.
     * @param levelNum  The number of the level.
     * @param levelDesc The description of the level.
     * @param layers    The layers that make up the level.
     */
    public Level(int width, int height, int levelTime, int levelNum, String levelDesc, String[][] layers) {
        this.width = width;
        this.height = height;
        this.levelTime = levelTime;
        this.levelNum = levelNum;
        this.levelDesc = levelDesc;
        this.tileLayer = new TileLayer(width, height, layers[0]);
        this.itemLayer = new ItemLayer(width, height, layers[1]);
        this.actorLayer = new ActorLayer(width, height, layers[2]);

        int j = 0;
        for (String row : layers[3]) {
            String[] currentTiles = row.split(",");
            for (int i = 0; i < tileLayer.getTiles().length; i++) {
                if (!currentTiles[i].equals("n")) {
                    String[] interactionCoords = currentTiles[i].split(":");
                    tileLayer.updateTile(tileLayer.getTileAt(i, j), Integer.parseInt(interactionCoords[0]),
                            Integer.parseInt(interactionCoords[1]));
                }
            }
            j++;
        }

        j = 0;
        for (String row : layers[4]) {
            String[] currentActors = row.split(",");
            for (int i = 0; i < currentActors.length; i++) {
                if (!currentActors[i].equals("n")) {
                    String initialDirection = currentActors[i];
                    Actor currentActor = actorLayer.getActor(i, j);
                    if (currentActor.getType() == ActorType.PINKBALL) {
                        PinkBall pinkBall = (PinkBall) currentActor;
                        pinkBall.setDirection(convertStringToDirection(initialDirection));
                    } else if (currentActor.getType() == ActorType.BUG) {
                        String[] directions = initialDirection.split(":");
                        Bug bug = (Bug) currentActor;
                        bug.setBothDirection(convertStringToDirection(directions[0]),
                                convertStringToDirection(directions[1]));
                    }
                }
            }
            j++;
        }
    }

    /**
     * Converts a string direction to a KeyCode depicting that direction.
     *
     * @param direction The string representation of the KeyCode.
     * @return The equivalent KeyCode for the direction.
     */
    private KeyCode convertStringToDirection(String direction) {
        switch (direction) {
            case "up":
                return KeyCode.W;
            case "down":
                return KeyCode.S;
            case "left":
                return KeyCode.A;
            case "right":
                return KeyCode.D;
            default:
                // Should never happen
                throw new IllegalArgumentException();
        }
    }

    /**
     * Converts a KeyCode direction to a String depicting that direction.
     *
     * @param direction The KeyCode representation of the String.
     * @return The equivalent String for the direction.
     */
    public static String convertDirectionToString(KeyCode direction) {
        switch (direction) {
            case D:
                return "right";
            case A:
                return "left";
            case W:
                return "up";
            case S:
                return "down";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the width of the level.
     *
     * @return The width of the level.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the level.
     *
     * @return The height of the level.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the tileLayer of the level.
     *
     * @return The tileLayer of the level.
     */
    public TileLayer getTileLayer() {
        return tileLayer;
    }

    /**
     * Returns the itemLayer of the level.
     *
     * @return The itemLayer of the level.
     */
    public ItemLayer getItemLayer() {
        return itemLayer;
    }

    /**
     * Returns the actorLayer of the level.
     *
     * @return The actorLayer of the level.
     */
    public ActorLayer getActorLayer() {
        return actorLayer;
    }

    /**
     * Returns the level number of the level.
     *
     * @return The level's number.
     */
    public int getLevelNum() {
        return levelNum;
    }

    /**
     * Returns the description of the level.
     *
     * @return The description of the level.
     */
    public String getLevelDesc() {
        return levelDesc;
    }

    /**
     * Returns the time to beat the level.
     *
     * @return Time to beat the level.
     */
    public int getLevelTime() {
        return levelTime;
    }
}
