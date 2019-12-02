package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BullsAndCowsLC299 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String secret;
      while ((secret = br.readLine()) != null) {
         String guess = br.readLine();

         int a =0, b = 0;
         int[] counts = new int[10];
         for(int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char g = guess.charAt(i);

            if (s == g) a++;
            else {
               switch (s) {
                  case '0': counts[0]++; break;
                  case '1': counts[1]++; break;
                  case '2': counts[2]++; break;
                  case '3': counts[3]++; break;
                  case '4': counts[4]++; break;
                  case '5': counts[5]++; break;
                  case '6': counts[6]++; break;
                  case '7': counts[7]++; break;
                  case '8': counts[8]++; break;
                  case '9': counts[9]++; break;
                  default: break;
               }

               switch (g) {
                  case '0': counts[0]++; break;
                  case '1': counts[1]++; break;
                  case '2': counts[2]++; break;
                  case '3': counts[3]++; break;
                  case '4': counts[4]++; break;
                  case '5': counts[5]++; break;
                  case '6': counts[6]++; break;
                  case '7': counts[7]++; break;
                  case '8': counts[8]++; break;
                  case '9': counts[9]++; break;
                  default: break;
               }
            }

         }

         for(int i : counts) {
            if (i > 1) b += (i - 1);
         }

         System.out.println(a + "A" + b + "B");
      }
   }
}
