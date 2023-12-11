package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The block class represents a block actor in the game CaveQuest.
 * The block will move only when a player attempts to move on the same tile.
 *
 * @author Quoc Nguyen, Anouya Mugari
 */
public class Block extends Actor {

    /**
     * Constructs a new Block instance with the BLOCK type and a default image.
     */
    public Block() {
        super(ActorType.BLOCK, new Image("images/stuff/block.png"));
    }
}
