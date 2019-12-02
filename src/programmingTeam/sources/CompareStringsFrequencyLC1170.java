package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompareStringsFrequencyLC1170 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      String[] queries = br.readLine().split(" ");
      String[] words = br.readLine().split(" ");

      List<Long> queryValues = Arrays.stream(queries).map(query -> {
         int[] values = new int[query.length()];
         for (int i = 0; i < values.length; i++) {
            values[i] = query.charAt(i);
         }

         int smallest = Arrays.stream(values).min().getAsInt();

         long freq = Arrays.stream(values).filter(n -> n == smallest).count();
         return freq;
      }).collect(Collectors.toList());

      List<Long> wordValues = Arrays.stream(words).map(query -> {
         int[] values = new int[query.length()];
         for (int i = 0; i < values.length; i++) {
            values[i] = query.charAt(i);
         }

         int smallest = Arrays.stream(values).min().getAsInt();

         long freq = Arrays.stream(values).filter(n -> n == smallest).count();
         return freq;
      }).collect(Collectors.toList());

      List<Long> answers = queryValues.stream()
       .map(q -> wordValues.stream().filter(w -> w > q).count())
       .collect(Collectors.toList());

      int[] ans = new int[answers.size()];
      for(int i = 0; i < ans.length; i++) {
         ans[i] = answers.get(i).intValue();
      }

      for(int i : ans) System.out.print(i + " ");
      System.out.println();
   }
}
