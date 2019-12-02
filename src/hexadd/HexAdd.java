package hexadd;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class HexAdd {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) throws IOException {
      
//      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//      String line;
//
//      while ((line = reader.readLine()) != null) {
//            String input[] = line.split();
//            int[] values = new int[input.length];
//            for (int i = 0; i < input.length ; i++) {
//            	values[i] = Integer.parseInt(input[i]);
//            }
//
//      }
//      
      Scanner in = new Scanner(System.in);
      
      String s = in.nextLine();

      int x = Integer.parseInt(s.split(" [+-] ")[0], 16);
      
      int y = Integer.parseInt(s.split(" [+-] ")[1], 16);
      
      int z = 0;
      
      
      if (s.contains("+")) {
         z = x + y;
         System.out.println(
         String.format("%13s", Integer.toBinaryString(x)).replaceAll(" ", "0") +
         " + " +
         String.format("%13s", Integer.toBinaryString(y)).replaceAll(" ", "0") +
         " = " + z);
      }
      else if (s.contains("-")) {
         z = x - y;
         System.out.println(
         String.format("%13s", Integer.toBinaryString(x)).replaceAll(" ", "0") +
         " - " +
         String.format("%13s", Integer.toBinaryString(y)).replaceAll(" ", "0") +
         " = " + z);
      }
      
      
      
   }
   
}
