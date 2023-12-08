package ChipsChallenge;

import java.util.ArrayList;

/**
 * The ActorLayer class represents a layer of actors
 * in the game Chips Challenge.
 * It manages the arrangement of actors on a grid,
 * including the player and various monsters.
 */
public class ActorLayer {

    /**
     * A 2D array representing the grid of actors in the layer.
     */
    private Actor[][] actors;

    /**
     * The main player in the actor layer.
     */
    private Player mainPlayer;

    /**
     * A list containing all the monsters in the actor layer.
     */
    private ArrayList<Actor> monsters;

    /**
     * Constructs an ActorLayer with the specified width,
     * height, and initial arrangement of actors.
     * @param width      The width of the actor layer grid.
     * @param height     The height of the actor layer grid.
     * @param actorArr   An array representing the initial
     *                   arrangement of actors in the grid.
     */
    public ActorLayer(int width, int height, String[] actorArr) {
        actors = new Actor[height][width];
        monsters = new ArrayList<>();
        initialiseItems(actorArr);
    }

    private void initialiseItems(String[] actorArr) {
        int j = 0;
        for (String actorRow: actorArr) {
            // Should probably have this in game controller or a
            //specialised file
            String[] currentActors = actorRow.split(",");
            for (int i = 0; i < actors[j].length; i++) {
                actors[j][i] = identifyActor(currentActors[i]);
                actors[j][i].setCoordinates(i, j);
            }
            j++;
        }
    }

    private Actor identifyActor(String type) {
        switch (type) {
            case "n":
                return new NoActor();
            case "p":
                Player player = new Player();
                mainPlayer = player;
                return player;
            case "bl":
                return new Block();
            case "b":
                Bug bug = new Bug();
                monsters.add(bug);
                return bug;
            case "f":
                Frog frog = new Frog();
                monsters.add(frog);
                return frog;
            case "pb":
                PinkBall ball = new PinkBall();
                monsters.add(ball);
                return ball;
            default:
                return null;
        }
    }

    /**
     * Gets the actor at the specified coordinates.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return The actor at the specified coordinates, or null if out of bounds.
     */
    public Actor getActor(int x, int y) {
        if (validPosition(x, y)) {
            return actors[y][x];
        }
        return null;
    }

    /**
     * Gets the 2D array of actors representing the grid.
     * @return The 2D array of actors.
     */
    public Actor[][] getActors() {
        return actors;
    }

    /**
     * Sets the actor at the specified coordinates in the grid.
     * @param x        The X-coordinate.
     * @param y        The Y-coordinate.
     * @param newActor The new actor to set.
     */
    public void setActor(int x, int y, Actor newActor) {
        actors[y][x] = newActor;
    }

    /**
     * Checks if the specified coordinates are within
     * the valid bounds of the actor layer grid.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return True if the position is valid, false otherwise.
     */
    boolean validPosition(int x, int y) {
        return x >= 0 && x < actors[0].length && y < actors.length;
    }

    /**
     * Gets the main player in the actor layer.
     * @return The main player.
     */
    public Player getPlayer() {
        return this.mainPlayer;
    }

    /**
     * Gets the list of monsters in the actor layer.
     * @return The list of monsters.
     */
    public ArrayList<Actor> getMonsters() {
        return monsters;
    }

    /**
     * Updates the position of the specified actor in the grid.
     * @param actor The actor to update.
     * @param x     The new X-coordinate.
     * @param y     The new Y-coordinate.
     */
    public void updateActor(Actor actor, int x, int y) {
        actors[actor.getY()][actor.getX()] = new NoActor();
        actor.setX(x);
        actor.setY(y);
        actors[actor.getY()][actor.getX()] = actor;
    }
}
