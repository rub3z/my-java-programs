package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistinctList {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.lines().forEach(line -> {
         String[] terms = line.split(" ");
         ArrayList<String> termsList = new ArrayList<>();
         for (String s : terms) {
            termsList.add(s);
         }

         termsList.stream().distinct()
          .forEach(term -> System.out.print(term + " "));
      });
   }
}
