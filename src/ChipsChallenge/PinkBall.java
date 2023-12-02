package ChipsChallenge;

import javafx.scene.image.Image;
import com.sun.javafx.scene.traversal.Direction;

public class PinkBall extends Actor {

    private Direction direction;

    public PinkBall(int x, int y, Direction direction) {
        super(x, y, ActorType.PINKBALL, new Image("path_to_pinkball_image")); // Replce  "path_to_pinkball_image" with the actual path
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void moveActor() {
        // Implement the movement logic 
        // Pink Ball should move in a straight line until it hits a blocking tile,
        // then it should reverse its direction and travel backward.
        // The actual logic depends on the game rules and how the tiles are represented.
        // You may need to check the type of the tile in front of the Pink Ball and decide
        // whether it's a blocking tile or not.
    }

    public void reverseDirection() {
        // Implement the logic to reverse the direction of the Pink Ball
        // This method is called when the pink ball hits a blocking tile.
        // You need to change the direction accordingly.
    }
}

