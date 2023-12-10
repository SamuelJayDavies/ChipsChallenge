package ChipsChallenge;

import javafx.scene.input.KeyCode;

public class FrogMove {

    private int stepsTaken;

    private KeyCode direction;

    public FrogMove(int stepsTaken, KeyCode direction) {
        this.stepsTaken = stepsTaken;
        this.direction = direction;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public void setStepsTaken(int stepsTaken) {
        this.stepsTaken = stepsTaken;
    }

    public KeyCode getDirection() {
        return direction;
    }

    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }
}
