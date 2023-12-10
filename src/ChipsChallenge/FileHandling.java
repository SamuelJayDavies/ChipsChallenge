package ChipsChallenge;

import java.util.Scanner;

public class FileHandling {

    public static String[] splitFile(Scanner levelFile, int height) {
        String[] tileArr = new String[height];
        boolean layerFinished = false;
        int i = 0;
        while(!layerFinished) {
            String nextLine = levelFile.nextLine();
            if(!(nextLine.equals(""))) {
                tileArr[i] = nextLine;
                i++;
            } else {
                layerFinished = true;
            }
        }
        return tileArr;
    }
}
