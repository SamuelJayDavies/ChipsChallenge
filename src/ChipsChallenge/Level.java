package ChipsChallenge;

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

        /*
        int j=0;
        for (String row: layers[3]) {
            String[] currentTiles = row.split(","); // Should probably have this in game controller or a
            for (int i=0; i<tileLayer.getTiles().length; i++) {              // specialised file
                //tileLayer.get[j][i] = identifyTile(currentTiles[i]);
            }
            j++;
        }

         */
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
