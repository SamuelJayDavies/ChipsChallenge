package ChipsChallenge;

import java.util.Scanner;

public class ActorLayer {

    private Actor[][] actors;

    private Player mainPlayer;

    public ActorLayer(int width, int height, String[] actorArr) {
        actors = new Actor[height][width];
        initialiseItems(actorArr);
    }

    private void initialiseItems(String[] actorArr) {
        int j=0;
        for (String actorRow: actorArr) {
            String[] currentActors = actorRow.split(","); // Should probably have this in game controller or a
            for (int i=0; i<actors[j].length; i++) {              // specialised file
                actors[j][i] = identifyActor(currentActors[i]);
                actors[j][i].setCoordinates(i,j);
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
                return new Bug();
            case "f":
                return new Frog();
            case "pb":
                return new PinkBall();
            default:
                return null;
        }
    }

    public Actor getActor(int x, int y) {
        if(validPosition(x,y)) {
            return actors[x][y];
        }
        return null;
    }

    public Actor[][] getActors() {
        return actors;
    }

    private boolean validPosition(int x, int y) {
        return x >= 0 && x < actors.length && y < actors[0].length;
    }

    public Player getPlayer() {
        return this.mainPlayer;
    }

    public void updateActor(Actor actor, int x, int y) {
        actors[actor.getY()][actor.getX()] = new NoActor();
        actor.setX(x);
        actor.setY(y);
        actors[actor.getY()][actor.getX()] = actor;
    }
}
