package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitCounting1225 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.readLine();
      String line;
      while((line = br.readLine()) != null) {
         int num = Integer.parseInt(line);
         line = "";
         for (int i = 1; i <= num; i++ ) {
            line += i;
         }

         int[] counts = new int[10];

         for(int i = 0; i < line.length(); i++) {
            int n = Integer.parseInt(line.substring(i,i+1));
            counts[n]++;
         }

         for(int i : counts) {
            System.out.print(i + " ");
         }
         System.out.println();
      }
   }
}
