package pickmatches;

import java.util.Scanner;

public class PickMatches {

   public static class MatchPiles {

      int numSticks1;
      int numSticks2;

      public void setNumSticks1(int num1) {
         numSticks1 = num1;
      }

      public void setNumSticks2(int num2) {
         numSticks2 = num2;
      }

      public int getNumSticks1() {
         return numSticks1;
      }

      public int getNumSticks2() {
         return numSticks2;
      }

      public MatchPiles(int num) {
         numSticks1 = num;
         numSticks2 = num;
      }

      public void displayPiles() {
         System.out.println("Pile 1: " + numSticks1 + " matches.");
         for (int i = 0; i < numSticks1; i++) {
            System.out.print("\\");
         }

         System.out.println("\n");

         System.out.println("Pile 2: " + numSticks2 + " matches.");
         for (int i = 0; i < numSticks2; i++) {
            System.out.print("\\");
         }

         System.out.println("\n");

      }

   }

   public static void main(String[] args) {

      int numMatches = 0;

      System.out.println("Hello! Enter the number of matches you want in both "
       + "piles.");

      while (numMatches < 5 || numMatches > 20) {
         System.out.println("Please enter an integer between 5 and 20.");
         numMatches = validate();
      }

      System.out.println(numMatches);

      MatchPiles myPiles = new MatchPiles(numMatches);

      playMatches(myPiles, numMatches);

      System.out.println("Adios, loser!");

   }

   public static int validate() {
      String input;
      int x = 0;

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
         }

      } while (notValid);

      return x;

   }

   public static void playMatches(MatchPiles m, int theMatches) {
      char playAgain = 'y';
      int userSticks = 0;
      int userPile = 0;

      Scanner in = new Scanner(System.in);
      String elString = "";

      while (playAgain == 'y') {
         System.out.print("\nLet's play! Think you can beat me, bro?\n");
         System.out.print("Remember, whoever picks up the last match wins.\n");
         System.out.print("You can go first, heh heh. :D\n");

         while (m.getNumSticks1() != 0 && m.getNumSticks2() != 0) {

            m.displayPiles();

            do {
               System.out.print("Enter how many sticks you want to grab, ");
               System.out.print("followed by the pile you want to get them from.\n");

               userSticks = validate();
               userPile = validate();

               if (userSticks < 1 || userPile < 1 || userPile > 2) {
                  System.out.println("Invalid input, man.");
               }

            } while (userSticks < 1 || userPile < 1 || userPile > 2);

            do {
               switch (userPile) {
                  case (1):
                     if (userSticks > m.getNumSticks1()) {
                        userSticks = m.getNumSticks1();
                     }
                     m.setNumSticks1(m.getNumSticks1() - userSticks);
                     break;
                  case (2):
                     if (userSticks > m.getNumSticks2()) {
                        userSticks = m.getNumSticks2();
                     }
                     m.setNumSticks2(m.getNumSticks2() - userSticks);
                  default:
                     break;
               }
            } while (userPile < 1 || userPile > 2);

            System.out.print("Removing " + userSticks + " stick(s) from pile " + userPile + "...\n\n");

            m.displayPiles();

            System.out.print("My turn!!!\n");

            if (userPile == 1) {
               m.setNumSticks2(m.getNumSticks2() - userSticks);
               userPile = 2;
            } else {
               m.setNumSticks1(m.getNumSticks1() - userSticks);
               userPile = 1;
            }
            
            System.out.print("Removing " + userSticks + " stick(s) from pile " + userPile + "...\n\n");

            if (m.getNumSticks1() == 0 && m.getNumSticks2() == 0) {

               m.displayPiles();
               System.out.print("HAHAHA!!! I WON!!!\n");
               System.out.print("Want to lose again (y/n) ??? ");

               while (!elString.equals("y") && !elString.equals("n")) {
                  elString = in.nextLine();
                  if (!elString.equals("y") && !elString.equals("n")) {
                     System.out.println("Pouye pls. Press y or n.");
                  }
               }
               
               playAgain = elString.charAt(0);
               elString = "";
               
               if (playAgain == 'y') {
                  m.setNumSticks1(theMatches);
                  m.setNumSticks2(theMatches);
               }
            }
         }
      }
   }
}
