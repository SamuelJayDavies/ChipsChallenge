package ChipsChallenge;

import com.sun.javafx.scene.traversal.Direction;

import java.util.Scanner;

public class Player extends Actor{

    public Player(int x, int y, Direction direction) {
        super(x, y, direction);
        this.direction = Direction.RIGHT;
    }

    public void movePlayer() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter direction: ");
        String input = scan.next();
    }
}
