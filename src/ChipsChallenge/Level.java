package ChipsChallenge;

public class Level {

    private int levelNum;

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(String levelTxt) {
        this.tileLayer = new TileLayer(2,2, null);
        this.itemLayer = new ItemLayer(2,2, null);
        this.actorLayer = new ActorLayer(2,2);
    }
}
