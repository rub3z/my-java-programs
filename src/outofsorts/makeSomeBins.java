package outofsorts;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Rub3z
 */
public class makeSomeBins {

   public static void main(String[] args)
    throws FileNotFoundException, IOException {
      int n = 100000;
      
      DataOutputStream out = 
       new DataOutputStream(new FileOutputStream("sorted.bin"));
      
      out.writeInt(n);
      
      for (int i = 1; i <= n; i++)
         out.writeInt(i);
      
      out.flush();
      
      out = new DataOutputStream(new FileOutputStream("reverse.bin"));
      
      out.writeInt(n);
      
      for (int i = n; i > 0; i--) 
         out.writeInt(i);
      
      out = new DataOutputStream(new FileOutputStream("random.bin"));
      
      out.writeInt(n);
      
      Random rando = new Random();
      
      for (int i = 0; i < n; i++) 
         out.writeInt(rando.nextInt());
      
   }
   
}
