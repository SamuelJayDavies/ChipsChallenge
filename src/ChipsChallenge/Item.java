package ChipsChallenge;

import javafx.scene.image.Image;

class Item {
    // Common properties
    private ItemType type;
    private Image image;

    // Constructor to initialize item properties
    public Item(ItemType type, Image image) {
        this.type = type;
        this.image = image;
    }

    public ItemType getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }
}
