package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * The Actor class is a base class for game entities that can appear on game.
 * It provides common properties and methods for different types of game actors.
 */
public class Actor {

    /**
     * The type of the actor, represented by the ActorType enum.
     */
    private final ActorType type;

    /**
     * The image associated with the actor.
     */
    private final Image image;

    /**
     * The X-coordinate of the actor on the game grid.
     */
    private int x;

    /**
     * The Y-coordinate of the actor on the game grid.
     */
    private int y;

    /**
     * Constructs a new Actor with the specified type and image.
     * @param type  The type of the actor, represented by the ActorType enum.
     * @param image The image associated with the actor.
     */

    public Actor(ActorType type, Image image) {
        this.type = type;
        this.image = image;
    }

    /**
     * Gets the type of the actor.
     * @return The type of the actor.
     */
    public ActorType getType() {
        return type;
    }

    /**
     * Gets the image associated with the actor.
     * @return The image of the actor.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the X-coordinate of the actor on the game.
     * @return The X-coordinate of the actor.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X-coordinate of the actor on the game.
     * @param x The new X-coordinate of the actor.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y-coordinate of the actor on the game.
     * @return The Y-coordinate of the actor.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y-coordinate of the actor on the game.
     * @param y The new Y-coordinate of the actor.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets both X and Y coordinates of the actor on the game grid.
     * @param x The new X-coordinate of the actor.
     * @param y The new Y-coordinate of the actor.
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
