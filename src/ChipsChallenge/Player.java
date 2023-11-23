package ChipsChallenge;

import com.sun.javafx.scene.traversal.Direction;

import java.util.Scanner;

public class Player extends Actor{

    public Player(int x, int y, Direction direction) {
        super(x, y);
        this.direction = Direction.RIGHT;
    }

    public void movePlayer() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter direction: ");
        String input = scan.next();
        switch (input.toUpperCase()) {
            case "UP":
                this.direction = Direction.UP;
                this.y--;
                break;
            case "DOWN":
                this.direction = Direction.DOWN;
                this.y++;
                break;
            case "LEFT":
                this.direction = Direction.LEFT;
                this.x--;
                break;
            case "RIGHT":
                this.direction = Direction.RIGHT;
                this.x++;
                break;
            default:
                System.out.println("Invalid direction.");
        }
    }
}
