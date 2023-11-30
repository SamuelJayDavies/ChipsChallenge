package ChipsChallenge;

import java.util.Scanner;

public class Level {

    private int levelNum;

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(int width, int height, String[][] layers) {
        this.tileLayer = new TileLayer(width, height, layers[0]);
        this.itemLayer = new ItemLayer(width, height, layers[1]);
        this.actorLayer = new ActorLayer(width, height, layers[2]);
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
