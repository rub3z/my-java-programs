package facfreq;

import java.util.Scanner;


/**
 *
 * @author Rub3z
 */
public class FacFreq {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      Scanner in = new Scanner(System.in);

      int[] na = new int[1];
      int[] fac = new int[1];
      int nextSlot = 0;

      while (!in.hasNext("0")) {
         na[nextSlot] = in.nextInt();
         nextSlot++;
         int[] copyInt = new int[na.length + 1];
         System.arraycopy(na, 0, copyInt, 0, na.length);
         na = copyInt;
         fac = copyInt;
      }
      
      for (int i : fac)
         i = 0;
      
      for (int i = 0; i < fac.length; i++)
         for (int j = na[i] - 1; j > 0; j--) 
            fac[i] *= na[i] - j;
      
      for (int i : fac)
          System.out.println(i);

      System.out.println(fac);
   }

}
