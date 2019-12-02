package treeset;

import java.util.Scanner;

/**
 * 
 * @author Rub3z
 */
public class HangmanGame {
   
   public static void main(String[] args) {
      final int MAX_NUMBER_OF_MISSES = 6;
      
      TreeSet correctGuesses = new TreeSet();
      TreeSet wrongGuesses = new TreeSet();
      Scanner input = new Scanner(System.in);
      
      //Input checkers
      boolean hasMoreThanLetters = false;
      boolean correctInput = false;
      
      String theWord = "";
      char theGuessedLetter = ' ';
      
      //A loop for the first player's input.
      while (!correctInput) {
         System.out.println("Player One: Enter the secret word or phrase.");
         theWord = input.nextLine().toLowerCase();
         
         //Ignores whitespaces
         for (char i : theWord.toCharArray()) {
            if (!Character.isLetter(i) && !Character.isWhitespace(i)) {
               hasMoreThanLetters = true;
            }
         }
         
         if (hasMoreThanLetters) {
            System.out.println("Don't be a smart-aleck. Use only letters,"
             + "dude.");
            hasMoreThanLetters = false;
         }
         else {
            correctInput = true;
         } 
      }
      
      //Print a whole bunch of lines to hide the word that was typed...
      System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
       + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      
      //Loop for other player(s) input.
      while (wrongGuesses.getCount() < MAX_NUMBER_OF_MISSES) {
         if (theGuessedLetter == ' ') {
            System.out.println("Other Player(s): Go ahead and guess "
             + "a letter.");
            //More than two people can play Hangman, y'know!!!
         }
         else {
            System.out.println("Guess another letter.");
         }
         
         String guess = input.nextLine().toLowerCase();
         
         theGuessedLetter = ' ';
         
         //Reset the non-letter checker thingy
         hasMoreThanLetters = false;
         //And counting the number of letters input
         int letterCount = 0;
         
         //Checking if the guess input is...
         for (char i : guess.toCharArray()) {
            if (Character.isLetter(i)) {  //1. A letter
               theGuessedLetter = i;
               letterCount++;             //2. Only one letter and...
            }
            if (!Character.isLetter(i) && !Character.isWhitespace(i)) {
               hasMoreThanLetters = true; //3. Not a number or other character
            }
         }

         //Tell 'em
         if (letterCount > 1 || hasMoreThanLetters) {
            System.out.println("Sorry bro, you can only guess one letter "
             + "at a time. \nCan't be cheeky by typing numbers or special "
             + "characters, either.");
         }
         else {
            boolean wrongGuess = true;
            boolean sameLetter = false;
            
            if (correctGuesses.find(theGuessedLetter) ||
             wrongGuesses.find(theGuessedLetter)) {
               sameLetter = true;
            }
            
            for (char i : theWord.toCharArray()) {
               if (i == theGuessedLetter && theGuessedLetter != ' ') {
                  if (!correctGuesses.find(i)) {
                     correctGuesses.add(i);
                     System.out.println("NICE!!!");
                  }
                  wrongGuess = false;
                  break;
               }
            }

            if(wrongGuess && theGuessedLetter != ' ' && !sameLetter) {
               wrongGuesses.add(theGuessedLetter);
               System.out.println("NOPE!!!");
            }
            else if (sameLetter) {
               System.out.println("You've entered that letter already, bro.");
            }
            else if (theGuessedLetter == ' ') {
               System.out.println("Why did you only enter spaces??? \n"
                + "I understand it's the largest button on the keyboard, but "
                + "geez. \nIs it that hard for you to hit any of the other "
                + "ones???");
               //This next line ensures that the prompt to enter another
               //is displayed instead of the first prompt.
               theGuessedLetter = '0';
            }

            //This should be fun.
            printBody(wrongGuesses.getCount());

            if (printSecretWord(correctGuesses, theWord)) {
               break;
            }
            
            printMisses(wrongGuesses);
         }
      }
      
      if (wrongGuesses.getCount() == MAX_NUMBER_OF_MISSES) {
         System.out.println("HAHAHAHAHAHAHAHA YOU LOST, LOSER. \n"
          + "What's the matter? Was it too hard for you? \n"
          + "Git gud, or go cry in a corner. \n"
          + "Wimp.");
      }
      
      
   }
   
   public static void printBody(int numMisses) {
      switch (numMisses) {
         
         //I thought it looked wierd when the man was all the way to the left
         case 1 :
            System.out.println("    O \n\n");
            break;
         case 2 :
            System.out.println("    O ");
            System.out.println("    | \n");
            break;
         case 3 :
            System.out.println("    O ");
            System.out.println("   /| \n");
            break;
         case 4 :
            System.out.println("    O ");
            System.out.println("   /|\\\n");
            break;
         case 5 :
            System.out.println("    O ");
            System.out.println("   /|\\");
            System.out.println("   /");
            System.out.println("One miss left...");
            break;
         case 6 :
            System.out.println("    | ");
            System.out.println("    O ");
            System.out.println("   /|\\");
            System.out.println("   / \\");
            System.out.println("HANGED!!!");
            break;
         default :
            break;
      }
      System.out.println("");
   }
   
   public static boolean printSecretWord(TreeSet correctGuesses,
                                         String theWord) {
      String formattedSecretWord = "";
      
      for (char i : theWord.toCharArray()) {
         if (correctGuesses.find(i)) {
            formattedSecretWord += i + " ";
         }
         else if (Character.isWhitespace(i)) {
            formattedSecretWord += "  ";
         }
         else {
            formattedSecretWord += "_ ";
         }
      }
      System.out.println(formattedSecretWord);
      
      /**
       * Replace all spaces in the phrase and the formatted version.
       * This makes the following if statement much less congested.
       * Ideally, I would have found a way to make this work by replacing
       * all the spaces outside of the loop structure instead of running
       * this replaceAll() method so many times. Unfortunately, I couldn't 
       * find  a way to do so that'd allow me to put spaces in the formatted 
       * word as above, which detects whitespaces accordingly.
       */
      theWord = theWord.replaceAll(" ", "");
      formattedSecretWord = formattedSecretWord.replaceAll(" ", "");
      
      if (theWord.equals(formattedSecretWord)) {
         System.out.println("CONGLATURATION!!! YOU WON! \n"
          + "... This doesn't mean you're really smart or anything. \n"
          + "The other guy could just be dumb. It was probably an easy one.");
         return true;
      }
      else {
         System.out.println("");
         return false;
      }
   }
   
   public static void printMisses(TreeSet missedGuesses) {
      missedGuesses.printAll();
      System.out.println("");
      //Easy peasy
   }
}
