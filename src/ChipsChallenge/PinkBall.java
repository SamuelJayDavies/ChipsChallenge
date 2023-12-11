package ChipsChallenge;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The PinkBall class represents a type of actor in the game CaveQuest.
 * PinkBalls can only move on one axis once set and simply bounce back and fourth between
 * block tiles.
 *
 * @author Samuel Davies
 */
public class PinkBall extends Actor {

    /**
     * The current direction of the PinkBall.
     */
    private KeyCode direction;

    /**
     * Constructs a new PinkBall instance with the default image.
     */
    public PinkBall() {
        super(ActorType.PINKBALL, new Image("images/stuff/pink ball.png"));
    }

    /**
     * Sets the direction of the PinkBall based on a string input.
     * Valid directions are "up", "down", "left", and "right".
     *
     * @param direction The string representation of the direction.
     * @throws IllegalArgumentException if an invalid direction is provided.
     */
    public void setDirection(String direction) {
        switch (direction) {
            case "up":
                this.direction = KeyCode.W;
                break;
            case "down":
                this.direction = KeyCode.S;
                break;
            case "left":
                this.direction = KeyCode.A;
                break;
            case "right":
                this.direction = KeyCode.D;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Gets the current direction of the PinkBall.
     *
     * @return The KeyCode representing the current direction.
     */
    public KeyCode getDirection() {
        return this.direction;
    }

    /**
     * Sets the direction of the PinkBall based on a KeyCode input.
     *
     * @param direction The KeyCode representing the new direction.
     */
    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    /**
     * Reverses the current direction of the PinkBall.
     */
    public void reverseDirection() {
        direction = (direction == KeyCode.D) ? KeyCode.A
                : (direction == KeyCode.A) ? KeyCode.D
                : (direction == KeyCode.W) ? KeyCode.S
                : KeyCode.W;
    }

}


