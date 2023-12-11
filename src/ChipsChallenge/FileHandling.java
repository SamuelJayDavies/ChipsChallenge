package ChipsChallenge;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Static class used for reading in and writing out data from files.
 * The file handling class reads in levels, saved levels and highScores and
 * returns them to be used by the game controller.
 *
 * @author Samuel Davies
 */
public class FileHandling {

    /**
     * Number of layers needed to fully describe level.
     */
    public static final int NUM_OF_LAYERS = 5;

    /**
     * Height of information layer that is needed to describe the saved level's updated
     * variables.
     */
    public static final int INFO_LAYER_HEIGHT = 3;

    /**
     * Position of width in layer array.
     */
    public static final int WIDTH_POSITION = 0;

    /**
     * Position of height in layer array.
     */
    public static final int HEIGHT_POSITION = 1;

    /**
     * Position of time in layer array.
     */
    public static final int LEVEL_TIME_POSITION = 2;

    /**
     * Position of level number in layer array.
     */
    public static final int LEVEL_NUM_POSITION = 3;

    /**
     * Position of level description in layer array.
     */
    private static final int LEVEL_DESC_POSITION = 4;

    /**
     * Returns a String array where every entry is a line from the passed in file.
     * The reading stops when a new line character is found.
     *
     * @param file   The file that is being read in by the scanner.
     * @param height The height of the lines being read in.
     * @return A String array representation of the lines from the file.
     */
    public static String[] splitFile(Scanner file, int height) {
        String[] tileArr = new String[height];
        boolean layerFinished = false;
        int i = 0;
        while (!layerFinished) {
            String nextLine = file.nextLine();
            // If a new line has been found
            if (!(nextLine.equals(""))) {
                tileArr[i] = nextLine;
                i++;
            } else {
                layerFinished = true;
            }
        }
        return tileArr;
    }

    /**
     * Reads in all the saved files on record and returns them in a list.
     *
     * @return A list of all saved files on record.
     */
    public static ArrayList<SavedLevel> readInSavedLevels() {
        String fileName = "SavedLevels.txt";
        File savedLevelsFile = new File(fileName);
        boolean isFinished = false;
        try {
            // In case no levels exist
            savedLevelsFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<SavedLevel> savedLevels = new ArrayList<>();
        Scanner in;
        try {
            in = new Scanner(savedLevelsFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        do {
            // For when there are no saved levels
            if (in.hasNextLine()) {
                String firstLine = in.nextLine();
                String[] infoArr;
                // For first record where next will be first line
                if (!firstLine.equals("next")) {
                    infoArr = new String[INFO_LAYER_HEIGHT];
                    infoArr[0] = firstLine;
                    String[] afterArr = FileHandling.splitFile(in, 2);
                    infoArr[1] = afterArr[0];
                    infoArr[2] = afterArr[1];
                } else {
                    infoArr = FileHandling.splitFile(in, INFO_LAYER_HEIGHT);
                }
                String[] originalLevelArr = infoArr[0].split(",");
                String[] newLevelArr = infoArr[1].split(",");
                ArrayList<Key> currentInventory = new ArrayList<>();
                // If the player didn't pick up any keys
                if (!(infoArr[2] == null)) {
                    String[] inventoryArr = infoArr[2].split(",");
                    currentInventory = new ArrayList<>();
                    for (String key : inventoryArr) {
                        currentInventory.add(new Key(key.charAt(1)));
                    }
                }

                String[][] layers = new String[NUM_OF_LAYERS][1];
                for (int i = 0; i < NUM_OF_LAYERS; i++) {
                    layers[i] = FileHandling.splitFile(in, Integer.parseInt(originalLevelArr[1]));
                }

                Level originalLevel = new Level(Integer.parseInt(originalLevelArr[WIDTH_POSITION]),
                        Integer.parseInt(originalLevelArr[HEIGHT_POSITION]),
                        Integer.parseInt(originalLevelArr[LEVEL_TIME_POSITION]),
                        Integer.parseInt(originalLevelArr[LEVEL_NUM_POSITION]),
                        originalLevelArr[LEVEL_DESC_POSITION], layers);

                SavedLevel savedLevel = new SavedLevel(newLevelArr[0],
                        Integer.parseInt(newLevelArr[1]),
                        Integer.parseInt(newLevelArr[2]), currentInventory, originalLevel);

                savedLevels.add(savedLevel);
            } else {
                in.close();
                return savedLevels;
            }
            String finishResult = in.nextLine();
            // If we have reached the end of the file
            if (finishResult.equals("end")) {
                isFinished = true;
            }
        } while (!isFinished);
        in.close();
        return savedLevels;
    }

    /**
     * Saves the current level to file. If the user already has a record in the file, that record will be
     * overwritten by the newestSave.
     *
     * @param savedLevels   All the current saved levels on file.
     * @param newestSave    The newest save that is currently being written to file.
     * @param levelFinished If the level was finished or not. If it was finished it will not be saved.
     */
    public static void saveCurrentLevel(ArrayList<SavedLevel> savedLevels,
                                        SavedLevel newestSave, boolean levelFinished) {

        String tempCurrentLevels = "tempCurrentLevels.txt";
        File oldFile = new File("SavedLevels.txt");
        File newFile = new File(tempCurrentLevels);
        try {
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            boolean isNewSave = true;

            for (SavedLevel savedLevel : savedLevels) {
                if (savedLevel.getUsername().equals(newestSave.getUsername())) {
                    writeToFile(printWriter, newestSave);
                    isNewSave = false;
                } else {
                    writeToFile(printWriter, savedLevel);
                }
            }

            // For first time saves and new saves, if the level is finished, the level won't be saved
            if (isNewSave && !levelFinished) {
                writeToFile(printWriter, newestSave);
            }

            printWriter.write("end");

            printWriter.flush();
            printWriter.close();
            // Replace the original saved levels file with the new file and rename it
            if (oldFile.delete()) {
                File dump = new File("SavedLevels.txt");
                if (!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Writes all the saved levels to file. Writes the new level structure and updated level variables to file.
     *
     * @param printWriter The writer that is connected to the file.
     * @param savedLevel  The saved level to be written to the file
     */
    private static void writeToFile(PrintWriter printWriter, SavedLevel savedLevel) {
        Level currentLevel = savedLevel.getLevel();
        TileLayer tileLayer = currentLevel.getTileLayer();

        printWriter.write("next\n");
        // Information about the original level
        printWriter.write(tileLayer.getTiles()[0].length + "," + tileLayer.getTiles().length + ","
                + currentLevel.getLevelTime() + "," + currentLevel.getLevelNum() + ","
                + currentLevel.getLevelDesc() + "\n");

        // Information about the new level (current version of original level)
        printWriter.write(savedLevel.getUsername() + "," + savedLevel.getCurrentTime() + ","
                + savedLevel.getChipCount() + "\n");

        for (Key key : savedLevel.getInventory()) {
            printWriter.write(ItemLayer.convertItemToString(key) + ",");
        }

        if (savedLevel.getInventory().isEmpty()) {
            printWriter.write("\n");
        } else {
            printWriter.write("\n\n");
        }

        // Information of tiles on the new level
        for (int i = 0; i < tileLayer.getTiles().length; i++) {
            for (int j = 0; j < tileLayer.getTiles()[0].length; j++) {
                String tileStr = TileLayer.convertTileToString(tileLayer.getTileAt(j, i));
                printWriter.write(tileStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        // Information of items on the new level
        ItemLayer itemLayer = currentLevel.getItemLayer();
        for (int i = 0; i < itemLayer.getItems().length; i++) {
            for (int j = 0; j < itemLayer.getItems()[0].length; j++) {
                String itemStr = ItemLayer.convertItemToString(itemLayer.getItemAt(j, i));
                printWriter.write(itemStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        // Information of actors on the new level
        ActorLayer actorLayer = currentLevel.getActorLayer();
        for (int i = 0; i < actorLayer.getActors().length; i++) {
            for (int j = 0; j < actorLayer.getActors()[0].length; j++) {
                String actorStr = ActorLayer.convertActorToString(actorLayer.getActor(j, i));
                printWriter.write(actorStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        // Information of tile interactions on the original level
        for (int i = 0; i < tileLayer.getTiles().length; i++) {
            for (int j = 0; j < tileLayer.getTiles()[0].length; j++) {
                if (tileLayer.getTileAt(j, i).getType() == TileType.BUTTON) {
                    ChipsChallenge.Button button = (ChipsChallenge.Button) tileLayer.getTileAt(j, i);
                    printWriter.write(button.getLinkedTrap().getX() + ":" + button.getLinkedTrap().getY());
                } else {
                    printWriter.write("n");
                }
                printWriter.write(",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        String[][] actorDetails = new String[actorLayer.getActors().length][actorLayer.getActors()[0].length];
        for (int i = 0; i < actorDetails.length; i++) {
            for (int j = 0; j < actorDetails[0].length; j++) {
                actorDetails[i][j] = "n";
            }
        }

        // If monster is found, change actor details coordinate to have their directions
        for (Actor monster : actorLayer.getMonsters()) {
            if (monster.getType() == ActorType.PINKBALL) {
                PinkBall ball = (PinkBall) monster;
                actorDetails[ball.getY()][ball.getX()] = Level.convertDirectionToString(ball.getDirection());
            } else if (monster.getType() == ActorType.BUG) {
                Bug bug = (Bug) monster;
                actorDetails[bug.getY()][bug.getX()] = Level.convertDirectionToString(bug.getFollowDirection()) + ":"
                        + Level.convertDirectionToString(bug.getDirection());
            }
        }

        // Information of monsters on new level
        for (int i = 0; i < actorDetails.length; i++) {
            for (int j = 0; j < actorDetails[0].length; j++) {
                printWriter.write(actorDetails[i][j] + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");
    }
}
