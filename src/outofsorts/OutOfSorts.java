package outofsorts;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Rub3z
 */
public class OutOfSorts {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) 
    throws FileNotFoundException, IOException 
   {
      Scanner in = new Scanner(System.in);
      
      System.out.println("Enter the name of the file you'd like to open.");
      
      String fileName = in.next();
      
      if (fileName.contains("test"))
         tester();
      
      DataInputStream fromBin 
       = new DataInputStream(new FileInputStream(new File(fileName)));
      
      int n = fromBin.readInt();
      
      int[] original = new int[n];
      
      for (int i = 0; i < n; i++)
         original[i] = fromBin.readInt();
      
      int[] firstCopy = new int[n];
      int[] secondCopy = new int[n];
      
//    This was used to check if the files were written / read to as intended.
//      for (int i : original) {
//         System.out.println(i);
//      }
      
      long start, end;
      
      System.out.println("Performing insertion sort...");
      
      // Run the algorithm by the JVM five times with a loop.
      // I'll do this by creating a new copy of the original array
      // on each iteration.
      for (int i = 0; i < 5; i++) {
         System.arraycopy(original, 0, firstCopy, 0, n);
         sortInsertionly(firstCopy);
      }
      System.arraycopy(original, 0, firstCopy, 0, n);
      
      start = System.nanoTime();
      sortInsertionly(firstCopy);
      end = System.nanoTime();

      isOrdered(firstCopy);
      
      System.out.println("Time to complete: "
       + "" + (end - start) + " nanoseconds.");
    
      System.out.println("Performing quick sort...");
      
      // Once again, we'll do this five times.
      for (int i = 0; i < 5; i++) {
         System.arraycopy(original, 0, secondCopy, 0, n);
         sortQuickly(secondCopy, 0, 99999);
      }
      System.arraycopy(original, 0, secondCopy, 0, n);
      
      start = System.nanoTime();
      sortQuickly(secondCopy, 0, 99999);
      end = System.nanoTime();

      isOrdered(secondCopy);
      
      System.out.println("Time to complete: "
       + "" + (end - start) + " nanoseconds.");
      
   }
   
   public static void isOrdered (int array[]) {
      System.out.print("Is this array in order??? ... ");
      boolean ordered = true;
      for (int i = 0; i < array.length - 1; i++) {
         if (array[i] > array[i + 1]) {
            System.out.println("NO.");
            ordered = false;
            break;
         }
      }
      if (ordered)
         System.out.println("YES.");
   }
   
   public static void sortQuickly (int array[], int left, int right) {
      if ((right - left) <= 10)
         sortInsertionly(array, left, right);
      else {
         int pivotIndex = 
           medianOfThree(array[left],   //first
           array[(left + right)/2],    //middle
           array[right],               //last
           left,                       //leftIndex
           (left + right)/2,           //midIndex
           right);                     //rightIndex 
         // God, that was annoying.
         
         int j = partition(array, left, right, pivotIndex);
         
         sortQuickly(array, left, j - 1);
         sortQuickly(array, j + 1, right);
      }
   }
   
   public static int partition(int array[], 
      int left, int right, int pivotIndex) {
      int pivotValue = array[pivotIndex];
      // SWAP
      int temp = array[pivotIndex];
      array[pivotIndex] = array[right];
      array[right] = temp;
      
      int store = left;
      for (int i = left; i < right; i++) {
         if (array[i] < pivotValue) {
            // ANOTHER SWAP AAAAAAAGGHH
            temp = array[store];
            array[store] = array[i];
            array[i] = temp;
            store++;
         }
      }
      
      // YAY SWAP AGAIN (WHY DO I KEEP RE-WRITING THIS)
      temp = array[right];
      array[right] = array[store];
      array[store] = temp;
      
      return store;
   }
   
   public static void sortInsertionly (int array[]) {
      sortInsertionly(array, 0, array.length - 1);
   }
   
   public static void sortInsertionly (int array[], int left, int right) {
      for (int i = left + 1; i <= right; i++) {
         int j;
         int temp = array[i];
         for (j = i - 1; j >= left; j--) {
            if (array[j] > temp) 
               array[j + 1] = array[j];
            else 
               break;
         }
         array[j+1] = temp;
      }
   }
   
   public static int medianOfThree(int first, int middle, int last,
    int leftIndex, int midIndex, int rightIndex) {
      boolean a = first <= middle;
      boolean b = middle <= last;
      boolean c = last <= first;
      
      if ((a && b) || (!a && !b)) return midIndex;
      if ((a && !b && c)||(!a && b && !c)) return leftIndex;
      return rightIndex;
   }
   
   public static void tester() {
      System.out.println("Welcome to the test method! \n"
       + "Enter the length n of your array followed by its values:");
      
      Scanner testIn = new Scanner(System.in);
      
      int n = testIn.nextInt();
      
      int[] array = new int[n];
      
      for (int i = 0; i < n; i++) {
         array[i] = testIn.nextInt();
      }
      
      System.out.println("Select a method: \n"
       + "1. insertion \n"
       + "2. medianOfThree and partition \n"
       + "3. quicksort \n");
       
      int dummy1;
      int dummy2;
      
      switch (testIn.nextInt()) {
         case 1:
            System.out.println("Left and right index?");
            dummy1 = testIn.nextInt();
            dummy2 = testIn.nextInt();
            sortInsertionly(array, dummy1, dummy2);
            break;
         case 2:
            dummy1 = medianOfThree(array[0], array[(n - 1)/2], array[n - 1],
            0, (n - 1)/2, n- 1);
            System.out.println("Pivot index is: " + dummy1);
            dummy2 = partition(array, 0, n - 1, dummy1);
            System.out.println("Partition returned: " + dummy2);
            break;
         case 3:
            sortQuickly(array, 0, n - 1);
      }
      
      for (int i = 0; i < n; i++) {
         System.out.print(array[i] + " ");
      }
      System.out.println("");
      isOrdered(array);
      
      System.out.println("Alrighty then. Test again (y/n) ? \n");
      if (testIn.next().equals("y"))
         tester();
      
      System.out.println("\n WOOHOO RUNTIME EXCEPTION!!!!! \n");
   }
}
