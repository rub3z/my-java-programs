package run4yourlife;

import java.util.Random;
import java.util.Scanner;

import static run4yourlife.Run4YourLife.*;

/**
 *
 * @author Rub3z
 * Ruben Arthur Baerga, SID 010366978
 * CECS 328 Lab 1
 */
public class Test4YourLife {
   
   public static void main (String[] args) {
      
      System.out.println("Case 1: 10 values, pos & neg \n"
       + "Case 2: 11 values, pos & neg\n"
       + "Case 3: Array of length 0\n"
       + "Case 4: 10 values, neg\n"
       + "Case 5: 10 zero values\n"
       + "Case 6: 10 values, pos\n"
       + "Case 7: Enter array manually, size 10. \n"
       + "   (Good for closer analysis of previously run tests.)");
      
      Random rando = new Random();
      int randSeed = rando.nextInt();
      
      Scanner input = new Scanner (System.in);
      switch (input.nextInt()) {
         case 1:
            whatCouldPossiblyGoWrong(randArray(randSeed, 10));
            break;
         case 2:
            whatCouldPossiblyGoWrong(randArray(randSeed, 10));
            break;
         case 3:
            whatCouldPossiblyGoWrong(randArray(randSeed, 0));
            break;
         case 4:
            whatCouldPossiblyGoWrong(testArray(-1));
            break;
         case 5:
            whatCouldPossiblyGoWrong(testArray(0));
            break;
         case 6:
            whatCouldPossiblyGoWrong(testArray(1));
            break;
         case 7:
            Scanner arrayInput = new Scanner(System.in);
            String inputArray = arrayInput.nextLine();
            int[] myArray = new int[10];
            
            for (int i = 0; i < 10; i++)
               myArray[i] = Integer.parseInt(inputArray.split(" ")[i]);
            
            whatCouldPossiblyGoWrong(myArray);
            break;
         default:
            break;
      }

   }
   
   // For creating simpler arrays of length 10 for testing.
   public static int[] testArray (int posNegOrZero) {
      
      Random randTest = new Random();
      
      int[] theTestArray = new int[10];
      
      switch (posNegOrZero) {
         case 1:
            for (int i = 0; i < theTestArray.length; i++) 
               theTestArray[i] = randTest.nextInt(201) + 1;
            break;
         case -1:
            for (int i = 0; i < theTestArray.length; i++) 
               theTestArray[i] = randTest.nextInt(201) - 201;
            break;
         default:
            break;
      }
      
      for (int i : theTestArray)
         System.out.print(i + " ");
      
      System.out.println("");
      return theTestArray;
   }
   
   // Call all four algorithms.
   // What could possibly go wrong?
   public static void whatCouldPossiblyGoWrong(int[] array) {
      System.out.println(n00bAlgorithm(array));
      System.out.println(kindaMehAlgorithm(array));
      System.out.println(prettyGudAlgorithm(array, 0, array.length - 1));
      System.out.println(super1337proHaxxorAlgorithm(array));
   }
}
