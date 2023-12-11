package ChipsChallenge;

public class GameCollision {

    private final Level currentLevel;

    public GameCollision(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isValidMove(int[] position) {
        return (position[0] < currentLevel.getWidth() && position[0] >= 0)
                && (position[1] < currentLevel.getHeight() && position[1] >= 0);
    }

    public TileType checkForTileCollision(int[] position) {
        Tile currentTile = currentLevel.getTileLayer().getTileAt(position[0], position[1]);
        return currentTile.getType();
    }

    public ActorType checkForActorCollision(int[] position) {
        Actor currentActor = currentLevel.getActorLayer().getActor(position[0], position[1]);
        return currentActor.getType();
    }

    public boolean onActiveTrap(Actor actor) {
        Tile possibleTrap = currentLevel.getTileLayer().getTileAt(actor.getX(), actor.getY());
        if(possibleTrap.getType() == TileType.TRAP) {
            Trap foundTrap = (Trap) possibleTrap;
            return foundTrap.isActive();
        }
        return false;
    }
}
