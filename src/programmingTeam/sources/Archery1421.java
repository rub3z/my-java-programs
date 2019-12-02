package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Archery1421 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.readLine();
      String line;
      while ((line = br.readLine()) != null) {
         int w = Integer.parseInt(line);
         int n = Integer.parseInt(br.readLine());
         int[][] targets = new int[3][n];
         for (int i = 0; i < n; i++) {
            String[] nTarget = br.readLine().split(" ");
            targets[0][i] = Integer.parseInt(nTarget[0]);
            targets[1][i] = Integer.parseInt(nTarget[1]);
            targets[2][i] = Integer.parseInt(nTarget[2]);
         }

         

      }
   }
}