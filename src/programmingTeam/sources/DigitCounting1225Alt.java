package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitCounting1225Alt {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.readLine();
      br.lines().map(Integer::parseInt)
       .map(n -> {
          String line = "";
          for (int i = 1; i <= n; i++) line += i;
          return line;
       })
       .forEach(line -> {
          int[] counts = new int[10];
          for (int i = 0; i < line.length(); i++) {
             counts[Integer.parseInt(line.substring(i,i+1))]++;
          }
          for(int i : counts) System.out.print(i + " ");
          System.out.println();
       });
   }
}
