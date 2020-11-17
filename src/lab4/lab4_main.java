package lab4;

import java.io.*;
import java.util.*;

public class lab4_main {
    public static void main(String[] arg) throws FileNotFoundException {
        File homeruns = new File("players_homeruns.csv");
        Scanner fileScanner = new Scanner(homeruns);
        RedBlackTreeMap<String, Integer> players = new RedBlackTreeMap<String, Integer>();

        // first 5
        int ctr = 0;
        while (fileScanner.hasNextLine() && ctr < 5) {
            String[] tempArray = fileScanner.nextLine().split(",");
            int tempHomeruns = Integer.parseInt(tempArray[1]);
            players.add(tempArray[0], tempHomeruns);
            ctr++;
        }

        System.out.println("Print structure using pre-order traversal for first 5 names:");
        players.printStructure(players.getmRoot());

        // next 5
        while (fileScanner.hasNextLine() && ctr < 10) {
            String[] tempArray = fileScanner.nextLine().split(",");
            int tempHomeruns = Integer.parseInt(tempArray[1]);
            players.add(tempArray[0], tempHomeruns);
            ctr++;
        }

        System.out.println("\nPrint structure using pre-order traversal for 10 added names total:");
        players.printStructure(players.getmRoot());

        System.out.printf("\na) Finding key that is a leaf (Willie Mays): %d", players.find("Willie Mays"));
        System.out.printf("\nb) Finding key that is a root (Honus Wagner): %d", players.find("Honus Wagner"));
        System.out.printf("\nc) Finding key that has one NIL child and one non-NIL child (Rogers Hornsby): %d", players.find("Rogers Hornsby"));
        System.out.printf("\nd) Finding key that is in a red node (Ted Williams): %d\n", players.find("Ted Williams"));

        System.out.println("\nAdding the rest of the names to the red black tree...");
        while (fileScanner.hasNextLine()) {
            String[] tempArray = fileScanner.nextLine().split(",");
            int tempHomeruns = Integer.parseInt(tempArray[1]);
            players.add(tempArray[0], tempHomeruns);
            ctr++;
        }

        System.out.printf("\na) Finding key that is a leaf (Willie Mays): %d", players.find("Willie Mays"));
        System.out.printf("\nb) Finding key that is a root (Honus Wagner): %d", players.find("Honus Wagner"));
        System.out.printf("\nc) Finding key that has one NIL child and one non-NIL child (Rogers Hornsby): %d", players.find("Rogers Hornsby"));
        System.out.printf("\nd) Finding key that is in a red node (Ted Williams): %d", players.find("Ted Williams"));
    }
}
