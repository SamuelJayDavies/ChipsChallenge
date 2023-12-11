package ChipsChallenge;

/**
 * Represents a position in the actor matrix where no actor exists.
 *
 * @author Samuel Davies
 */
public class NoActor extends Actor {

    /**
     * Creates a new noActor object with a null image, as the actor doesn't exist.
     */
    public NoActor() {
        super(ActorType.NOACTOR, null);
    }

}
