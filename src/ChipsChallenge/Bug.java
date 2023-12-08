package ChipsChallenge;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The Bug class represents a type of monster in the game Chips Challenge.
 * Bugs can only travel over specific tiles and
 * follow the left or right edge of the path.
 * They are prevented from moving onto blocking tiles,
 * other monsters, or out-of-bounds positions.
 */

public class Bug extends Actor {

    /**
     * The KeyCode representing the direction
     * that the bug is currently following.
     */
    private KeyCode followDirection;

    /**
     * The current direction of the PinkBall.
     */
    private KeyCode direction;

    /**
     * Constructs a new Bug instance.
     * Initializes the Bug with the BUG type and a default image.
     * Sets the initial direction to follow the left edge.
     */

    public Bug() {
        super(ActorType.BUG, new Image("images/stuff/bug.png"));
    }

    /**
     * Gets the direction that the bug is currently following.
     * @return The KeyCode representing the follow direction.
     */
    public KeyCode getFollowDirection() {
        return followDirection;
    }

    /**
     * Sets both the follow direction and the current direction of the bug.
     * @param followDirection The KeyCode representing the follow direction.
     * @param direction       The KeyCode representing the current direction.
     */
    public void setBothDirection(KeyCode followDirection, KeyCode direction) {
        this.followDirection = followDirection;
        this.direction = direction;
    }

    /**
     * Sets the follow direction of the bug.
     * @param followDirection The KeyCode representing the follow direction.
     */
    public void setFollowDirection(KeyCode followDirection) {
        this.followDirection = followDirection;
    }

    /**
     * Gets the current direction of the bug.
     * @return The KeyCode representing the current direction.
     */
    public KeyCode getDirection() {
        return direction;
    }

    /**
     * Sets the current direction of the bug.
     * @param direction The KeyCode representing the current direction.
     */
    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    /**
     * Turns the bug in its current direction a specified number of times.
     * @param numberOfTimes The number of times to turn the bug.
     */
    public void turnBug(int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            if (this.followDirection == KeyCode.A) {
                switch (direction) {
                    case A:
                        this.direction = KeyCode.S;
                        break;
                    case W:
                        this.direction = KeyCode.A;
                        break;
                    case D:
                        this.direction = KeyCode.W;
                        break;
                    case S:
                        this.direction = KeyCode.D;
                        break;
                }
            } else {
                switch (direction) {
                    case A:
                        this.direction = KeyCode.W;
                        break;
                    case W:
                        this.direction = KeyCode.D;
                        break;
                    case D:
                        this.direction = KeyCode.S;
                        break;
                    case S:
                        this.direction = KeyCode.A;
                        break;
                }
            }
        }
    }

    /**
     * Reverses the provided direction.
     * @param direction The KeyCode to reverse.
     * @return The reversed KeyCode.
     */
    public KeyCode reverseDirection(KeyCode direction) {
        return (direction == KeyCode.D) ? KeyCode.A
                : (direction == KeyCode.A) ? KeyCode.D
                : (direction == KeyCode.W) ? KeyCode.S
                : KeyCode.W;
    }
}
