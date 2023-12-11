package ChipsChallenge;

/**
 * The TileType enum represents the different types of tiles in the game CaveQuest.
 * Each enum constant corresponds to a specific tile.
 *
 * @author Samuel Davies
 */
public enum TileType {

    /**
     * An open path where the player and monsters can move freely.
     */
    PATH,

    /**
     * A tile that monsters cannot step on, and becomes a path after a player
     * has stepped on and off it.
     */
    DIRT,

    /**
     * An impassable wall that blocks all actor's movement.
     */
    WALL,

    /**
     * The exit tile where the player must reach to complete the level.
     */
    EXIT,

    /**
     * A button that triggers a trap when standing on it.
     */
    BUTTON,

    /**
     * A trap tile that will limit actor movement will the corresponding button is being pressed.
     */
    TRAP,

    /**
     * A water tile that will kill the player if they step on it.
     */
    WATER,

    /**
     * A tile representing a socket where a certain number of chips are required to unlock
     * it.
     */
    CHIPSOCKET,

    /**
     * A regular ice tile the player can slide on.
     */
    ICE,

    /**
     * A specific ice tile with a top-right orientation.
     */
    ICETR,

    /**
     * A specific ice tile with a top-left orientation.
     */
    ICETL,

    /**
     * A specific ice tile with a bottom-right orientation.
     */
    ICEBR,

    /**
     * A specific ice tile with a bottom-left orientation.
     */
    ICEBL,

    /**
     * Door: A door that requires an equivalent key to open.
     */
    DOOR
}

