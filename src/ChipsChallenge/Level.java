package ChipsChallenge;

public class Level {

    private TileLayer tileLayer;

    private ItemLayer itemLayer;
    private ActorLayer actorLayer;

    public Level(String levelTxt) {
        this.tileLayer = new TileLayer(/** Section of levelTxt */);
        this.itemLayer = new ItemLayer(/** Section of levelTxt */);
        this.actorLayer = new ActorLayer(/** Section of levelTxt */);
    }
}
