//CECS 277 Project 1: Games Master
//By Ruben Baerga ID #010366978

package gaemzmastah;
import java.util.Scanner;

/**
 * Number_guess_game.java
 * This class has one public repl() method that's called by another class
 * to begin its loop:
 * Instantiating the done and numGuesses slots to zero and
 *  creating a random number for the game;
 * Greeting the user;
 * Prompting them to guess a number;
 * Responding by telling the user whether they're getting closer (warmer)
 * or further away (colder) ;
 * And checking whether the user's guess was right or if they want to exit.
 * 
 *
 * @author Rub3z
 */
public class Number_guess_game extends Arepl {
   private int guess;
   private int lastGuessDifference;
   private int thisGuessDifference;
   private int numGuesses;
   private int num;
   private boolean done;
   
   /**
    * This main method is for testing purposes.
    * 
    * @param args
    */
   public static void main(String[] args) {
      IRepl test = new Number_guess_game();
      test.repl();
   }
   
   @Override
   public void setup() {
      done = false;
      num = 1 + (int)(Math.random() * 1000);//rand.nextInt(999) + 10;
      numGuesses = 0;
   }
   
   @Override
   public void hello() {
      System.out.println("Greetings, player. Welcome to the...\n\n"
       + "NUMBER GUESSING GAME!!!\n\n"
       + "I'm thinking of a number between 1 and 1000, inclusive.");
   }
   
   
   @Override
   public void listen() {
      System.out.print(numGuesses == 0 ?
       "Guess a number,":
       "Guess again,");
      System.out.println(" or press 0 to quit.");
      Scanner in = new Scanner(System.in);
      guess = in.nextInt();
   }
   
   @Override
   public void respond() {
      if (guess == 0) {
         done = true;
      }
      
      else if (guess == num) {
         done = true;
         numGuesses++;
         System.out.println("NICE!!! You guessed the number!!! You win!!!");
      }
      else {
         done = false;
         
         thisGuessDifference = Math.abs(num - guess);
         System.out.print("NOPE!!! ");
         if (numGuesses != 0) {
            System.out.println(thisGuessDifference >= lastGuessDifference ?
             "Yikes. You're getting colder.":
             "You're getting warmer, man.");
         }
         lastGuessDifference = thisGuessDifference;
         numGuesses++;
         
         if (thisGuessDifference <= 5) {
            System.out.println("You're SOOO CLOSE...");
         }
         
         switch (numGuesses) {
            case 5:
               System.out.println("That's five guesses so far. Give up yet?");
               break;
            case 10:
               System.out.println("That's ten guesses.\n"
                + "Maybe you should quit.");
               break;
            case 15:
               System.out.println("Fifteen guesses...\n "
                + "Jesus, you're persistent.");
               break;
            default:
               break;
         }
         if (numGuesses >= 16) {
            System.out.println("You just never give up, do you?");
         }
      }   
   }
   
   @Override
   public boolean endChk()  {
      if (done) {
         System.out.println("Thanks for playing.");
         if (guess != num) {
            System.out.println("The number was " + num + ", by the way.");
         }
         return false;
      }
      return true;
   }
}
