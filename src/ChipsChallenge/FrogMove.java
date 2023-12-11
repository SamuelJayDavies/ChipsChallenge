package ChipsChallenge;

import javafx.scene.input.KeyCode;

/**
 * Object to store current frog move in terms of the direction and the amount of steps taken.
 *
 * @author Samuel Davies
 */
public class FrogMove {

    /**
     * The amount of steps taken during this move.
     */
    private int stepsTaken;

    /**
     * The direction the frog is moving.
     */
    private KeyCode direction;

    /**
     * Create a new frog move with a number of steps taken and an initial direction.
     *
     * @param stepsTaken The amount of steps taken.
     * @param direction  The direction the frog is moving.
     */
    public FrogMove(int stepsTaken, KeyCode direction) {
        this.stepsTaken = stepsTaken;
        this.direction = direction;
    }

    /**
     * Returns the amount of steps taken.
     *
     * @return The amount of steps taken.
     */
    public int getStepsTaken() {
        return stepsTaken;
    }

    /**
     * Sets the amount of steps taken.
     *
     * @param stepsTaken The amount of steps taken.
     */
    public void setStepsTaken(int stepsTaken) {
        this.stepsTaken = stepsTaken;
    }

    /**
     * Returns the current direction the frog is moving in.
     *
     * @return The frogs current direction.
     */
    public KeyCode getDirection() {
        return direction;
    }

    /**
     * Sets the frogs current direction.
     *
     * @param direction The frogs current direction.
     */
    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }
}
