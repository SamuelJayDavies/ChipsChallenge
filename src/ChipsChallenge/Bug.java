package ChipsChallenge;

import ChipsChallenge.Actor;
import com.sun.javafx.scene.traversal.Direction;

public class Bug extends Actor {

    private int x, y;
    private Direction direction;
    public Bug(int x, int y,Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    @Override
    public void moveActor() {

    }

    private boolean canMoveRight() {
        return true;
    }

    private boolean canMoveLeft() {
        return true;
    }
}
