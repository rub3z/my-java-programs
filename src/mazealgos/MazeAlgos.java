
package mazealgos;



/**
 *
 * @author rubes
 */
public class MazeAlgos {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      int[][] maze = new int[8][8];
      
      int[] split = new int[8];
       //(int)(Math.random() * 8);
      int[] open  = new int[8];
       //(int)(Math.random() * 8);
      for (int i = 0; i < 8; i++) {
         split[i] = (int)(Math.random() * 8);
         open[i]  = (int)(Math.random() * 8);
      }
      
         for(int i = 0; i < 8; i++) {
            System.out.print("\n|");
            for(int j = 0; j < 8; j++) {
               for(int k = 0; k < 8; k++) {
                  System.out.print((i == open[k] || j == split[k]) ?
                   "_ " :
                   " |");
               }
               System.out.println("");
            }
         }
   }
}
