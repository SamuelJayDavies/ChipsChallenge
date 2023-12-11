package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a computer chip that can be picked up by the player and used to
 * collect score or open chipsockets.
 * All chips have the same value of 1.
 *
 * @author Samuel Davies
 */
class Chip extends Item {

    /**
     * Creates a new computer chip with a default image.
     */
    public Chip() {
        super(ItemType.CHIP, new Image("images/stuff/computer chip.png"));
    }
}
