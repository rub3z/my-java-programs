package wordindex;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author rubes
 */
public class WordIndex {

   public static void main(String[] args) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
      br.lines().forEach(line -> {
         int len = line.length();
         int sum = 0;
         if (len == 1) {
            System.out.println(line.charAt(0) - 96);
         }
         if (len == 2) {
            int i = 26;
            for (; i >= 26 - (line.charAt(0) - 97); i--) {
               sum += i;
            }
            sum += line.charAt(1) - line.charAt(0);
            System.out.println(sum);
         }
         if (len == 3) {
            
         }
      });
   }
}
