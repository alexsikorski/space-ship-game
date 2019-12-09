package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {

    public static ArrayList<Integer> OpenAndRead() {
        ArrayList<Integer> leaderList = new ArrayList<>(); // array that holds the contents of the txt


        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("src/utilities/leaderboard.txt"));
            String line = null;

            while ((line = br.readLine()) != null) {    // stops when zero characters are found
                Integer result = Integer.parseInt(line);
                leaderList.add(result);
            }
            // TEST
            // for (int i = 0; i <10; i++ ){
            //    System.out.println(leaderList.get(i));
            //}
            // TEST

            br.close();

        } catch (FileNotFoundException e) {
            System.err.println("LEADERBOARD.TXT NOT FOUND");

        } catch (IOException e) {
            System.err.println("UNABLE TO READ FILE");
        }
        return leaderList;
    }
}
