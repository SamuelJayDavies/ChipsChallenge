package ChipsChallenge;

import javafx.scene.image.Image;

public class PinkBall extends Actor {
    private Direction direction;

    public PinkBall(int x, int y, Direction direction) {
        super(ActorType.PINKBALL, new Image("null"), x, y);
        this.direction = direction;
    }

    @Override
    public void moveActor() {
        int nextX = calculateNextX();
        int nextY = calculateNextY();

        if (isValidTile(nextX, nextY)) {
            setX(nextX);
            setY(nextY);
        } else {
            reverseDirection();
        }
    }

    private int calculateNextX() {
        return getX() + (direction == Direction.RIGHT ? 1 : (direction == Direction.LEFT ? -1 : 0));
    }

    private int calculateNextY() {
        return getY() + (direction == Direction.DOWN ? 1 : (direction == Direction.UP ? -1 : 0));
    }

    private boolean isValidTile(int x, int y) {
        TileType tileType = getTileType(x, y);
        return tileType == TileType.PATH || tileType == TileType.BUTTON || tileType == TileType.TRAP;
    }

    private TileType getTileType(int x, int y) {
        
        return gameMap[y][x];  // Asuming y represents the row and x represents the column
    }

    public void reverseDirection() {
        direction = (direction == Direction.RIGHT) ? Direction.LEFT :
                    (direction == Direction.LEFT) ? Direction.RIGHT :
                    (direction == Direction.UP) ? Direction.DOWN :
                    Direction.UP;
    }
}


