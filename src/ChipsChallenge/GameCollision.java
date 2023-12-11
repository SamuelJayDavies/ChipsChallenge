package ChipsChallenge;

/**
 * Class that checks for collisions between actors and tiles.
 *
 * @author Samuel Davies
 */
public final class GameCollision {

    /**
     * The current level that the game has loaded in.
     */
    private final Level currentLevel;

    /**
     * Creates a new game collision object with a specified currentLevel to check for collisions.
     *
     * @param currentLevel The current level loaded in by the main Game Controller.
     */
    public GameCollision(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Takes in a position and checks if it is within the current levels boundaries in terms of
     * height and width.
     *
     * @param position The position to be verified.
     * @return {@code true} if the position is valid, {@code false} otherwise.
     */
    public boolean isValidMove(int[] position) {
        return (position[0] < currentLevel.getWidth() && position[0] >= 0)
                && (position[1] < currentLevel.getHeight() && position[1] >= 0);
    }

    /**
     * Takes in a position belonging to an actor and checks the tile they are currently standing
     * on and returns it.
     *
     * @param position The position of the actor.
     * @return The tile type of the tile the actor is currently standing on.
     */
    public TileType checkForTileCollision(int[] position) {
        Tile currentTile = currentLevel.getTileLayer().getTileAt(position[0], position[1]);
        return currentTile.getType();
    }

    /**
     * Takes in a position belonging to an actor and checks if they are on the same tile as an actor
     * and returns it.
     *
     * @param position The position of the actor.
     * @return The actor type of the actor, the actor is currently standing on.
     */
    public ActorType checkForActorCollision(int[] position) {
        Actor currentActor = currentLevel.getActorLayer().getActor(position[0], position[1]);
        return currentActor.getType();
    }

    /**
     * Returns if the actor is currently standing on an active trap.
     *
     * @param actor The actor being verified.
     * @return {@code true} if they are on a trap, and it is active, {@code false} otherwise.
     */
    public boolean onActiveTrap(Actor actor) {
        Tile possibleTrap = currentLevel.getTileLayer().getTileAt(actor.getX(), actor.getY());
        if (possibleTrap.getType() == TileType.TRAP) {
            Trap foundTrap = (Trap) possibleTrap;
            return foundTrap.isActive();
        }
        return false;
    }
}
