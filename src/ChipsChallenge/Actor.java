package ChipsChallenge;

import javafx.scene.image.Image;

public class Actor {
    private final ActorType type;
    private final Image image;

    private int x;

    private int y;

    public Actor(ActorType type, Image image) {
        this.type = type;
        this.image = image;
    }

    public ActorType getType() {
        return type;
    }

    public Image getImage() {
        return image;
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

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}