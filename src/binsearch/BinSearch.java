package binsearch;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class BinSearch {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      
      int arraySize;
      
      Random rando = new Random();
      
      System.out.println("Enter an integer for the size of the random "
       + "integer array:");
      
      arraySize = validate();
      
      int[] randArray = new int[arraySize];
      
      for (int i = 0; i < arraySize; i++) 
         randArray[i] = rando.nextInt(201) - 100;
      
      int temp;
      
      for (int i = 0; i < randArray.length; i++) {
         for (int j = 0; j < randArray.length - i - 1; j++) {
            if (randArray[j] > randArray[j + 1]) {
               temp = randArray[j];
               randArray[j] = randArray[j + 1];
               randArray[j + 1] = temp;
            }
         }
      }
      
      for (int i= 0 ; i< arraySize; i++)
         System.out.println( i + " " + randArray[i]);
      
      int key = randArray[rando.nextInt(randArray.length)];
      
      System.out.println("\nRandom key: " + key);
      
      int bindex;
      int lindex;
      long timerStart;
      long runtime;
      
      timerStart = System.nanoTime();
      
      lindex = linSearch(randArray, key);
      
      runtime = System.nanoTime() - timerStart;
      
      System.out.println("Linear search index: " + lindex);
      System.out.println("Linear search runtime: " + runtime + " nanoseconds.");
      
      
      timerStart = System.nanoTime();
      
      bindex = binSearch(randArray, key);
      
      runtime = System.nanoTime() - timerStart;
      
      System.out.println("Binary search index: " + bindex);
      System.out.println("Binary search runtime: " + runtime + " nanoseconds.");
      
      
   }
   
   private static int binSearch(int[] array, int key, int startAt, int endAt) {
      if (endAt < startAt) return -1;
      
      int middleIndex = (startAt + endAt) / 2;
      
      int num = array[middleIndex];
      
      if (num == key) return middleIndex;
      
      if (num < key) return binSearch(array, key, middleIndex + 1, endAt);
      
      else return binSearch(array, key, startAt, middleIndex - 1);
      
   }
   
   public static int binSearch(int[] array, int key) {
      return binSearch(array, key, 0, array.length - 1);
   }
   
   private static int linSearch(int[] array, int key, int startAt) {
      if (startAt == array.length) return -1;
      
      if (array[startAt] == key) return startAt;
      
      return linSearch(array, key, startAt + 1);
   }
   
   public static int linSearch(int[] array, int key) {
      return linSearch(array, key, 0);
   }
   
   public static int validate() {
      String input;
      int x = 1337;

      boolean notValid = false;

      Scanner in = new Scanner(System.in);

      do {
         input = in.nextLine();

         try {
            x = Integer.parseInt(input);
         } catch (Exception NumberFormatException) {
            System.out.println("NO POUYE. NO. ENTER AN INTEGER AND ONLY "
             + "AN INTEGER.");
            notValid = true;
         } finally {
               if (x <= 0) {
                  System.out.println("Invalid array size. "
                   + "Enter a bigger number.");
                  notValid = true;
               }
               else if (x == 1337)
                  notValid = true;
               else
                  notValid = false;
         }

      } while (notValid);

      return x;

   }
   
}
