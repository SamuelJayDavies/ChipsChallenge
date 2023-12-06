package ChipsChallenge;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * The Bug class represents a type of monster in the game Chips Challenge.
 * Bugs can only travel over specific tiles and follow the left or right edge of the path.
 * They are prevented from moving onto blocking tiles, other monsters, or out-of-bounds positions.
 */

public class Bug extends Actor {

    private KeyCode followDirection;
    private KeyCode direction;

    /**
     * Constructs a new Bug instance.
     * Initializes the Bug with the BUG type and a default image.
     * Sets the initial direction to follow the left edge.
     */

    public Bug() {
        super(ActorType.BUG, new Image("images/stuff/bug.png"));
        this.followDirection = KeyCode.D;
        this.direction = KeyCode.A;
    }

    public KeyCode getFollowDirection() {
        return followDirection;
    }

    public void setFollowDirection(KeyCode followDirection) {
        this.followDirection = followDirection;
    }

    public KeyCode getDirection() {
        return direction;
    }

    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    public void turnBug(int numberOfTimes) {
        for(int i=0; i<numberOfTimes; i++) {
            if(this.followDirection == KeyCode.A) {
                switch(direction) {
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
                switch(direction) {
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