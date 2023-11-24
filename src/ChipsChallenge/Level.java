package ChipsChallenge;

public class Level {

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(String levelTxt) {
        this.tileLayer = new TileLayer(2,2, null);
        this.itemLayer = new ItemLayer(/** Section of levelTxt */);
        this.actorLayer = new ActorLayer(/** Section of levelTxt */);
    }
}
