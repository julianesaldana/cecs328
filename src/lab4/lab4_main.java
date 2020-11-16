package lab4;

import java.io.*;
import java.util.*;

public class lab4_main {
    public static void main(String[]arg) throws FileNotFoundException {
        File homeruns = new File("players_homeruns.csv");
        Scanner fileScanner = new Scanner(homeruns);
        RedBlackTreeMap<String, Integer> players = new RedBlackTreeMap<String, Integer>();

        int ctr = 0;
        while (fileScanner.hasNextLine() && ctr < 5){
            String [] tempArray = fileScanner.nextLine().split(",");
            int tempHomeruns = Integer.parseInt(tempArray[1]);
            players.add(tempArray[0], tempHomeruns);
            System.out.printf("Adding %s : %d\n", tempArray[0], tempHomeruns);
            ctr++;
        }


        System.out.println();
        System.out.println(players.getmRoot());
        System.out.println();
        players.printStructure(players.getmRoot());
    }
}
