package gaemzmastah;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class Nim extends Arepl {
   public class Nim_Brain {
      private int brainRow;
      private int brainPebls;
      
      public Nim_Move makeAMove(Nim gameState) {
         int numRows = gameState.getBoard().length;
         int choose = 1337;
         while (brainRow != choose) {
            choose = (int)(Math.random()*numRows);
            if(gameState.getBoard()[choose] != 0) {
               brainRow = choose;
            }
         }
         
         brainPebls = (int)(Math.random() * gameState.getBoard()[brainRow]) + 1;
         
         Nim_Move brainMove = new Nim_Move(brainRow, brainPebls);
         return brainMove;
      }
   }
   
   public class Nim_Move {
      private int rowChoice;
      private int peblChoice;
      
      public Nim_Move (int row, int pebls) {
         rowChoice = row;
         peblChoice = pebls;
      }

      public int getRowChoice() {
         return rowChoice;
      }

      public int getPeblChoice() {
         return peblChoice;
      }
      
      public boolean quit() {
         return rowChoice < 0 || peblChoice <= 0 ;
      }
      
      
      
   }
   
   private String userInput;
   private boolean done;
   private int[] board = new int[0];
   private int rowNum;
   private int peblNum;
   private int numTurns;
   private Nim_Brain theBrain;
   private Nim_Move theMove;
   
   public static void main(String[] args) {
      IRepl test = new Nim();
      test.repl();
   }

   public int[] getBoard() {
      return board;
   }

   @Override
   public void hello() {
      System.out.println("Greetings, player. Welcome to...\n\n"
       + "NIM!!!\n\n"
       + "The rules are as follows: \n"
       + "From any one of the rows shown, you may take 1 or more \no lett - "
       + "I mean, pebbles - even the entire row if you so choose.\n"
       + "We shall take turns removing pebbles until one player takes the "
       + "last one...\nthat player shall be the victor.\n"
       + "I shall allow you to go first.\n\n"
       + "PREPARE YOURSELF; HUMAN!!!" );
   }

   @Override
   public void setup() {
      if (numTurns == 0) {
         board = new int[(int)(Math.random() * 4) + 3];
         for (int i = 0; i < board.length ; i++) {
            board[i] = (int)(Math.random() * 6) + 3;
         }
      }
      done = false;
      numTurns++;
   }

   @Override
   public void listen() {
      displayBoard();
      
      Scanner in = new Scanner(System.in);
      System.out.println("Enter the row number followed by the number "
       + "of pebbles you'd like to take; or enter 0 to quit.");
      rowNum = in.nextInt();
      peblNum = in.nextInt();
      if (rowNum != 0 && peblNum != 0) {
         System.out.print("Row ");
         if (rowNum > board.length)
            rowNum = board.length;
         System.out.print(rowNum);
         rowNum--; 
         System.out.print(": removing ");
         if(peblNum > board[rowNum])
            peblNum = board[rowNum];
         System.out.println(peblNum == board[rowNum] ?
          "all" :
          peblNum);
         System.out.println(" pebbles.");
      }  
      
      Nim_Move playerMove = new Nim_Move(rowNum, peblNum);
      theMove = playerMove;
   }

   @Override
   public void respond() {
      if (theMove.quit())
         done = true;
      else {
         board[theMove.getRowChoice()] -= theMove.getPeblChoice();
         
         displayBoard();
         
         if (win()) {
            done = true;
            System.out.println("YOU HAVE BESTED ME!?\nUNPOSSIBRU!!!\n\n"
             + "...you must have cheated.");
         }
         else {
            System.out.println("I shall move now...");
            
            Nim_Brain thisBrain = new Nim_Brain();
            theBrain = thisBrain;
            
            Nim_Move brainMove = theBrain.makeAMove(this);
            
            
            rowNum = brainMove.getRowChoice();
            peblNum = brainMove.getPeblChoice();
            
            board[rowNum] -= peblNum;
            
            System.out.println("From row " + (rowNum + 1) 
             + ", I shall remove " + peblNum + " pebbles.");
            
            if(win()) {
               done = true;
               System.out.println("MWAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHA!!!\n"
                + "It appears I have bested you, puny player. You lose.");
            }
         }
      }
   }

   @Override
   public boolean endChk() {
      if (done) {
         System.out.println("Thanks for playing.");
         return false;
      }
      return true;
   }
   
   public boolean win() {
      int totalPebls = 0;
      for (int i : board)
         totalPebls += i;
      
      return totalPebls == 0;
   }
   
   public void displayBoard() {
      for (int i = 0; i < board.length ; i++) {
            System.out.print((i + 1) + ":");
            for (int j = 0; j < board[i] ; j++) {
               if (j % 3 == 0)
                  System.out.print(" ");
               System.out.print("o");
            }
            System.out.println("");
         }
   }
}
