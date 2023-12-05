package ChipsChallenge;

import java.util.ArrayList;
import java.util.Scanner;

public class ActorLayer {

    private Actor[][] actors;

    private Player mainPlayer;

    private ArrayList<Actor> monsters;

    public ActorLayer(int width, int height, String[] actorArr) {
        actors = new Actor[height][width];
        monsters = new ArrayList<>();
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

    public Actor getActor(int x, int y) {
        if(validPosition(x,y)) {
            return actors[y][x];
        }
        return null;
    }

    public Actor[][] getActors() {
        return actors;
    }

    public void setActor(int x, int y, Actor newActor) {
        actors[y][x] = newActor;
    }

    boolean validPosition(int x, int y) {
        return x >= 0 && x < actors[0].length && y < actors.length;
    }

    public Player getPlayer() {
        return this.mainPlayer;
    }

    public ArrayList<Actor> getMonsters() {
        return monsters;
    }

    public void updateActor(Actor actor, int x, int y) {
        actors[actor.getY()][actor.getX()] = new NoActor();
        actor.setX(x);
        actor.setY(y);
        actors[actor.getY()][actor.getX()] = actor;
    }
}
