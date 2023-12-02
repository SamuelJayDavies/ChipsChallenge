package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The Bug class represents a type of monster in the game Chips Challenge.
 * Bugs can only travel over specific tiles and follow the left or right edge of the path.
 * They are prevented from moving onto blocking tiles, other monsters, or out-of-bounds positions.
 */

public class Bug extends Actor {

    private boolean followEdge;

    /**
     * Constructs a new Bug instance.
     * Initializes the Bug with the BUG type and a default image.
     * Sets the initial direction to follow the left edge.
     */

    public Bug() {
        super(ActorType.BUG, new Image("null"));
        this.followEdge = true;
    }

    /**
     * Moves the Bug in the game world based on its current direction.
     * Updates the Bug's coordinates and may change its direction.
     *
     * @param actorLayer The layer containing all actors in the game.
     */
    public void move(ActorLayer actorLayer) {
        int currentX = getX();
        int currentY = getY();

        int nextX = currentX;
        int nextY = currentY;

        if (followEdge) {
            if (canMove(currentX, currentY - 1, actorLayer)) {
                nextY = currentY - 1;
            } else if (canMove(currentX + 1, currentY, actorLayer)) {
                nextX = currentX + 1;
                followEdge = false;
            } else if (canMove(currentX, currentY + 1, actorLayer)) {
                nextY = currentY + 1;
                followEdge = false;
            } else {
                followEdge = false;
            }
        } else {
            if (canMove(currentX, currentY + 1, actorLayer)) {
                nextY = currentY + 1;
            } else if (canMove(currentX - 1, currentY, actorLayer)) {
                nextX = currentX - 1;
                followEdge = true;
            } else if (canMove(currentX, currentY - 1, actorLayer)) {
                nextY = currentY - 1;
                followEdge = true;
            } else {
                followEdge = true;
            }
        }
        actorLayer.updateActor(this, nextX, nextY);
    }

    /**
     * Checks whether the Bug can move to the specified coordinates.
     * The Bug cannot move to positions occupied by blocking tiles, other Bugs, or out-of-bounds positions.
     *
     * @param newX        The x-coordinate of the target position.
     * @param newY        The y-coordinate of the target position.
     * @param actorLayer  The layer containing all actors in the game.
     * @return True if the Bug can move to the target position, false otherwise.
     */

    private boolean canMove(int newX, int newY, ActorLayer actorLayer) {
        if (!actorLayer.validPosition(newX, newY)) {
            return false;
        }

        Actor newActor = actorLayer.getActor(newX, newY);
        if (newActor != null && newActor.getType() == ActorType.BLOCK) {
            return false;
        }

        if (newActor != null && newActor.getType() == ActorType.BUG) {
            return false;
        }
        return true;
    }

    /**
     * Updates the Bug's direction based on specific rules or conditions.
     * This method may be called during the game to change the Bug's movement behavior.
     */
    public void updateDirection() {

    }
}