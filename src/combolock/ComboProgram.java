package combolock;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class ComboProgram {
      
   public static void main(String[] args) {
      final int NUMBER_OF_POSITIONS = 40;

      Random randomLockCombo = new Random();
      int firstNum = randomLockCombo.nextInt(NUMBER_OF_POSITIONS);
      int secondNum = randomLockCombo.nextInt(NUMBER_OF_POSITIONS);
      int thirdNum = randomLockCombo.nextInt(NUMBER_OF_POSITIONS);
      
      System.out.println("Random combination is: " + firstNum + " "
       + secondNum + " " + thirdNum + "\n\n");

      ComboLock randomLock = new ComboLock(firstNum, secondNum, thirdNum);
      
      Scanner input = new Scanner(System.in);
      int[] userNumbers = new int[3];
      int iterations = 1;
      
      do {
         System.out.println("Guess three integers from 0 to 39, or "
          + "enter \"0 0 0\" to quit:");
         
         for ( int i = 0; i < userNumbers.length; i++) {
            userNumbers[i] = input.nextInt();
         }

         randomLock.turnRight(NUMBER_OF_POSITIONS - userNumbers[0]);
         randomLock.turnLeft(NUMBER_OF_POSITIONS - (userNumbers[0] 
          - userNumbers[1]));
         randomLock.turnRight(NUMBER_OF_POSITIONS - (userNumbers[2] 
          - userNumbers[1]));
         
         if (randomLock.open()) {
            System.out.println("CONGLATURATION!!!\n\n...You obviously " 
             + "cheated. Cheaters never prosper, you cheating cheater.");
            break;
         }
         else if (userNumbers[0] != 0 || userNumbers[1] != 0 
          || userNumbers[2] != 0) {
            
            System.out.print("Sorry, that's incorrect. ");
            if (iterations == 1) {
               System.out.println("Do you really think you're going to "
                + "guess it correctly?\nIt's not like it's already displayed "
                 + "on the screen or something like that.\n");
            }
            if (iterations == 2) {
                System.out.println("But you can keep trying if you like.\n");
            }
            if (iterations == 3) {
                System.out.println("Jesus, you're persistent.\n");
            }
            if (iterations >= 4) {
                System.out.println("You just don't give up, do you?\n");
            }
         }
         else if (userNumbers[0] == 0 && userNumbers[1] == 0 
          && userNumbers[2] == 0) {
            System.out.println("You quitter.");
         }
         iterations++;
      } while (userNumbers[0] != 0 || userNumbers[1] != 0 
         || userNumbers[2] != 0);
   }
}
