package ChipsChallenge;

import com.sun.javafx.scene.traversal.Direction;

public class Frog extends Actor{

    private int x, y;
    public Frog(int x, int y) {
        super(x, y);
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

    public void moveActor() {}
}
