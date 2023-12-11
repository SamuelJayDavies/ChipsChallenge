package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The Player class represents the player character in the game.
 * It extends the Actor class and provides functionality specific to the player in terms
 * of tailored movement based on user input.
 *
 * @author Samuel Davies
 */
public class Player extends Actor {

    /**
     * Constructs a new Player object with the default player type and image.
     */

    public Player() {
        super(ActorType.PLAYER, new Image("images/stuff/caveman.png"));
    }
}
