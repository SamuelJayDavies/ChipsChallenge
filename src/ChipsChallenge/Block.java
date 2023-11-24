package ChipsChallenge;

public class Block extends Actor {
    private boolean moveTheBlock;
    private int x, y;

    public Block(int x, int y) {
        super(x, y);
        this.moveTheBlock = true;
    }

    public boolean getMoveTheBlock() {
        return moveTheBlock;
    }

    public void setMoveTheBlock(boolean moveTheBlock) {
        this.moveTheBlock = moveTheBlock;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void moveActor() {

    }
}
