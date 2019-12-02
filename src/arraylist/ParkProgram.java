//package arraylist;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Scanner;
//
///**
// *
// * @author Rub3z
// */
//public class ParkProgram {
//
//   public static void main(String[] args) throws ParseException {
//      ArrayList ProgrammableParks = new ArrayList();
//      Scanner userInput = new Scanner(System.in);
//      SimpleDateFormat creationDates = new SimpleDateFormat("yyyy-MM-dd");
//
//      int userSelection = 0;
//
//      while (userSelection != 4) {
//         if (userSelection < 4 && userSelection > 0) {
//            System.out.print("Hello ");
//            if (userSelection != 0) {
//               System.out.print("again ");
//            }
//            System.out.println("beautiful national parks enthusiast!!!");
//            System.out.println("Please use the number keys to select an"
//             + " option below:");
//         }
//
//         System.out.println("1. Add parks to database");
//         System.out.println("2. Show collection of parks");
//         System.out.println("3. Remove parks from database");
//         System.out.println("4. Quit");
//
//         userSelection = userInput.nextInt();
//         userInput.nextLine();
//         // We're basically "catching" the newline character typed here.
//         // Thank you for helping me with this problem, Neal :D
//
//         System.out.println("");
//
//         switch (userSelection) {
//            case 1:
//               addParks(ProgrammableParks, creationDates, userInput);
//               break;
//            case 2:
//               showCollection(ProgrammableParks);
//               break;
//            case 3:
//               removeParks(ProgrammableParks, userInput);
//               break;
//            case 4:
//               break;
//            default:
//               System.out.println("Sorry, it appears you've entered an invalid "
//                + "input. Give it another shot!");
//         }
//         System.out.println("");
//      }
//      System.out.println("Have a nice day, beautiful parks enthusiast!!!");
//      userInput.close();
//   }
//
//   public static void addParks( ArrayList addingParks, SimpleDateFormat dates,
//    Scanner input) throws ParseException{
//      System.out.println("Please enter a group of park information, "
//       + "one per line.");
//      System.out.println("Your input should be of the form: ");
//      System.out.println("Park Name, Area, Visitors, Creation Date, Location, "
//       + "Latitude, Longitude. \nThe creation date should be typed "
//       + "YYYY-MM-DD. For example: ");
//      System.out.println("Pinnacles, 107.7, 196635, 2013-01-10, California, "
//       + "36.48, -121.16.");
//      System.out.println("Type \"done\" on one line when you are done.");
//
//      String parkInfo = input.nextLine();
//
//      while (!parkInfo.equals("done")) {
//         String[] parsedInfo = parkInfo.split(", ");
//
//         NationalPark added = new NationalPark(
//          parsedInfo[0], //Park Name
//          Double.parseDouble(parsedInfo[1]), //Park Area
//          Integer.parseInt(parsedInfo[2]), //Visitors
//          dates.parse(parsedInfo[3]), //Creation Date
//          parsedInfo[4], //Location
//          Float.parseFloat(parsedInfo[5]), //Latitude
//          Float.parseFloat(parsedInfo[6])); //Longitude
//
//         addingParks.addLast(added);
//
//         parkInfo = input.nextLine();
//      }
//      System.out.println("");
//   }
//
//   public static void showCollection(ArrayList showParks) {
//      showParks.printAll();
//   }
//
//   public static void removeParks(ArrayList removingParks, Scanner input) {
//      for (int i = 0; i < removingParks.getCount(); i++) {
//         System.out.println((i + 1) + ". " + removingParks.get(i).toString());
//      }
//
//      System.out.println("Please enter the number of the corresponding park "
//       + "you'd like to remove...");
//      int toBeRemoved = input.nextInt();
//
//      removingParks.removeAt(toBeRemoved - 1);
//   }
//}
