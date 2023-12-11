package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a trap tile that can limit actor movement if steeped on while active. Each trap
 * tile will have a linked button that causes it to become active.
 */
public class Trap extends Tile {

    /**
     * If the trap is active.
     */
    private boolean active;

    /**
     * The button that causes the trap to become active.
     */
    private Button linkedButton;

    /**
     * The x coordinate of the trap.
     */
    private int x;

    /**
     * The y coordinate of the trap.
     */
    private int y;

    /**
     * Creates a new trap with a default image and default setting of false for active.
     */
    public Trap() {
        super(TileType.TRAP, new Image("images/stuff/trap v2.png"));
        active = false;
    }

    /**
     * Returns if the trap is active.
     *
     * @return If the trap is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the trap being active to true or false.
     *
     * @param active {@code true} for active, {@code false} otherwise.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Set linked button to the trap.
     *
     * @param linkedButton The button that is linked to this trap.
     */
    public void setLinkedButton(Button linkedButton) {
        this.linkedButton = linkedButton;
    }

    /**
     * Returns the x coordinate of the trap.
     *
     * @return The x coordinate of the trap.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the trap.
     *
     * @param x The x-coordinate of the trap.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of the trap.
     *
     * @return The y coordinate of the trap.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the trap.
     *
     * @param y The y-coordinate of the trap.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns different images depending on if the trap is currently active.
     *
     * @return Image corresponding to current state of trap.
     */
    @Override
    public Image getImage() {
        return active ? new Image("images/stuff/trap v2.png") : new Image("images/stuff/path.png");
    }
}
