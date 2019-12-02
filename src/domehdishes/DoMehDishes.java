package domehdishes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author Rub3z
 */
public class DoMehDishes {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) throws IOException {
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String line;
      
      int m = 0;
      int n = 0;
      int lineNum = 0;
      int[] values = new int[0];
      
      while ((line = reader.readLine()) != null) {
         String input[] = line.split(" ");
         if (lineNum == 0) {
            n = Integer.parseInt(input[0]);
            m = Integer.parseInt(input[1]);
         }
         if (lineNum == 1) {
            values = new int[n];
            for (int i = 0; i < input.length; i++) {
               values[i] = Integer.parseInt(input[i]);
            }
         }
         else if (lineNum > 1){
            System.out.println("Case " + (lineNum - 1));
            maxSubArraySum(values, Integer.parseInt(input[0]), Integer.parseInt(input[1]));
         }
         lineNum++;
      }
   }
   
   public static void maxSubArraySum(int[] array, int a, int b) {
      int max = 0;
      int left = a;
      int right = b;
      
      int thisSum = 0;
      for (int i = a; i <= b; i++) {
         thisSum += array[i];
         if (thisSum > max) {
            max = thisSum;
            right++;
         }
         if (thisSum < 0) {
            thisSum = 0;
            left = i;
            right = i;
         }
      }
      
      System.out.println(left + " " + right);
   }
   
}
