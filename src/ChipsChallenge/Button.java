package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents a button that can be stepped on to set of a linked trap. The trap will continue to function
 * until the actor steps of this button.
 *
 * @author Samuel Davies
 */
public class Button extends Tile {

    /**
     * The linked trap that will activate upon stepping on this button.
     */
    private Trap linkedTrap;

    /**
     * If the button is currently active.
     */
    private boolean active;

    /**
     * Creates a new button with a default image.
     */
    public Button() {
        super(TileType.BUTTON, new Image("images/stuff/button.png"));
    }

    /**
     * Returns if the button is currently active.
     *
     * @return If the button is currently active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the button to either be active or non-active.
     *
     * @param active {@code true} for active, {@code false} otherwise.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the linked trap with the button.
     *
     * @return The linked trap.
     */
    public Trap getLinkedTrap() {
        return linkedTrap;
    }

    /**
     * Sets the trap the button is linked with.
     *
     * @param linkedTrap The trap the button is linked with.
     */
    public void setLinkedTrap(final Trap linkedTrap) {
        this.linkedTrap = linkedTrap;
    }
}
