package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The Frog class represents a type of monster in the game CaveQuest.
 * Frogs attempt to reach the player via the quickest possible route. If they are unable
 * to do this, they will move in the general direction of the player.
 *
 * @author Mohammed Ahmed
 */
public class Frog extends Actor {

    /**
     * Constructs a new Frog instance with the FROG type and a default image.
     */
    public Frog() {
        super(ActorType.FROG, new Image("images/stuff/frog.png"));
    }
}
