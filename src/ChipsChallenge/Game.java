package ChipsChallenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    private Level currentLevel;
    //private User currentUser;

    public static void testFunction() {
        try{
            System.out.println(new File("").getAbsolutePath());
            File myFile = new File("src/levels/level1.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
        }catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

}
