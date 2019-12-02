package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BirthsAndDeaths {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line = "";
      int[] numAlive = new int[10000];

      int year = Integer.parseInt(br.readLine());

      while ((line = br.readLine()) != null) {
         String[] nums = line.split(" ");
         int b = Integer.parseInt(nums[0]);
         int d = Integer.parseInt(nums[1]);

         if (b == 0 && d == 0) {
            break;
         }

         for(int i = b; i <= d; i++) {
            numAlive[i]++;
         }
      }
      System.out.println(
       "Number of people alive in " + year + ": " + numAlive[year]);
   }
}

