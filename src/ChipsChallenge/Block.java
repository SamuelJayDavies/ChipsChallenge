package ChipsChallenge;

public class Block extends Actor {
    private boolean moveTheBlock;

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

    public void move() {

    }
}
