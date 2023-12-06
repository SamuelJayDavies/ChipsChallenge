package ChipsChallenge;

import javafx.scene.input.KeyCode;

import java.util.Scanner;

public class Level {

    private int levelNum;

    private int width;

    private int height;

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(int width, int height, String[][] layers) {
        this.width = width;
        this.height = height;
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
                    }
                } else {
                    System.out.println(1);
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
}
