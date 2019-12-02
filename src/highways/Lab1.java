package highways;

import java.util.Scanner;

/**
 * Dude, I understand that people srsly read this crap at the top and it's nice
 * to have so ppl know what it actually does; but in this class MC Neal Terrell
 * doesn't actually care about any of dat mess; yo. Topkek
 *
 * @author Rub3z
 */
public class Lab1 {
   static final int MAX_INTERSTATE_ID_LENGTH = 15;
   //The number of chars in an ID such as "Interstate 105" is actually 14.
   //This is cool since we're assuming the user's input will be correct.
   //No interstateID.length method needed! HA! I WIN!!!

   public static void main(String[] args) {
      String interstateID = "";

      while (!interstateID.contains("exit")) {
         System.out.print("Please enter a US Interstate Highway name, or");
         System.out.println(" type \"exit\" to quit.");

         Scanner in = new Scanner(System.in);
         interstateID = in.nextLine();
         
         //I seriously, seriously wish there was a better way to write this.
         //There probably is, but I couldn't wrap my head around it.
         //Checking the loop condition in the middle of the loop!?
         //UUUUUUUUGGGHHH
         if (!interstateID.contains("exit")) {
            int routeNumber = parseInterstateIdentifier(interstateID);
            System.out.print("Interstate " + routeNumber + " is ");
            System.out.println(highwayCharacteristics(routeNumber));
         }
      }
   }

   public static int parseInterstateIdentifier(String interstateID) {
      int routeNumber = 0;
      //This loop makes me all hot and sweaty.
      for (int i = 0; i < MAX_INTERSTATE_ID_LENGTH; i++) {
         if (Character.isDigit(interstateID.charAt(i))) {
            routeNumber = Integer.parseInt(interstateID.substring(i));
            i = MAX_INTERSTATE_ID_LENGTH + 1;
            //SEE!? Amazeballs, I know.
         }
      }
      return routeNumber;
   }

   public static String highwayCharacteristics(int routeNumber) {
      String highwayCharacteristics = "";

      if (routeNumber % 1000 < 100) {
         if (routeNumber % 5 == 0) {
            highwayCharacteristics = "a long-distance arterial highway ";
         }
         if (routeNumber % 2 == 0) {
            highwayCharacteristics += "oriented east-west.";
         }
         else {
            highwayCharacteristics += "oriented north-south.";
         }
         return highwayCharacteristics + "\n";
         //Gotta remember the \n newline character. :)
      }
      else if ((routeNumber / 100) % 2 == 0) {
         highwayCharacteristics = "a circumferential highway of Interstate ";
      }
      else {
         highwayCharacteristics = "a spur highway of Interstate ";
      }
      highwayCharacteristics += routeNumber % 100 + ".\n";
      //Adding a whole bunch of these Strings together as demonstrated in class
      return highwayCharacteristics;
   }
}