package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class AddReverseNumbers {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.readLine();
      String line;
      while((line = br.readLine()) != null) {
         String rline = "";
         for(char i : line.toCharArray()) {
            rline = i + rline;
         }

         String[] rnums = rline.split(" ");
         BigInteger a = new BigInteger(rnums[0]);
         BigInteger b = new BigInteger(rnums[1]);
         BigInteger sum = a.add(b);

         String rsum = sum.toString();

         for(char i : rsum.toCharArray()) {
            rsum = i + rsum;
         }
         rsum = rsum.substring(0, rsum.length()/2);

         rsum = rsum.replaceFirst("0*", "");

         System.out.println(rsum);
      }
   }
}
