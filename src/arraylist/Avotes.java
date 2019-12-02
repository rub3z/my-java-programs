package arraylist;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class Avotes {
   
   public static void main(String args[]) {
      Scanner in = new Scanner(System.in);
      
      int numCandidates = in.nextInt();
      
      ArrayList[] votes = new ArrayList[1001];
      
      for (ArrayList i : votes) {
         for (int j = 0; j < numCandidates ; j++) {
            i.addLast(in.nextInt());
         }
      }
      
      
      int[] numVotesPerCandidate = new int[numCandidates];
      
      for (int i = 0; i < numCandidates ; i++) {
         for (int j = 0; j < 1001; j++) {
            if (votes[j].get(0) == i)
               numVotesPerCandidate[i]++; 
         }
      }
      
      

      
      
   }
}
