package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ToLuceneQuery {
   public static void main(String[] args) throws IOException {

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.lines().forEach(line -> {
         line = line.replaceAll("\\s+OR\\s+", " ");
         line = line.replaceAll("\\s+AND\\s+", " +");
         line = line.replaceAll("\\s+NOT\\s+", " -");
         System.out.println(line);
      });
   }
}
