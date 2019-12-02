package sinkequilibrium;

import java.io.IOException;
import java.util.ArrayList;

/**
 * CECS 455 Game Theory Spring 2017
 * Assignment 3: Sink Equilibrium.
 * 
 * @author Ruben Baerga
 * ID #010366978
 */
public class SinkEquilibrium {
   
   // This is so the bestResponse() method can use it.
   static Utility util = new Utility();
   
   public static void main(String[] args) throws IOException {
      // Import using the mysterious utility function.
      util.importUtility();
      
      // Create a list of numbers for the players; values initialized
      // to zero. This is a strategy profile.
      ArrayList<Integer> moves = new ArrayList<>();
      for(int i = 0; i < util.getNumPlayers(); i++)
         moves.add(0);
      
      // This list of Strings will store newly found strategy profiles.
      ArrayList<String> strats = new ArrayList<>();
      String s;
      
      // Indexes for players.
      int p1 = 0;
      int p2 = 1;
      
      // Loop control.
      int i = 0;
      
      // Index of beginning and end of the sink equilibrium best response
      // cycle found by generating the the list of profiles.
      // Once we've found said cycle, it's as if there's a long string
      // of strategy profiles still attached to it. This index allows us
      // to *snip* it off.
      int index = 0;
      
      // BEGIN!!! Let's hope this works....
      while(i < 1000) {
         // The next two if statements check if a best response to the
         // current strategy profile for p1 or p2 can be found. If it can, then
         // we check if the new strategy profile *is already in* our list.
         // If not, we add it in. If so, we note the index of where the 
         // matching profile is in our current list and throw it on top anyway
         // (it's a String that will be the last line of our results file).
         if (bestResponse(moves, p1)) {
             s = newVertex(moves);
            if(strats.indexOf(s) == -1)
               strats.add(s);
            else {
               index = strats.indexOf(s);
               strats.add(s);
               break;
            }
         }
         if (bestResponse(moves, p2)) {
             s = newVertex(moves);
            if(strats.indexOf(s) == -1)
               strats.add(s);
            else {
               index = strats.indexOf(s);
               strats.add(s);
               break;
            }
         }
         i++;
         
         // This is what happens if finding the solution is *a bit* less 
         // trivial than we'd hope. Once we try looking for best responses 
         // between the two players a thousand times, we reset the iterator;
         // clear the profiles list, and increment the p2 index... changing
         // the player with which p1 would like to play. Then we do it again.
         if (i == 1000) {
            i = 0;
            strats.clear();
            p2++;
         }
         
         // If p1 has tried trading moves back in forth with EVERY player in 
         // the list, then the player at the next index can try doing so.
         if (p2 == 20) {
            p1++;
            p2 = 0;
         }
      }
      // This algorithm will continue until it finds a sink or every player 
      // has traded blows with each other. Note that there's no mechanism in 
      // place to reset p1's counter; so if it ever reached that point it 
      // would completely fail. In that case, it may not really be all that
      // useful to reset it anyway.
      
      // This is where the *snipping* takes place as described above.
      for (int j = 0; j < index; j++) {
         strats.remove(0);
      }
      
      // Print the result. If we've found one, of course.
      strats.stream().forEach(System.out::println);
      
   }
   
   // This returns a String of the form described in the assignment
   // description, a list of moves for each player (a strategy profile)
   // separated by commas and spaces and bookended by square brackets.
   public static String newVertex(ArrayList<Integer> moves) {
      String new1 = "[";
      for(int j = 0; j < moves.size(); j++) {
         new1 += moves.get(j).toString();
         if(j != moves.size() - 1) {
            new1 += ", ";
         }
      }
      new1 += "]";
      return new1;
   }
   
   // The best response algorithm! It's very greedy.
   // Using the evaluation function of the utility class, we extract the 
   // current utility of a given player in this state, and note his current
   // move. Then we use a loop to find the player's utility for every move
   // that he can make; looking for the value of the best move they can make 
   // from here. Then, we set the player's move to that move.
   //
   // If a best move has been found, the function returns true.
   // If the best move is the current move of the player (before we
   // started all this) then the function returns false.
   public static boolean bestResponse(ArrayList<Integer> moves, int player) {
      
      System.out.print("Player: " + player + " ");
      System.out.println(" Current move: " + moves.get(player) + " ");
      int currentUtil = util.evaluate(moves).get(player);
      int bestUtil = 0;
      int currentMove = moves.get(player);
      int bestMove = currentMove;
      int nextUtil;
      
      for(int i = 0; i < 100; i++) {
         moves.set(player, i);
         if ((nextUtil = util.evaluate(moves).get(player)) > currentUtil) {
            if (nextUtil - currentUtil > bestUtil) {
               bestUtil = nextUtil - currentUtil;
               bestMove = i;
            }
         }
      }
      
      moves.set(player, bestMove);
      
      if(bestMove == currentMove)
         System.out.println("No better move...");
      else System.out.println("Move found: " + bestMove);
      
      return bestMove != currentMove;
   }
}
