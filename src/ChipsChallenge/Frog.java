package ChipsChallenge;

import javafx.scene.image.Image;

import java.util.Random;

public class Frog extends Actor {

    public Frog() {
        super(ActorType.FROG, new Image("null"));
    }

    /**
     * Moves the Frog in the game world based on its AI behavior.
     * Updates the Frog's coordinates.
     *
     * @param actorLayer The layer containing all actors in the game.
     */
    public void move(ActorLayer actorLayer) {
        int currentX = getX();
        int currentY = getY();

        // Get all possible adjacent positions
        int[][] possibleMoves = {
                {currentX - 1, currentY},
                {currentX + 1, currentY},
                {currentX, currentY - 1},
                {currentX, currentY + 1}
        };

        // Shuffle the array to randomize the movement
        shuffleArray(possibleMoves);

        for (int[] move : possibleMoves) {
            int nextX = move[0];
            int nextY = move[1];

            if (canMove(nextX, nextY, actorLayer)) {
                actorLayer.updateActor(this, nextX, nextY);
                break;  // Move only once in this AI example
            }
        }
    }

    /**
     * Checks whether the Frog can move to the specified coordinates.
     * The Frog cannot move to positions occupied by blocking tiles, other Frogs, or out-of-bounds positions.
     *
     * @param newX       The x-coordinate of the target position.
     * @param newY       The y-coordinate of the target position.
     * @param actorLayer The layer containing all actors in the game.
     * @return True if the Frog can move to the target position, false otherwise.
     */
    private boolean canMove(int newX, int newY, ActorLayer actorLayer) {
        if (!actorLayer.validPosition(newX, newY)) {
            return false;
        }

        Actor newActor = actorLayer.getActor(newX, newY);
        if (newActor != null && newActor.getType() == ActorType.BLOCK) {
            return false;
        }

        if (newActor != null && newActor.getType() == ActorType.FROG) {
            return false;
        }
        return true;
    }

    /**
     * Shuffle a 2D array.
     *
     * @param array The array to shuffle.
     */
    private void shuffleArray(int[][] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
