// julian saldana 018462169
package lab3;

import java.io.*;
import java.util.*;

public class lab3_main {
    public static void main(String[]arg) throws FileNotFoundException{
        HashSet hs1 = new HashSet();
        hs1.HashMap(50);

        File trumpFile = new File("trump_speech.txt");
        Scanner fileScanner = new Scanner(trumpFile);

        while (fileScanner.hasNext()) {
            String word = fileScanner.next().replaceAll("[^A-Z a-z 0-9]", "");
            if (!word.equals("")){
                hs1.add(word);
            }
        }
        fileScanner.close();

        System.out.printf("There are %d distinct words.", hs1.count());
    }
}
