package lexerluthor;

/**
 * This is the source file for our beautiful lexer.
 * There's only a few methods below the main method: peek(), printTokenInfo() 
 * and id(); the first of which should be clear and the other two are used for
 * displaying token info as in the project description and identifying tokens 
 * respectively. 
 *
 * There's a fair amount of comments throughout so you may see what's going on.
 * 
 * Hope you like it.
 * 
 * @author Eric Aguirre ID #010366978
 * @author Ruben Baerga ID #009824605
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LexerLuthor {

   /**
    * 
    * @param args the command line arguments
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {
      System.out.println("This program simulates a lexer.");
      System.out.println("Enter the name of the file or path to a file you'd "
       + "like to lex.");
      System.out.println("Example of file path to be entered: \n"
       + "C:/Users/YourName/Desktop/sample1.txt");
      System.out.println("You (should) be able to enter sample1.txt or "
       + "sample2.txt or sample3.txt \nto see output for the appropriate sample"
       + " .acod programs as seen in the \nproject sheet, as they (should) be "
       + "present here in the project package.");
     
      Scanner in = new Scanner(System.in);
      // User will input what file they want to see get lexed up.
      File lexIt = new File(in.nextLine());
      Scanner lex = new Scanner(lexIt);
      
      String line = ""; // Represents each line of the file.
      String build = ""; // String for each output.
      int lineNumber = 1; // Initialize the line number.
      
      while (lex.hasNextLine()) // Checks each line.
      {
         line = lex.nextLine();

         ArrayList<String> characters = new ArrayList<>();
         for (int k = 0; k < line.length(); k++) {
            characters.add(line.substring(k, k + 1));
            // Add a char to the array.
         }

         for (int i = 0; i < characters.size(); i++) {
            int ID; // The token ID.
            
            // This checks if we are at the end of the current line.
            if ( i == characters.size() - 1) {
               build += characters.get(i);
               ID = id(build);
               printTokenInfo(ID, lineNumber, build);
               build = "";
               
            // Now we'll check if we've encountered a forward slash token...
            } else if (characters.get(i).equals("/")) {
               build += characters.get(i);
               // And ignore the rest of this line if the following character
               // is a forward slash.
               if (peek(characters, i).equals("/")) {
                  i = characters.size();
                  build = "";
               } else {
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";
               }
               
            } else if (characters.get(i).equals(" ")) {
               // IGNORE SPACES
            } else {
               
               // Check if we've encountered a quote that would begin a String
               if (characters.get(i).equals("\"")) {
                  // Loop until we find another quote to end the String
                  for(int t = i + 1; t < characters.size(); t++) {
                     build += characters.get(t);
                     if (peek(characters, t).equals("\"")) {
                        i = t + 1; // Set i to index after t
                        printTokenInfo(5, lineNumber, build);
                        build = "";
                        break;
                     }
                  }
                  
               // Check if we've encountered letters, which may be part of
               // an identifier or a keyword.
               } else if (characters.get(i).matches("[a-z]")) {
                  build += characters.get(i);
                  // Then check if the next isn't a letter, terminating
                  // identifier or keyword.
                  if (peek(characters, i).matches("[0-9]")
                   || peek(characters, i).equals(",")
                   || peek(characters, i).equals(";")
                   || peek(characters, i).equals(" ")
                   || peek(characters, i).equals("*")
                   || peek(characters, i).equals("^")
                   || peek(characters, i).equals(":")
                   || peek(characters, i).equals(".")
                   || peek(characters, i).equals("=")
                   || peek(characters, i).equals("-")
                   || peek(characters, i).equals("+")
                   || peek(characters, i).equals("\\")
                   || peek(characters, i).equals("<")
                   || peek(characters, i).equals(">")
                   || peek(characters, i).equals("[")
                   || peek(characters, i).equals("]")
                   || peek(characters, i).equals("{")
                   || peek(characters, i).equals("}")
                   || peek(characters, i).equals("(")
                   || peek(characters, i).equals(")")
                   || peek(characters, i).equals("\"")) {
                     // NOTE: This was done because using String literals to 
                     // represent regexes can be uncomfortable.
                     // This is also more clear on what exactly we're peeking
                     // for in the next character in our list.

                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }

               // Check for capital letters.
               } else if (characters.get(i).matches("[A-Z]")) {
                  build += characters.get(i);
                  
               // Start special character checks.
               } else if (characters.get(i).equals("=")) {
                  build += characters.get(i);
                  // "==" operation
                  if (!peek(characters, i).equals("=")) {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  } else {
                     i++;
                     build += characters.get(i);
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
                  
               } else if (characters.get(i).equals("-")) {
                  build += characters.get(i);
                  // "->" arrow operation
                  if (!peek(characters, i).equals(">")) {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  } else {
                     i++;
                     build += characters.get(i);
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
               
               // Punctuations 
               } else if (characters.get(i).equals(":")) {
                  build += characters.get(i);
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";
               } else if (characters.get(i).equals("+")) {
                  build += characters.get(i);
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";
               } else if (characters.get(i).equals("^")) {
                  build += characters.get(i);
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";

               } else if (characters.get(i).equals(".")) {
                  build += characters.get(i);
                  // Check if this "." is part of a float token
                  if (!peek(characters, i).matches("[0-9]")) {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
               
               // "*" operator
               } else if (characters.get(i).equals("*")) {
                  build += characters.get(i);
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";
                  
               // Paired delimiters
               } else if (characters.get(i).equals("{")
                || characters.get(i).equals("}")
                || characters.get(i).equals("[")
                || characters.get(i).equals("]")
                || characters.get(i).equals("(")
                || characters.get(i).equals(")")
                || characters.get(i).equals(",")
                || characters.get(i).equals(";")) {

                  // Again, using String literals to represent regexes can
                  // be a real pain.
                  build += characters.get(i);
                  ID = id(build);
                  printTokenInfo(ID, lineNumber, build);
                  build = "";
                  
               // The "!=" operator
               } else if (characters.get(i).equals("!")) {
                  build += characters.get(i);
                  if (peek(characters, i).equals("=")) {
                     i++;
                     build += characters.get(i);
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
                  build = "";
                  
               // Check for "<", "<=", or "<<" operators
               } else if (characters.get(i).equals("<")) {
                  build += characters.get(i);
                  if (peek(characters, i).equals("<")
                   || peek(characters, i).equals("=")) {
                     i++;
                     build += characters.get(i);
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  } else {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
                  
               // Check for ">", ">=", ">>" operators
               } else if (characters.get(i).equals(">")) {
                  build += characters.get(i);
                  if (peek(characters, i).equals(">")
                   || peek(characters, i).equals("=")) {
                     i++;
                     build += characters.get(i);
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  } else {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
                  
               // Check for numbers which could be part of an
               // int or float token.
               } else if (characters.get(i).matches("[0-9]")) {
                  build += characters.get(i);
                  if (!peek(characters, i).matches("[0-9]")
                   && !peek(characters, i).equals(".")) {
                     ID = id(build);
                     printTokenInfo(ID, lineNumber, build);
                     build = "";
                  }
               }
            }
         }
         lineNumber++; // increment line number
      }
      printTokenInfo(0, lineNumber - 1, "");
   }

   /**
    * Written by Ruben
    * Given a token ID number, a line number of the given input, and a String
    * which (hopefully) corresponds to that token ID; print out a formatted
    * line displaying this info. Also shows the value of the token if it is
    * an int or a float (ID number 3 or 4 respectively).
    * @param ID the token ID number
    * @param lineNumber line number of the input that the token is on
    * @param build a String representing the token
    */
   public static void printTokenInfo(int ID, int lineNumber, String build) {
      System.out.printf("(Tok:%3d", ID );
      System.out.printf(" line=%3d", lineNumber);
      System.out.print(" str= " + "\"" + build + "\"");
      if (ID == 3) System.out.print(" int= " + build);
      if (ID == 4) System.out.print(" float= " + build);
      System.out.println(")");
      
   }

   /**
    * Written by Eric
    * Modified by Ruben to accept a String ArrayList
    * Takes an ArrayList of Strings (presumably each having a length of 1) and
    * returns the String in the index following the given index.
    * @param characters the ArrayList of Strings
    * @param i the given index to peek after
    * @return the String in the index i + 1
    */
   public static String peek(ArrayList<String> characters, int i) {
      String peekTo = "k";
      if (i < characters.size()) {
         peekTo = characters.get(i + 1);
      }
      return peekTo;
   }

   /**
    * Written by Eric
    * 
    * Takes a String and does a ludicrous amount of checks on it to see what
    * Toen ID it corresponds to; and returns that ID as an int.
    * @param build The String to be checked
    * @return the Token ID number of that String
    */
   public static int id(String build) {  
////////// INT AND FLOAT /////////////////
      if (Character.isDigit(build.charAt(0))) {
         for (int c = 0; c < build.length(); c++) 
            if (build.charAt(c) == '.') 
               return 4;
         return 3;
         
////////// UNPAIRED DELIMITERS ////////////
      } else if (build.equals(",")) 
         return 6;
       else if (build.equals(";")) 
         return 7;
      
////////// KEYWORDS //////////////////////
       else if (build.equals("prog")) 
         return 10;
       else if (build.equals("main"))
         return 11;
       else if (build.equals("fcn")) 
         return 12;
       else if (build.equals("class")) 
         return 13;
       else if (build.equals("float")) 
         return 15;
       else if (build.equals("int")) 
         return 16;
       else if (build.equals("string")) 
         return 17;
       else if (build.equals("if")) 
         return 18;
       else if (build.equals("elseif")) 
         return 19;
       else if (build.equals("else")) 
         return 20;
       else if (build.equals("while")) 
         return 21;
       else if (build.equals("input")) 
         return 22;
       else if (build.equals("print")) 
         return 23;
       else if (build.equals("new")) 
         return 24;
       else if (build.equals("return")) 
         return 25;
        
////////// PAIRED DELIMITERS /////////////
       else if (build.equals("<")) 
         return 31;
       else if (build.equals(">")) 
         return 32;
       else if (build.equals("{")) 
         return 33;
       else if (build.equals("}")) 
         return 34;
       else if (build.equals("[")) 
         return 35;
       else if (build.equals("]")) 
         return 36;
       else if (build.equals("(")) 
         return 37;
       else if (build.equals(")")) 
         return 38;
        
////////// OTHER PUNCTUATION ////////////
       else if (build.equals("*")) 
         return 41;
       else if (build.equals("^")) 
         return 42;
       else if (build.equals(":")) 
         return 43;
       else if (build.equals(".")) 
         return 44;
       else if (build.equals("=")) 
         return 45;
       else if (build.equals("-")) 
         return 46;
       else if (build.equals("+")) 
         return 47;
       else if (build.equals("/")) 
         return 48;

////////// MULTI-CHAR OPERATORS //////////
       else if (build.equals("->")) 
         return 51;
       else if (build.equals("==")) 
         return 52;
       else if (build.equals("!=")) 
         return 53;
       else if (build.equals("<=")) 
         return 54;
       else if (build.equals(">=")) 
         return 55;
       else if (build.equals("<<")) 
         return 56;
       else if (build.equals(">>")) 
         return 57;
      return 2;
   }
}
