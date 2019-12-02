package misskitty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MissKitty {

   public static void main(String[] args) throws IOException {
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String cases = reader.readLine();
      int numCases = Integer.parseInt(cases);
      int numLines = 0;
      int x = 0;
      
      String line;
      while ((line = reader.readLine()) != null || numLines < numCases) {
         String input[] = line.split(" ");
         int[] values = new int[input.length];
         for (int i = 0; i < input.length ; i++) {
            values[i] = Integer.parseInt(input[i]);
         }
         
         int m = values[0];
         int a = values[1];
         int b = values[2];
         int c = values[3];
         int d = values[4];
         int e = values[5];
         int f = values[6];
         int g = values[7];
         int h = values[8];
         int i = values[9];
         int j = values[10];
         int k = values[11];
         
         
         int munny = 0;
         
      
         while (munny < m) {
            switch (j * x + k) {
               case 0: 
                  munny += a*x*x + b*x + c;
                  break;
               case 1:
                  munny += d*x*x + e*x + f;
                  break;
               case 2:
                  munny += g*x*x + h*x + i;
                  break;
               default:
                  break;
            }
            x++;
         }
         System.out.println(x);
      }
   }
}
