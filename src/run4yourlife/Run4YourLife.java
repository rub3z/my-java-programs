package run4yourlife;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 * Ruben Arthur Baerga, SID 010366978
 * CECS 328 Lab 1
 */
public class Run4YourLife {

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      
      System.out.println("Let's get in shape and run some algorithms, bro.\n"
       + "Each time we run one, we'll clock how long it takes.\n"
       + "Enter a number to select an option:\n"
       + "1. Quit and give up on running algorithms. Weakling. \n"
       + "2. Run Freddy's algorithm.\n"
       + "3. Run Susie's algorithm.\n"
       + "4. Run Johnny's algoritm.\n"
       + "5. Run Sally's algorithm.");

      int select = in.nextInt();

      System.out.println("Enter two integers. \n"
       + "The first should be the seed "
       + "value and the second will be the input size.");
      int theSeed = in.nextInt();
      int theSize = in.nextInt();

      int[] theArray = randArray(theSeed, theSize);

      long startTime = 0;
      long endTime = 0;

      switch (select) {
         case 2:
            for (int i = 0; i < 5; i++) {
               n00bAlgorithm(theArray);
            }
            startTime = System.nanoTime();
            n00bAlgorithm(theArray);
            endTime = System.nanoTime();
            break;
         case 3:
            for (int i = 0; i < 5; i++) {
               kindaMehAlgorithm(theArray);
            }
            startTime = System.nanoTime();
            kindaMehAlgorithm(theArray);
            endTime = System.nanoTime();
            break;
         case 4:
            for (int i = 0; i < 5; i++) {
               prettyGudAlgorithm(theArray, 0, theArray.length - 1);
            }
            startTime = System.nanoTime();
            prettyGudAlgorithm(theArray, 0, theArray.length - 1);
            endTime = System.nanoTime();
            break;
         case 5:
            for (int i = 0; i < 5; i++) {
               super1337proHaxxorAlgorithm(theArray);
            }
            startTime = System.nanoTime();
            super1337proHaxxorAlgorithm(theArray);
            endTime = System.nanoTime();
            break;
         default:
            break;
      }
      
      // It goes too fast on my system for milliseconds to be useful... :P
      System.out.println("Runtime for algorithm " + (select-1) + " is \n"
       + (endTime - startTime) + " nanoseconds.");

   }
   
   // Create a random array given a seed and an input size.
   public static int[] randArray (int seed, int inputSize) {
      
      Random seeded = new Random(seed);
      
      int[] seededArray = new int[inputSize];
      
      for (int i = 0; i < seededArray.length; i++) 
         seededArray[i] = seeded.nextInt(201) - 100;
      
      return seededArray;
   }
   
   // Freddy's algorithm. O(n^4). Yikes.
   public static int n00bAlgorithm(int[] array) {
      if (array.length < 1) return 0;
      
      int max = 0;
      for (int i = 0; i < array.length; i++) {
         for (int j = i; j < array.length; j++) {
            int thisSum = 0;
            for (int k = i; k < j + 1; k++) {
               thisSum += array[k];
               if (thisSum > max) {
                  max = thisSum;
               }
            }
         }
      }
      return max;
   }
   
   // Susie's algorithm. O(n^2). Not that great.
   public static int kindaMehAlgorithm(int[] array) {
      if (array.length < 1) return 0;
      
      int max = 0;
      for (int i = 0; i < array.length; i++) {
         int thisSum = 0;
         for (int j = i; j < array.length; j++) {
            thisSum += array[j];
            if (thisSum > max) {
               max = thisSum;
            }
         }
      }
      return max;
   }
   
   // Johnny's algorithm. O(n*log n). Hilariously complicated.
   public static int prettyGudAlgorithm(int[] array, int left, int right) {
      if (right < 0) return 0;
      
      if (left == right) {
         if (array[left] > 0) {
            return array[left];
         }
         return 0;
      }
      
      int center = (left + right)/2;
      
      int maxLeftSum = prettyGudAlgorithm(array, left, center);
      int maxRightSum = prettyGudAlgorithm(array, center + 1, right);
      
      int maxLeftBorder = 0;
      int leftBorder = 0;
      for (int i = center; i >= left; i--) {
         leftBorder = leftBorder + array[i];
         if (leftBorder > maxLeftBorder) {
            maxLeftBorder = leftBorder;
         }
      }
      
      int maxRightBorder = 0;
      int rightBorder = 0;
      for (int i = center + 1; i <= right; i++) {
         rightBorder = rightBorder + array[i];
         if (rightBorder > maxRightBorder) {
            maxRightBorder = rightBorder;
         }
      }
      
      return Math.max(Math.max(maxLeftSum, maxRightSum), 
                        maxLeftBorder + maxRightBorder);
   }
   
   // Sally's algorithm. O(n). Magical.
   public static int super1337proHaxxorAlgorithm(int[] array) {
      if (array.length < 1) return 0;
      
      int max = 0;
      int thisSum = 0;
      for (int i = 0; i < array.length; i++) {
         thisSum += array[i];
         if (thisSum > max) 
            max = thisSum;
         if (thisSum < 0)
            thisSum = 0;
      }
      return max;
   }
}
