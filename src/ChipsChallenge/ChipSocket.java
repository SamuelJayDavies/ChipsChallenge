package ChipsChallenge;

import javafx.scene.image.Image;

public class ChipSocket extends Tile {

    private int chipsRequired;

    public ChipSocket(int chipsRequired) {
        super(TileType.CHIPSOCKET, new Image("images/stuff/chip socket ancient version.png"));
        this.chipsRequired = chipsRequired;
    }

    public int getChipsRequired() {
        return chipsRequired;
    }

    public void setChipsRequired(int chipsRequired) {
        this.chipsRequired = chipsRequired;
    }
}
