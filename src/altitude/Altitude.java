package altitude;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Altitude {

   public static void main(String[] args) throws FileNotFoundException {
      
      Scanner input = new Scanner(new File(
       "C:\\altitude\\src\\altitude\\altitude.txt"));
      int[] x = new int[400];
      int[] y = new int[400];
      double[] altitude = new double[400];
      int i = 0;
      while (input.hasNextLine()) {
         
         x[i] = Integer.parseInt(input.next());
         y[i] = input.nextInt();
         altitude[i] = input.nextDouble();
      }
      
      System.out.println(x[1]);
      System.out.println(y[1]);
      System.out.println(altitude[1]);
      
   }

   public void printMax(int[] x, int[] y, double a[]) {
      
      System.out.print("The maximum altitude is ");
   }

   public void printMin(int[] x, int[] y, double a[]) {
      
      System.out.print("The minimum altitude is ");
   }
}
