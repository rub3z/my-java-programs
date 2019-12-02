package medianOfThree;

import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class Mo3 {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      System.out.println("Enter three numbers:");
      
      int a = in.nextInt();
      int b = in.nextInt();
      int c = in.nextInt();
      
      int d = (a < b) ? a : b;
      int e = (d < c) ? c : d;
      
      
      int mo3 = (e < d) ? d : e;
      
      System.out.println(mo3);
   }
   
}
