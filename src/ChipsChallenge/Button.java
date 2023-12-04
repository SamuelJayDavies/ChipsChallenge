package ChipsChallenge;

import javafx.scene.image.Image;

public class Button extends Tile {

    private Trap linkedTrap;

    private boolean active;

    public Button() {
        super(TileType.BUTTON, new Image("images/stuff/button.png"));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Trap getLinkedTrap() {
        return linkedTrap;
    }

    public void setLinkedTrap(Trap linkedTrap) {
        this.linkedTrap = linkedTrap;
    }
}
