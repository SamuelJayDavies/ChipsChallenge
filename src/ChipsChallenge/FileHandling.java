package ChipsChallenge;

import java.io.*;
import java.util.ArrayList;
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

    public static ArrayList<SavedLevel> readInSavedLevels() {
        String fileName = "SavedLevels.txt";
        File savedLevelsFile = new File(fileName);
        boolean isFinished = false;
        try{
            savedLevelsFile.createNewFile();
        } catch(IOException e) {
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
            if(in.hasNextLine()) {
                String firstLine = in.nextLine();
                String[] infoArr;
                if(!firstLine.equals("next")) {
                    infoArr = new String[3];
                    infoArr[0] = firstLine;
                    String[] afterArr = FileHandling.splitFile(in, 2);
                    infoArr[1] = afterArr[0];
                    infoArr[2] = afterArr[1];
                } else {
                    infoArr = FileHandling.splitFile(in, 3);
                }
                String[] originalLevelArr = infoArr[0].split(",");
                String[] newLevelArr = infoArr[1].split(",");
                ArrayList<Key> currentInventory = new ArrayList<>();
                if(!(infoArr[2] == null)) {
                    String[] inventoryArr = infoArr[2].split(",");
                    currentInventory = new ArrayList<>();
                    for(String key: inventoryArr) {
                        currentInventory.add(new Key(key.charAt(1)));
                    }
                }

                String[][] layers = new String[5][1];
                for(int i=0; i<5; i++) {
                    layers[i] = FileHandling.splitFile(in, Integer.parseInt(originalLevelArr[1]));
                }
                Level originalLevel = new Level(Integer.parseInt(originalLevelArr[0]), Integer.parseInt(originalLevelArr[1]),
                        Integer.parseInt(originalLevelArr[2]), Integer.parseInt(originalLevelArr[3]), originalLevelArr[4], layers);
                SavedLevel savedLevel = new SavedLevel(newLevelArr[0], Integer.parseInt(newLevelArr[1]),
                        Integer.parseInt(newLevelArr[2]), currentInventory, originalLevel);
                savedLevels.add(savedLevel);
            } else {
                in.close();
                return savedLevels;
            }
            String finishResult = in.nextLine();
            if(finishResult.equals("end")) {
                isFinished = true;
            }
        } while(!isFinished);
        in.close();
        return savedLevels;
    }

    public static void saveCurrentLevel(ArrayList<SavedLevel> savedLevels, SavedLevel newestSave, boolean levelFinished) {
        String tempCurrentLevels = "tempCurrentLevels.txt";
        File oldFile = new File("SavedLevels.txt");
        File newFile = new File(tempCurrentLevels);
        try{
            FileWriter fileWriter = new FileWriter(newFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            boolean isNewSave = true;

            for(SavedLevel savedLevel: savedLevels) {
                if(savedLevel.getUsername().equals(newestSave.getUsername())) {
                    writeToFile(printWriter, newestSave);
                    isNewSave = false;
                } else {
                    writeToFile(printWriter, savedLevel);
                }
            }

            // For first time saves and new saves
            if(isNewSave && !levelFinished) {
                writeToFile(printWriter, newestSave);
            }

            printWriter.write("end");

            printWriter.flush();
            printWriter.close();
            if(oldFile.delete()) {
                File dump = new File("SavedLevels.txt");
                if(!(newFile.renameTo(dump))) {
                    System.out.println("New file couldn't be renamed");
                }
            } else {
                System.out.println("Old file couldn't be deleted");
            }
        } catch(IOException e) {
            System.out.println("Problem");
        }
    }

    private static void writeToFile(PrintWriter printWriter, SavedLevel savedLevel) {
        Level currentLevel = savedLevel.getLevel();
        TileLayer tileLayer = currentLevel.getTileLayer();
        printWriter.write("next\n");
        printWriter.write(tileLayer.getTiles()[0].length + "," + tileLayer.getTiles().length + "," +
                currentLevel.getLevelTime() + "," + currentLevel.getLevelNum() + "," + currentLevel.getLevelDesc() + "\n");
        printWriter.write(savedLevel.getUsername() + "," + savedLevel.getCurrentTime() + "," + savedLevel.getChipCount() + "\n");
        for(Key key: savedLevel.getInventory()) {
            printWriter.write(ItemLayer.convertItemToString(key) + ",");
        }
        if(savedLevel.getInventory().isEmpty()) {
            printWriter.write("\n");
        } else {
            printWriter.write("\n\n");
        }

        for(int i=0; i<tileLayer.getTiles().length; i++) {
            for(int j=0; j<tileLayer.getTiles()[0].length; j++) {
                String tileStr = TileLayer.convertTileToString(tileLayer.getTileAt(j,i));
                printWriter.write(tileStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        ItemLayer itemLayer = currentLevel.getItemLayer();
        for(int i=0; i<itemLayer.getItems().length; i++) {
            for(int j=0; j<itemLayer.getItems()[0].length; j++) {
                String itemStr = ItemLayer.convertItemToString(itemLayer.getItemAt(j,i));
                printWriter.write(itemStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        ActorLayer actorLayer = currentLevel.getActorLayer();
        for(int i=0; i<actorLayer.getActors().length; i++) {
            for(int j=0; j<actorLayer.getActors()[0].length; j++) {
                String actorStr = ActorLayer.convertActorToString(actorLayer.getActor(j,i));
                printWriter.write(actorStr + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");

        for(int i=0; i<tileLayer.getTiles().length; i++) {
            for(int j=0; j<tileLayer.getTiles()[0].length; j++) {
                if(tileLayer.getTileAt(j,i).getType() == TileType.BUTTON) {
                    ChipsChallenge.Button button = (ChipsChallenge.Button) tileLayer.getTileAt(j,i);
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
        for(int i=0; i<actorDetails.length; i++) {
            for(int j=0; j<actorDetails[0].length; j++) {
                actorDetails[i][j] = "n";
            }
        }
        for(Actor monster: actorLayer.getMonsters()) {
            if(monster.getType() == ActorType.PINKBALL) {
                PinkBall ball = (PinkBall) monster;
                actorDetails[ball.getY()][ball.getX()] = Level.convertDirectionToString(ball.getDirection());
            } else if(monster.getType() == ActorType.BUG) {
                Bug bug = (Bug) monster;
                actorDetails[bug.getY()][bug.getX()] = Level.convertDirectionToString(bug.getFollowDirection()) + ":" +
                        Level.convertDirectionToString(bug.getDirection());
            }
        }

        for(int i=0; i<actorDetails.length; i++) {
            for(int j=0; j<actorDetails[0].length; j++) {
                printWriter.write(actorDetails[i][j] + ",");
            }
            printWriter.write("\n");
        }

        printWriter.write("\n");
    }
}
