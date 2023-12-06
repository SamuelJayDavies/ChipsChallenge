package ChipsChallenge;

import javafx.scene.image.Image;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class PinkBall extends Actor {
    private KeyCode direction;

    public PinkBall() {
        super(ActorType.PINKBALL, new Image("images/stuff/pink ball.png"));//, x, y);
    }

    public void setDirection(String direction) {
        switch(direction) {
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

    public KeyCode getDirection() {
        return this.direction;
    }

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
        return getX() + (direction == Direction.RIGHT ? 1 : (direction == Direction.LEFT ? -1 : 0));
    }

    private int calculateNextY() {
        return getY() + (direction == Direction.DOWN ? 1 : (direction == Direction.UP ? -1 : 0));
    }

    private boolean isValidTile(int x, int y) {
        TileType tileType = getTileType(x, y);
        return tileType == TileType.PATH || tileType == TileType.BUTTON || tileType == TileType.TRAP;
    }

    private TileType getTileType(int x, int y) {
        return null;
        //return gameMap[y][x];  // Asuming y represents the row and x represents the column
    }
    */
    public void reverseDirection() {
        direction = (direction == KeyCode.D) ? KeyCode.A :
                    (direction == KeyCode.A) ? KeyCode.D :
                    (direction == KeyCode.W) ? KeyCode.S :
                    KeyCode.W;
    }

}


