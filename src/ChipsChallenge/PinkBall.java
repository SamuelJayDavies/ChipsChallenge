package ChipsChallenge;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The PinkBall class represents a type of actor in the game Chips Challenge.
 * PinkBalls move in the game world and can change
 * direction based on user input.
 */
public class PinkBall extends Actor {

    /**
     * The current direction of the PinkBall.
     */
    private KeyCode direction;

    /**
     * Constructs a new PinkBall instance with the
     * PINKBALL type and a default image.
     */
    public PinkBall() {
        super(ActorType.PINKBALL, new Image("images/stuff/pink ball.png"));
    }

    /**
     * Sets the direction of the PinkBall based on a string input.
     * Valid directions are "up", "down", "left", and "right".
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
     * @return The KeyCode representing the current direction.
     */
    public KeyCode getDirection() {
        return this.direction;
    }

    /**
     * Sets the direction of the PinkBall based on a KeyCode input.
     * @param direction The KeyCode representing the new direction.
     */
    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }
    /*
    public void moveActor() {
        int nextX = calculateNextX();
        int nextY = calculateNextY();

        if (isValidTile(nextX, nextY)) {
            setX(nextX);
            setY(nextY);
        } else {
            reverseDirection();
        }
    }

    private int calculateNextX() {
        return getX() + (direction == Direction.RIGHT ? 1 :
         (direction == Direction.LEFT ? -1 : 0));
    }

    private int calculateNextY() {
        return getY() + (direction == Direction.DOWN ?
        1 : (direction == Direction.UP ? -1 : 0));
    }

    private boolean isValidTile(int x, int y) {
        TileType tileType = getTileType(x, y);
        return tileType == TileType.PATH || tileType == TileType.BUTTON
        || tileType == TileType.TRAP;
    }

    private TileType getTileType(int x, int y) {
        return null;
        //return gameMap[y][x];
        // Asuming y represents the row and x represents the column
    }
    */

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


