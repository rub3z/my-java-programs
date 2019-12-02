package linkedlist;

import java.util.Scanner;

public class ListGame {
   
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      
      //Input checkers
      boolean hasMoreThanNumbers = false;
      boolean correctInput = false;
      
      String[] theNums = new String[10];
      
      theNums[1] = "";
      
      //A loop for the first player's input.
      while (!correctInput) {
         System.out.println("Enter two integers.");
         System.out.println("You do not have to hit enter between each number. "
          + "Just spaces is fine.");
         
         theNums = input.nextLine().split(" ");
         
         
         
         //Ignores whitespaces
         for (String i : theNums) {
            for (char j : i.toCharArray()) {
               if (!Character.isDigit(j) && j != '-') hasMoreThanNumbers = true;
            }
         }
         
         if (hasMoreThanNumbers) {
            System.out.println("Don't be a smart-aleck. Use only letters,"
             + "dude.");
            hasMoreThanNumbers = false;
         }
         else {
            correctInput = true;
         } 
      }
      
      int[] nums = new int[2];
      
      for (int k = 0; k < 2; k++)
         nums[k] = Integer.parseInt(theNums[k]);
      
      System.out.println(gcd(nums[0], nums[1]));
      System.out.println(nums[0]*nums[1] / gcd(nums[0], nums[1]));
      
   }
   
   public static int gcd(int a, int b) {
      if (a == 0 || b == 0)
         return Math.abs(Math.max(a, b));
      //if (a < 0 || b < 0)
      
      int r = (a > b) ? a % b : b % a;
	
      return (r != 0) ? gcd(r, Math.min(a,b)) : Math.min(a,b);
   }
   
   
}
