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
     * The KeyCode representing the current direction of the bug.
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
     * Gets the direction that the bug is following.
     * @return The follow direction.
     */
    public KeyCode getFollowDirection() {
        return followDirection;
    }

    /**
     * Sets the direction for the bug to follow
     * and updates its current direction.
     * @param followDirection The direction for the bug to follow.
     */
    public void setFollowDirection(KeyCode followDirection) {
        this.followDirection = followDirection;
        this.direction = followDirection;
        turnBug(2);
        direction = followDirection;
        turnBug(2);
    }

    /**
     * Gets the current direction of the bug.
     * @return The current direction.
     */
    public KeyCode getDirection() {
        return direction;
    }

    /**
     * Sets the current direction of the bug.
     * @param direction The new direction for the bug.
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
}
