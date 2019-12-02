package bookletprinting;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rubes
 */
public class BookletPrinting {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      
      ArrayList<Integer> pages = new ArrayList<>();
      
      int n = 1337;
      
      while(n != 0) {
         n = in.nextInt();
         pages.add(n);
      }
      
      for(Integer i : pages) {
         if (i == 0) break;
         int sheets = (i - 1)/4 + 1;
         
         System.out.println("Printing order for " + i + " pages:");
         for(int j = 1; j <= sheets; j++) {
            System.out.println("Sheet " + j + ", front: ");
            if (j == 1 && i % 4 > 0) System.out.print("Blank");
            else System.out.print(sheets*4 );
            
         }
      }
   }
   
}
