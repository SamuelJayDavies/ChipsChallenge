package ChipsChallenge;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;

//me and ano will make more changes to the code we're
// just checking if everything is correct

/**
 * The Block class represents a block actor in the game Chips Challenge.
 * It is a static object that does not move and serves as an obstacle.
 */
public class Block extends Actor {

    /**
     * Constructs a new Block instance with the BLOCK type and a default image.
     */
    public Block() {
        super(ActorType.BLOCK, new Image("images/stuff/block.png"));
    }

    /**
     * Moves the actor in the specified direction on the game grid.
     * Since Blocks are static, this method does not have any effect.
     *
     * @param direction The direction in which to move the actor.
     * @param level     The current game level.
     */
    public void moveActor(Direction direction, Level level) {
        int nextX = getX();
        int nextY = getY();

        // Calculate the next position based on the direction
        switch (direction) {
            case UP:
                nextY--;
                break;
            case DOWN:
                nextY++;
                break;
            case LEFT:
                nextX--;
                break;
            case RIGHT:
                nextX++;
                break;
        }
    }
}
/*
        // Check if the next position is valid
        //(e.g., within the level boundaries and not blocked by other actors)
 */
        //if (isValidMove(nextX, nextY, level)) {
            // Update the position
            //setX(nextX);
           // setY(nextY);
        //}
    //}
    /*
    private boolean isValidMove(int x, int y, Level level) {
        // Check if the position is within the level boundaries
        if (x < 0 || x >= level.getWidth() || y < 0 || y >= level.getHeight()) {
            return false;
        }

        // Check if the next position is not blocked by other actors
        Actor actorAtNextPosition = level.getActorAt(x, y);
        if (actorAtNextPosition == null || !actorAtNextPosition.isBlocking()) {
            //the "isBlocking" method should be added to actor class

            /*public boolean isBlocking() {
        return true; // By default, actors are blocking
    }
            // If the next position is water, sink the
            block and replace it with a path
     */
            //if (actorAtNextPosition instanceof Water) {
                // Replace the block with a path at the current position
               // level.setActorAt(getX(), getY(), new Path());
                // Update the position to the next position
                //setX(x);
               // setY(y);

              //  return true; // Move is valid
           // }
          //  return true; // Move is valid
     //   }
        //return false; // Move is not valid
    //}

   //  */
//}
