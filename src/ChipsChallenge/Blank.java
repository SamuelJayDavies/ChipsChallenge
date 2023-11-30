package ChipsChallenge;

import javafx.scene.image.Image;

/**
 * Should change this whole class to that if the item is null just don't get an image, would save on processing time
 */
public class Blank extends Item {

    public Blank() {
        super(ItemType.NOTHING, null);
    }


}
