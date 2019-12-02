package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StringSort {
   public static void main(String[] args) throws IOException {

   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.lines().forEach(line -> {
         String[] terms = line.split("\\s+OR\\s+");
         ArrayList<String> termsList = new ArrayList<>();
         for (String s : terms) {
            termsList.add(s);
         }
         termsList.stream()
          //.distinct()
          .sorted()
          .forEach(term -> {
             term += " OR ";
             System.out.print(term);
          });
      });
   }
}
