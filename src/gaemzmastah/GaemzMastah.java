//CECS 277 Project 1: Games Master
//By Ruben Baerga ID #010366978

package gaemzmastah;

import java.util.Scanner;

/**
 * The perennially named GaemzMastah.java class
 * creates a Games Master (a self-described lord of something)
 * whose public repl() method is responsible for its logic:
 * Setting done slot to false;
 * Greeting the user;
 * Prompting the user with a menu;
 * Responding accordingly;
 * And checking if the user wants to quit.
 * 
 * @author Rub3z
 */

public class GaemzMastah extends Arepl {
   private int userMenuSelection;

   /**
    * This main method is also for testing purposes.
    * 
    * @param args
    */
   public static void main(String[] args) {
      IRepl test = new GaemzMastah();
      test.repl();
   }
   
   @Override
   public void hello() {
      System.out.println("Greetings, player."
       + " You have now entered the domain of...\n\n"
       + "THE GAEMZ MASTAH, LORD OF ALL GAME-LIKE THINGS.");
   }
   
   @Override
   public void setup() {}
   
   @Override
   public void listen() {
      Scanner in = new Scanner(System.in);
      System.out.println("Use the number keys to select an option, man...");
      System.out.println("1. Challenge me in the Number Guess Game.");
      System.out.println("2. Challenge me to a serious game of Nim.");
      System.out.println("0. Exit this domain.");
      userMenuSelection = in.nextInt();
   }
   
   @Override
   public void respond() {
      switch(userMenuSelection) {
         case 1:
            IRepl ng = new Number_guess_game();
            ng.repl();
            break;
         case 2:
            IRepl nim = new Nim();
            nim.repl();
         case 0:
            System.out.println("Godspeed, player.");
            break;
         default:
            break;
      }
   }
   
   @Override
   public boolean endChk() {
      return (userMenuSelection != 0);
   }
}
