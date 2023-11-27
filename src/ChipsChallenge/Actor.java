package ChipsChallenge;

import javafx.scene.image.Image;

public class Actor {
    private final ActorType type;

    private final Image image;

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
}