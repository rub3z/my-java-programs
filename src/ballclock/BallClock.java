package ballclock;
 
import java.util.ArrayList;
import java.util.Scanner;

public class BallClock {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      
      ArrayList<Integer> balls = new ArrayList<>();
      
      ArrayList<Integer> min = new ArrayList<>();
      ArrayList<Integer> min5 = new ArrayList<>();
      ArrayList<Integer> hours = new ArrayList<>();
      
      ArrayList<Integer> input = new ArrayList<>();
      
      while(in.hasNextLine())
         input.add(in.nextInt());
      
      for(Integer j : input) {
         for(int i = 0; i < j; i++)
            balls.add(i);
         
         for(int k = 0; k < Integer.MAX_VALUE; k++) {
            int b = balls.remove(balls.size() - 1);
            if (k % 5 != 4)
               min.add(b);
            if (k % 5 == 4) {
               min5.add(b);
               for(int i = min.size() - 1; i >= 0; i--)
                  balls.add(0, min.remove(i));
            }
            
            
         }
         
      }
   }
   
}
