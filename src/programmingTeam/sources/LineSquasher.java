package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LineSquasher {
   public static void main(String[] args) throws IOException {

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      ArrayList<String> strings = new ArrayList<>();
      String line;
      String newLine = "";
      while ((line = br.readLine()) != null) {
         newLine += line + " ";
      }

      System.out.println(newLine);
   }
}
