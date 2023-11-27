package ChipsChallenge;

import java.util.Scanner;

public class ActorLayer {

    private Actor[][] actors;

    public ActorLayer(int noOfRows, int noOfColumns) {
        actors = new Actor[noOfRows][noOfColumns];

    }

    public Actor getActor(int x, int y) {
        if(validPosition(x,y)) {
            return actors[x][y];
        }
        return null;
    }

    private void initializeActors(Scanner scan) {
        int j = 0;
        while (scan.hasNextLine()) {
            String currentRow = scan.nextLine();
            String[] currentActors = currentRow.split(",");
            for(int i = 0; i < actors[i].length; i++) {
                actors[j][i] = identifyActor(currentActors[i]);
            }
        }
        j++;
    }

    private Actor identifyActor(String type) {
        switch (type) {
            case "p":
                return new Player();
            case "bl":
                return new Block();
            case "b":
                return new Bug();
            case "f":
                return new Frog();
            case "pb":
                return new PinkBall();
        }
        return null;
    }

    public Actor[][] getActors() {
        return actors;
    }

    private boolean validPosition(int x, int y) {
        return x >= 0 && x < actors.length && y < actors[0].length;
    }
}
