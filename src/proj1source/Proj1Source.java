package proj1source;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ScottRoberts, collaborating with Ruben Baerga
 */
public class Proj1Source {
   //Information for db connection
   public static Scanner scan = new Scanner(System.in);
   static String user;
   static String pass;
   static String dbName;

   static int option = -1;
   static String sql = "";
   static PreparedStatement pstmt = null;
   static ResultSet rs = null;

   static Connection conn = null; //initialize the connection
   static Statement stmt = null;  //initialize the statement that we're using


   //This is the specification for the printout that I'm doing:
   //each % denotes the start of a new field.
   //The - denotes left justification.
   //The number indicates how wide to make the field.
   //The "s" denotes that it's a string.  All of our output in this test are
   //strings, but that won't always be the case.
   static final String displayFormat="%-5s%-15s%-15s%-15s\n";

   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
   static String DB_URL = "jdbc:derby://localhost:1527/";
   //            + "testdb;user=";

   /**
* Takes the input string and outputs "N/A" if the string is empty or null.
* @param input The string to be mapped.
* @return  Either the input string or "N/A" as appropriate.
*/
   public static String dispNull (String input) {
       //because of short circuiting, if it's null, it never checks the length.
       if (input == null || input.length() == 0)
           return "N/A";
       else
           return input;
   }

   public static int dispNull(int input){
           return input;
   }


   public static void displayMainMenu()
   {
       System.out.println("\nWhat would you like to do?");
       System.out.println("1. List all writing groups");
       System.out.println("2. List all the data for a group specified by the user");
       System.out.println("3. List all publishers");
       System.out.println("4. List all the data for a pubisher specified by the user");
       System.out.println("5. List all book titles");
       System.out.println("6. List all the data for a book specified by the user");
       System.out.println("7. Insert a new book");
       System.out.println("8. Insert a new publisher and update all books "
               + "published by one publisher to be published by the new publisher.");
       System.out.println("9. Insert a new writing group");
       System.out.println("10. Remove a book specified by the user");
       System.out.println("0. Exit.");
   }

   public static void pickOption()
   {
      do
      {
         option = -1;
         try
         {
            System.out.println("Please enter a number between 0 and 10.");
            option = scan.nextInt();
         }
         catch(InputMismatchException e)
         {
            Proj1Source.scan.nextLine();
         }
      } while(option < 0 || option > 10);
   }

   public static int pickInt()
   {
      int tempInt;
      do
      {
         tempInt = -1;
         try
         {
            tempInt = scan.nextInt();
         }
         catch(InputMismatchException e)
         {
            Proj1Source.scan.nextLine();
            System.out.println("Please enter a number.");
         }
      } while(tempInt < 0);
      return tempInt;
   }

   public static void executeOption()
   {
      try
      {
         //All SQL connection, prepared statements and result sets code
         switch(option)
         {
            case 1:
               listGroups();
               break;
            case 2:
               groupData();
               break;
            case 3:
               listPublishers();
               break;
            case 4:
               publisherData();
               break;
            case 5:
               listBooks();
               break;
            case 6:
               bookData();
                break;
            case 7:
               insertBook();
               break;
            case 8:
               insertPublisher();
               break;
            case 9:
               insertGroup();
               break;
            case 10:
               removeBook();
               break;
            default:
               break;
         }  
      }
      catch(SQLException e)
      {
         e.printStackTrace();
      }
   }

   public static void listGroups() throws SQLException {
      sql = "SELECT * FROM writingGroups";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      int i = 1;

         while(rs.next())
         {
            String groupName = rs.getString("groupName");
            String headWriter = rs.getString("headWriter");
            int yearFormed = rs.getInt("yearFormed");
            String subject = rs.getString("subject");
            System.out.println("Group name: " + groupName
             + "\nHead writer: " + headWriter 
             + "\nFounded: " + yearFormed 
             + "\nSubject: " + subject + "\n");
      }
   }

   public static void groupData() throws SQLException {
      System.out.println("What is the name of the writing group?");
      sql = "SELECT * FROM writingGroups WHERE groupName = ?";
      pstmt = conn.prepareStatement(sql);
      scan.nextLine();
      pstmt.setString(1, scan.nextLine());
      rs = pstmt.executeQuery();
      if (rs.next())
      {
         
         String headWriter = rs.getString("headWriter");
         int yearFormed = rs.getInt("yearFormed");
         String subject = rs.getString("subject");
         System.out.println("Head writer: " + headWriter 
          + "\nFounded: " + yearFormed 
          + "\nSubject: " + subject + "\n");
      }
      else
      {
         System.out.println("No Writing Group Found.");
      }           
   }

   public static void listPublishers() throws SQLException {
      sql = "SELECT * FROM publishers";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      int i = 1;
      
         while(rs.next())
         {
            i++;
            String publisherName = rs.getString("publisherName");
            String publisherAddress = rs.getString("publisherAddress");
            String publisherPhone = rs.getString("publisherPhone");
            String publisherEmail = rs.getString("publisherEmail");
            System.out.print(publisherName 
             + "\n " + publisherAddress 
             + "\n " + publisherPhone 
             + "\n " + publisherEmail + "\n\n");
      }
   }

   public static void publisherData() throws SQLException {
      sql = "SELECT * FROM publishers WHERE publisherName = ?";
      System.out.println("What would you like your publisherName to be?");
      pstmt = conn.prepareStatement(sql);
      scan.nextLine();
      pstmt.setString(1, scan.nextLine());
      rs = pstmt.executeQuery();
      if (rs.next())
      {
         
         String publisherAddress = rs.getString("publisherAddress");
         String publisherPhone = rs.getString("publisherPhone");
         String publisherEmail = rs.getString("publisherEmail");
         System.out.print("Address: " + publisherAddress 
          + "\nPhone number: " + publisherPhone 
          + "\nEmail: " + publisherEmail + "\n");
      }
      else
      {
         System.out.println("No publisher found.");
      }
   }

   public static void listBooks() throws SQLException {
     sql = "SELECT bookTitle FROM books";
     pstmt = conn.prepareStatement(sql);
     rs = pstmt.executeQuery();
     int i = 1;
     if (rs.next()) 
     {
        String bookTitle = rs.getString("bookTitle");
        System.out.println(i + ". " + bookTitle);
        while(rs.next())
        {
            i++;
            bookTitle = rs.getString("bookTitle");
            System.out.println(i + ". " + bookTitle);
        }
     }
     else
     {
         System.out.println("No books found.");
     }
   }

   public static void bookData() throws SQLException {
      sql = "SELECT * FROM books WHERE bookTitle = ? and groupName = ?";
      System.out.println("What is the book title?");
      pstmt = conn.prepareStatement(sql);
      scan.nextLine();
      pstmt.setString(1, scan.nextLine());
      System.out.println("What is the writing group?");
      pstmt.setString(2, scan.nextLine());
      rs = pstmt.executeQuery();
      if (rs.next())
      {
          
         String publisherName = rs.getString("publisherName");
         int yearPublished = rs.getInt("yearPublished");
         int numberPages = rs.getInt("numberPages");
         System.out.print(publisherName 
          + ", " + yearPublished + ", " + numberPages + " pages. \n");
      }
      else
      {
          System.out.println("No book found.");
      }
   }

   public static void insertBook() throws SQLException {
      sql = "INSERT INTO books (groupName, bookTitle, publisherName, yearPublished, numberPages)"
       + "VALUES (?,?,?,?,?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.clearParameters();
      System.out.print("Group Name: "); 
      
      String sql2 = "SELECT * FROM writingGroups WHERE groupName = ?";
      PreparedStatement pstmt2 = conn.prepareStatement(sql2);
      scan.nextLine();
      String groupName = scan.nextLine();
      pstmt2.setString(1, groupName);
      rs = pstmt2.executeQuery();
      if (rs.next())
      {
         pstmt.setString(1, groupName);
         System.out.print("Book Title: ");
         pstmt.setString(2, scan.nextLine());
         sql2 = "SELECT * FROM publishers WHERE publisherName = ?";
         System.out.print("Publisher Name: ");
         pstmt2 = conn.prepareStatement(sql2);
         String publisherName = scan.nextLine();
         pstmt2.setString(1, publisherName);
         rs = pstmt2.executeQuery();
         if (rs.next())
         {
            pstmt.setString(3, publisherName);
            System.out.print("Year Published: ");
            pstmt.setInt(4, pickInt());
            System.out.print("Number of Pages: ");
            pstmt.setInt(5, pickInt());
            pstmt.executeUpdate();
         }
         else
         {
            System.out.println("No publisher found.");
         }
      }
      else
      {
         System.out.println("No Writing Group Found.");
      }
      
//      //Check if the update works
//      sql = "SELECT * FROM books";
//      pstmt = conn.prepareStatement(sql);
//      rs = pstmt.executeQuery();
//      while (rs.next())
//      {
//         String groupName = rs.getString("groupName");
//         String bookTitle = rs.getString("bookTitle");
//         String publisherName = rs.getString("publisherName");
//         int yearPublished = rs.getInt("yearPublished");
//         int numberPages = rs.getInt("numberPages");
//         System.out.print(groupName + " " + bookTitle + " " + publisherName
//          + " " + yearPublished + " " + numberPages);
//      }
      
   }

   public static void insertPublisher() throws SQLException {
      System.out.println("Who is the old publisher?");
      scan.nextLine();
      String oldPubName = scan.nextLine();
      sql = "SELECT publisherName FROM publishers WHERE publisherName = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.clearParameters();
      pstmt.setString(1, oldPubName);
      rs = pstmt.executeQuery();
      //Old publisher Exists
      if (rs.next())
      {
         System.out.println("Old publisher exists.");
         System.out.println("Creating parameters for new publisher...");
         sql = "INSERT INTO publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)"
             + "VALUES (?,?,?,?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.clearParameters();
         System.out.print("Publisher Name: "); 
         //scan.nextLine();
         String pubName = scan.nextLine();
         pstmt.setString(1, pubName);
         System.out.print("Publisher Address: ");
         pstmt.setString(2, scan.nextLine());
         System.out.print("Publisher Phone: ");
         pstmt.setString(3, scan.nextLine());
         System.out.print("Publisher Email: ");
         pstmt.setString(4, scan.nextLine());
         pstmt.executeUpdate();

         //Set all old books to new publisher
         System.out.println("Changing publisher names...");
         sql = "UPDATE books "
             + "SET publisherName = ? "
             + "WHERE publisherName = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.clearParameters();
         pstmt.setString(1, pubName);
         pstmt.setString(2, oldPubName);
         pstmt.executeUpdate();
         System.out.println("Change done...");
      }
      else
      {
         System.out.println("The old publisher does not exist");
      }
      //All books must now have this publisher as a publisher
   }

   public static void insertGroup() throws SQLException {
      sql = "INSERT INTO writingGroups (groupName, headWriter, yearFormed, subject)"
       + "VALUES (?,?,?,?)"; 
      pstmt = conn.prepareStatement(sql);
      pstmt.clearParameters();
      System.out.print("Group Name: "); scan.nextLine();
      pstmt.setString(1, scan.nextLine());
      System.out.print("Head Writer: ");
      pstmt.setString(2, scan.nextLine());
      System.out.print("Year Formed: ");
      pstmt.setInt(3, pickInt());
      scan.nextLine();
      System.out.print("Subject: ");
      pstmt.setString(4, scan.nextLine());
      pstmt.executeUpdate();
//      //Check if the update works
//      sql = "SELECT * FROM writingGroups";
//      pstmt = conn.prepareStatement(sql);
//      rs = pstmt.executeQuery();
//      while (rs.next())
//      {
//         String groupName = rs.getString("groupName");
//         String headWriter = rs.getString("headWriter");
//         int yearFormed = rs.getInt("yearFormed");
//         String subject = rs.getString("subject");
//         System.out.println(groupName + "."
//          + "\nHeadwriter: " + headWriter 
//          + "\nFormed: " + yearFormed 
//          + "\nSubject:" + subject + "\n");
//      }
   }

   public static void removeBook() throws SQLException {
      sql = "SELECT bookTitle FROM books where bookTitle = ? and groupName = ?";
      //Run a query first
      scan.nextLine();
      System.out.println("What bookTitle do you want to remove?");
      String book = scan.nextLine();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, book);
      
      System.out.println("What writing group is " + book + " from?");
      String groupName = scan.nextLine();
      pstmt.setString(2, groupName);
      
      rs = pstmt.executeQuery();
      if (rs.next())
      {
         sql = "DELETE FROM books WHERE bookTitle = ? and groupName = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.clearParameters();
         pstmt.setString(1, book);
         pstmt.setString(2, groupName);
         pstmt.execute();
         System.out.println("Delete Successful."); 
      }
      else
      {
         System.out.println("There is no such book.");
      }

   }
   public static void main (String[] args)
   {
      try 
      {
         dbName = "project";
         user = "chuck";
         pass = "norris";

         //Constructing the database URL connection string
         DB_URL = DB_URL + dbName + ";user="+ user + ";password=" + pass;

         //STEP 2: Register JDBC driver
         Class.forName("org.apache.derby.jdbc.ClientDriver");

         //STEP 3: Open a connection
         System.out.println("Connecting to database...");
         conn = DriverManager.getConnection(DB_URL);

         // Step 4: Get menu choice
         while (option != 0) {
            displayMainMenu();
            pickOption();
            //STEP 5: Execute
            executeOption();
         }
         
         //STEP 6: Clean-up environment
         
         if (null != rs && null != pstmt && null != conn) {
            rs.close();
            pstmt.close();
            conn.close();
         }
         
      } catch (SQLException se) {
         //Handle errors for JDBC
         se.printStackTrace();
      } catch (Exception e) {
         //Handle errors for Class.forName
         e.printStackTrace();
      } finally {
         //finally block used to close resources
         try {
            if (stmt != null) {
               stmt.close();
            }
         } catch (SQLException se2) {}// nothing we can do
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException se) {
            se.printStackTrace();
         }//end finally try
      }//end try
      System.out.println("Sayonara.");
   }
}