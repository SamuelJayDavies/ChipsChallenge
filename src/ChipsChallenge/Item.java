package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Represents an item that can be picked up in game. An item can either be a computer chip,
 * a key or nothing.
 *
 * @author Samuel Davies
 */
class Item {

    /**
     * The type of the item, represented by the item enum.
     */
    private ItemType type;

    /**
     * The image associated with the item.
     */
    private Image image;

    /**
     * Creates a new item of type ItemType and assigning a default image.
     *
     * @param type  The itemType of the item being created.
     * @param image The image associated with the item.
     */
    public Item(ItemType type, Image image) {
        this.type = type;
        this.image = image;
    }

    /**
     * Returns the item's type.
     *
     * @return The item's type.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Returns the image associated with the item.
     *
     * @return The item's image.
     */
    public Image getImage() {
        return image;
    }
}
