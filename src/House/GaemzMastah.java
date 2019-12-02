//CECS 277 Project 4:
// Mystery House
//By Ruben Baerga ID #010366978

package House;

import java.util.Scanner;

/**
 * The perennially named GaemzMastah.java class
 * creates a Games Master (a self-described lord of something)
 * whose public repl() method is responsible for its logic:
 * Setting initial values for input and validation;
 * Greeting the user;
 * Prompting the user with a menu;
 * Responding accordingly;
 * And checking if the user wants to quit.
 * 
 * @author Rub3z
 */

public class GaemzMastah extends Arepl {
   private String input;
   private int userMenuSelection;
   private boolean invalidInput;

   /**
    * This main method is also for testing purposes.
    * 
    * @param args
    */
   public static void main(String[] args) {
      IRepl gm = new GaemzMastah();
      gm.repl();
   }
   
   @Override
   public void hello() {
      System.out.println("Greetings, player."
       + " You have now entered the domain of...\n\n"
       + "THE GAEMZ MASTAH, LORD OF ALL GAME-LIKE THINGS.");
   }
   
   @Override
   public void setup() {
      userMenuSelection = 1337;
      invalidInput = true;
   }
   
   @Override
   public void listen() {
      Scanner in = new Scanner(System.in);
      System.out.println("Use the number keys to select an option, man...");
      System.out.println("1. Challenge me in the Number Guess Game.");
      System.out.println("2. Challenge me to a game of Nim.");
      System.out.println("3. Enter Uncah Scrooge's Inescapable Mansion of "
       + "Hideous Quacking Horrors (title placeholder).");
      System.out.println("0. Exit this domain.");
      
      do {
         input = in.nextLine();

         try {
            userMenuSelection = Integer.parseInt(input);
         } catch (Exception NumberFormatException) {
            System.out.println("C'mon man, enter an integer.");
            invalidInput = true;
         } finally {
            if (userMenuSelection == 1337) {
               invalidInput = false;
            }
         }
      } while (!invalidInput);
   }
   
   @Override
   public void respond() {
      switch(userMenuSelection) {
         case 1:
            System.out.println("Jesus, that game is boring. No thanks.");
            break;
         case 2:
            System.out.println("Eh, That one's not that fun. I'm not that "
             + "gud at it... \n\n Just pick option 3 already.");
            break;
         case 3:
            System.out.println("Now we're talkin'.");
            IRepl mysteryHouse = new House();
            mysteryHouse.repl();
            break;
         case 0:
            System.out.println("Godspeed, player.");
            break;
         default:
            System.out.println("Please enter a valid integer.");
            break;
      }
   }
   
   @Override
   public boolean endChk() {
      return (userMenuSelection != 0);
   }
}
