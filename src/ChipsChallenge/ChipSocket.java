package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a chipsocket, a door that can only be opened if the player currently owns enough chips.
 * Each chipsockets chips required are specified when they are loaded in.
 *
 * @author Samuel Davies
 */
public class ChipSocket extends Tile {

    /**
     * The chips required to get past the chip socket.
     */
    private int chipsRequired;

    /**
     * Creates a new chipsocket and applies its chips required constraint.
     *
     * @param chipsRequired The amount of chips necessary to get past the chip socket.
     */
    public ChipSocket(int chipsRequired) {
        super(TileType.CHIPSOCKET, new Image("images/stuff/chip socket ancient version.png"));
        this.chipsRequired = chipsRequired;
    }

    /**
     * Returns the amount of chips required to get past the chip socket.
     *
     * @return The amount of chips required to get past the chip socket.
     */
    public int getChipsRequired() {
        return chipsRequired;
    }
}
