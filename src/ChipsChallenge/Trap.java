package ChipsChallenge;

import javafx.scene.image.Image;

public class Trap extends Tile {

    private boolean active;

    private Button linkedButton;

    private int x;

    private int y;

    public Trap() {
        super(TileType.TRAP, new Image("images/stuff/trap v2.png"));
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Button getLinkedButton() {
        return linkedButton;
    }

    public void setLinkedButton(Button linkedButton) {
        this.linkedButton = linkedButton;
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

    @Override
    public Image getImage() {
        // Fix this later, should just reference own image
        return active ? new Image("images/stuff/trap v2.png") : new Image("images/stuff/path.png");
    }
}
