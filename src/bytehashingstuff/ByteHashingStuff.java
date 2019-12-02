package bytehashingstuff;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class ByteHashingStuff {

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      
      System.out.println("Enter an integer or a string.");
      
      String s = in.next();
      
      boolean isNumber = true;
      
      for (char i : s.toCharArray())
         if (!Character.isDigit(i))
            isNumber = false;
      
      byte[] num = (isNumber) ?
       ByteBuffer.allocate(4).putInt(Integer.parseInt(s)).array() :
       s.getBytes(StandardCharsets.US_ASCII);
      
      System.out.println(hashitupm8(num, num.length - 1)); 
   }
   
   public static int hashitupm8 (byte[] a, int len) {
      int toInt = Byte.toUnsignedInt(a[len]);
      return (len == 0) ?
       toInt :
       toInt + (31 * hashitupm8(a, len - 1));
   }
}
