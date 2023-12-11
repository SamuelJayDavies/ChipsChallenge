package ChipsChallenge;

import javafx.scene.input.KeyCode;

import java.util.Scanner;

public class Level {

    private int levelNum;

    private String levelDesc;

    private int levelTime;

    private int width;

    private int height;

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(int width, int height, int levelTime, int levelNum, String levelDesc, String[][] layers) {
        this.width = width;
        this.height = height;
        this.levelTime = levelTime;
        this.levelNum = levelNum;
        this.levelDesc = levelDesc;
        this.tileLayer = new TileLayer(width, height, layers[0]);
        this.itemLayer = new ItemLayer(width, height, layers[1]);
        this.actorLayer = new ActorLayer(width, height, layers[2]);

        int j=0;
        for (String row: layers[3]) {
            String[] currentTiles = row.split(","); // Should probably have this in game controller or a
            for (int i=0; i<tileLayer.getTiles().length; i++) {
                if(!currentTiles[i].equals("n")) {
                    String[] interactionCoords = currentTiles[i].split(":");
                    tileLayer.updateTile(tileLayer.getTileAt(i, j), Integer.parseInt(interactionCoords[0]),
                            Integer.parseInt(interactionCoords[1]));
                }
            }
            j++;
        }

        j=0;
        for (String row: layers[4]) {
            String[] currentActors = row.split(","); // Should probably have this in game controller or a
            for (int i=0; i<currentActors.length; i++) {
                if(!currentActors[i].equals("n")) {
                    String initialDirection = currentActors[i];
                    Actor currentActor = actorLayer.getActor(i, j);
                    if(currentActor.getType() == ActorType.PINKBALL) {
                        PinkBall pinkBall = (PinkBall) currentActor;
                        pinkBall.setDirection(convertStringToDirection(initialDirection));
                    } else if(currentActor.getType() == ActorType.BUG) {
                        String[] directions = initialDirection.split(":");
                        Bug bug = (Bug) currentActor;
                        bug.setBothDirection(convertStringToDirection(directions[0]), convertStringToDirection(directions[1]));
                    }
                }
            }
            j++;
        }
    }

    private KeyCode convertStringToDirection(String direction) {
        switch(direction) {
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

    public static String convertDirectionToString(KeyCode direction) {
        switch(direction) {
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileLayer getTileLayer() {
        return tileLayer;
    }

    public ItemLayer getItemLayer() {
        return itemLayer;
    }

    public ActorLayer getActorLayer() {
        return actorLayer;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public int getLevelTime() {
        return levelTime;
    }
}
