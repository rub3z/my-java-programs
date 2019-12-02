package parseit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Ruben Baerga ID #010366978 in collaboration with
 * @author Eric Aguirre ID #
 */
public class ParseIt {
   ArrayList<String> grammar;
   
   ArrayList<Rule> rules;
   ArrayList<Symbol> terminals;
   ArrayList<Symbol> nonTerminals;
   Rule[][] parseTable;

   Stack<Symbol> theStack;
   //String input;
   
   final Symbol STAHP = new Symbol("STAHP");
   
   public ParseIt() {
      grammar = new ArrayList<>();
      rules = new ArrayList<>();
      terminals = new ArrayList<>();
      nonTerminals = new ArrayList<>();
      
   }
   
   public class Node {
      ArrayList<Symbol> kids;
   }
   
   // The Symbol class has Strings for its name and token,
   // as well as a list of Rules.
   public class Symbol {
      String token;     // Only necessary for terminal Symbol
      String name;      
      ArrayList<Rule> kidRules; // Only for non-terminal Symbol
      
      // Non-terminal symbol constructor takes in a line of grammar and an
      // otherwise useless int to distinguish it from the terminal constructor.
      public Symbol(String grammarLine, int dummy) {
         token = "";
         kidRules = new ArrayList<>();
         name = grammarLine.split(" ")[0];
         
         String[] theRules = grammarLine.split(" \\= | \\| ");
         
//         This snippet was used for testing the constructor.
//         System.out.println("Printing the line of grammar passed to Symbol"
//          + "constructor; should be:\n" + grammarLine 
//          + "\nResult after split:");
//         for (String i : theRules) System.out.print(i + " ");
//         System.out.println("");
         
         // The non-terminal Symbol constructor also calls the Rule
         // constructor, adding its new Rules to the list at the top.
         for(int i = 1; i < theRules.length; i++) {
            // Have to make this Rule to ensure it's the same Rule stuffed
            // into the lists. This way; a call to find the index of
            // a kidRule in the rules list won't return -1.
            Rule newRule = new Rule(theRules[i]);
            kidRules.add(newRule);
            rules.add(newRule);
         }
      }
      
      // Terminal Symbol constructor needs a name.
      public Symbol(String tName) {
         name = tName;
         switch (tName) {
            case "kwdprog"    :  token = "prog";   break;
            case "brace1"     :  token = "\\{";    break;
            case "brace2"     :  token = "\\}";    break;
            case "kwdvars"    :  token = "vars" ; break;      
//            case "eps"        :  token =  "blah" ; break;      
            case "parens1"    :  token =  "\\("; break;      
            case "parens2"    :  token =  "\\)"; break;      
            case "semi"       :  token =  ";" ; break;      
            case "\'int\'"    :  token =  "int" ; break;      
            case "\'float\'"  :  token =  "float" ; break;      
            case "\'string\'" :  token =  "string"; break;      
            case "id"     :  token =  "(_|[a-zA-Z])(_|[a-zA-Z]|[0-9])*"; break;      
            case "equal"      :  token =  "\\="; break;      
            case "kprint"     :  token =  "print"; break;      
            case "kwdwhile"   :  token =  "while"; break;      
            case "comma"      :  token =  "\\,"; break;      
            case "int"        :  token = "[0-9]+"; break;
            case "float"      :  token = "[0-9]+\\.[0-9]+";    break;
            case "string"     :  token = "\".*\""; break;
            case "opeq"       :  token = "\\=\\="; break;      
            case "opne"       :  token = "\\!\\="; break;      
            case "ople"       :  token = "\\<\\="; break;      
            case "opge"       :  token = "\\>\\="; break;      
            case "angle1"     :  token = "\\<"; break;      
            case "angle2"     :  token = "\\>"; break;      
            case "plus"       :  token = "\\+"; break;      
            case "minus"      :  token = "\\-"; break;    
            case "aster"      :  token = "\\*";    break;
            case "slash"      :  token = "/";    break;
            case "caret"      :  token = "\\^";    break;
            case "STAHP"      :  token = "\\$"; break;
            default: break;
         }
      }
      
      // Helper methods to identify Symbols by name or by token.
      public boolean hasName(String sName) {
         return sName.equals(name);
      }
      
      public boolean hasToken(String sToken) {
         return sToken.matches(token);
      }
      
   }
   
   // A Rule has a set of Strings which are the LHS and RHS Symbols.
   public class Rule {
      String leftHand;
      ArrayList<String> rightHand;
      
      public Rule(String ruleString) {
         rightHand = new ArrayList<>();

         String[] symbols = ruleString.split(" ");
         
         leftHand = symbols[0];
         
         for(int i = 1; i < symbols.length; i++)
            rightHand.add(symbols[i]); 
      }
   }
   
   // More private helper methods.
   // A simple way of finding whether a Symbol is non-terminal or not.
   private boolean isNonTerminalSymbol(String sName) {
      return Character.isUpperCase(sName.charAt(0));
   }
   
   // These are used to search for the index of a Symbol in either list
   // by providing its name or its token.
   private int searchBySymbolName(String sName) {
      for(Symbol i : terminals) 
         if (i.hasName(sName))
            return terminals.indexOf(i);
      for(Symbol i : nonTerminals) 
         if (i.hasName(sName))
            return nonTerminals.indexOf(i);
      return -1;
   }
   
   private int searchBySymbolToken(String sToken) {
      for(Symbol i : terminals) 
         if (i.hasToken(sToken))
            return terminals.indexOf(i);
      return -1;
   }
   
   // It does what it says.
   public void makeNonTerminalSymbolsAndRules() {
      for(int i = 0; i < grammar.size(); i++) {
         nonTerminals.add(new Symbol(grammar.get(i), i));
      }
   }
   
   // This is a weird one.
   // makeTerminalSymbols turns the entire grammar into one long String,
   // then splits it into a StringStream.
   // Using stream operators, it pulls it apart until a stream of distinct,
   // lowercase, non-operator Strings remain... the names of the terminal
   // symbols in the grammar; which are then added to the terminal Symbols list.
   public void makeTerminalSymbols() {
      String flattenedGrammar = "";
      for(String g : grammar) flattenedGrammar += g + " ";
      
      Arrays.stream(flattenedGrammar.split(" "))
       .filter(s -> !Character.isUpperCase(s.charAt(0)))
       .filter(s -> !s.equals("|") && !s.equals("="))
       .distinct()
       .forEach(t -> {
          terminals.add(new Symbol(t));
       });
      
   }
   
   // It does what it says.
   // It creates an LL parse table by looking at the LHS symbol of all of
   // the kidRules and checking if they match the given terminal symbol of
   // that column. It is capable of searching the kidRules of a LHS symbol
   // if it happens to be non-terminal, but if the LHS of its rule(s) are also
   // non-terminal it does nothing. So it's pretty naive in spite of its
   // potential for producing beautiful output.
   public void makeLLTable() {
      int numRows = grammar.size();
      int numColumns = terminals.size();
        
      parseTable = new Rule[numRows][numColumns];
      
      for(int i = 0; i < numRows; i++)
         for(int j = 0; j < numColumns; j++)
            for(Rule k : nonTerminals.get(i).kidRules)
               parseTable[i][j] = 
                digForTerminalSymbol(k, terminals.get(j).name);

      for(int i = 0; i < numRows; i++)
         for(int j = 0; j < numColumns; j++)
            for(Rule k : nonTerminals.get(i).kidRules)
               for(int l = 0; l < numRows; l++)
                  if(k.leftHand.equals(nonTerminals.get(l).name))
                     for(Rule m : nonTerminals.get(l).kidRules)
                        if(m.leftHand.equals(terminals.get(j).name))
                           parseTable[i][j] = k;
      
      for(int i = 0; i < numRows; i++)
         for(int j = 0; j < numColumns; j++)
            for(Rule k : nonTerminals.get(i).kidRules) 
               if (k.leftHand.equals(terminals.get(j).name))
                  parseTable[i][j] = k;
      
   }
   
   // A half-baked, recursive(!!!) attempt at solving the problem described 
   // for the method above. It works - it doesn't break everything - but 
   // apparently only in some special cases. A bit holistic. 
   public Rule digForTerminalSymbol(Rule rr, String tname) {
      if(rr.leftHand.equals(tname))
         return rr;
      if(isNonTerminalSymbol(rr.leftHand)) 
      {
         for(Rule i : 
          nonTerminals.get(searchBySymbolName(rr.leftHand)).kidRules) {
               return digForTerminalSymbol(i, tname);
         }
      }
      return null;
   }
   
   // This is so I could make my table into .csv format.
   public void printLLTableCSVFormat() {
      System.out.print("Symbols,");
      for (int i = 0; i < terminals.size(); i++) {
         if (i != terminals.size() - 1)
            System.out.print(terminals.get(i).name + ",");
         else System.out.print(terminals.get(i).name);
      }
      
      for (int i = 0; i < nonTerminals.size(); i++) {
         System.out.printf("\n%s,", nonTerminals.get(i).name);
         for(int j = 0; j < terminals.size(); j++) {
            if(rules.indexOf(parseTable[i][j]) != -1)
               System.out.printf("%d", rules.indexOf(parseTable[i][j]));
            if(j != terminals.size() - 1)
               System.out.print(",");
         }
      }
      System.out.println("");
   }
   
   // It does what it says, and does so beautifully.
   public void printLLTable() {
      System.out.println("\nPrint parse table:");
      System.out.print("Term ID#  : ");
      for (int i = 0; i < terminals.size(); i++) 
         System.out.printf("%2d ", i);
      
      for (int i = 0; i < nonTerminals.size(); i++) {
         System.out.printf("\n%-10s: ", nonTerminals.get(i).name);
         for(int j = 0; j < terminals.size(); j++)
            System.out.printf("%2d ", rules.indexOf(parseTable[i][j]));
      }
      System.out.println("");
   }
   
   public void parseItUpm8(String line) {
      theStack = new Stack<>();
      theStack.push(STAHP);
      theStack.push(nonTerminals.get(0));
      
      String[] input = line.split(" ");
      
      int i = 0, j = 0;
      System.out.println("LET'S PARSE THIS BRO!!!");
      while(!theStack.empty()) {
         System.out.println("\nIteration: " + j++);
         System.out.println("Symbol at top of stack: " + theStack.peek().name);
         System.out.println("Token at front of input: " + input[i]);
         // M1: Check if the top of the stack matches the front.
         // If so, pop off the stack and move to the next token of input.
         if(input[i].matches(theStack.peek().token)) {
            System.out.println("M1: MATCH!!! Poppin' and droppin'...");
            theStack.pop();
            i++;
         } 
         else {
            // M2: If the top of the stack is a terminal symbol after M1 didn't
            // happen, it means we've goofed up.
            if(terminals.contains(theStack.peek())) {
               System.out.println("M2: FAIL");
               return;
            }

            // The following could be done with less lines, but it'd probably
            // look no less - or much more - ugly. And of course, Rule #1.
            int tableRow = nonTerminals.indexOf(theStack.peek());
            int tableColumn = searchBySymbolToken(input[i]);
            System.out.println("Table row and column: " 
             + tableRow + " " + tableColumn);
            Rule cell = parseTable[tableRow][tableColumn];

            // M3: If the cell at the intersection of our symbol on top of the
            // stack and the symbol for the given token is empty; then we've
            // failed to parse.
            if(cell == null) {
               System.out.println("Code M3: NOPE");
               return;
            } else {

            // M4: If we didn't match anything and nothing went wrong; we pop
            // and toss the right-hand symbols of the cell rule on top of the
            // stack... in backwards order.
               System.out.println("M4: Poppin' and pushin', "
                + "Rule Number: " + rules.indexOf(cell));
               theStack.pop();
               for(int k = cell.rightHand.size() - 1; k >= 0; k--) {
                  // Our kind of naive search makes it necessary to check whether
                  // the name of the symbol is uppercase - if it is, then it's
                  // a non-terminal symbol. So we use the index returned from our
                  // search to push from that list of symbols.
                  if (Character.isUpperCase(cell.rightHand.get(k).charAt(0)))
                     System.out.println("Pushing " +
                  theStack.push(
                   nonTerminals.get(searchBySymbolName(cell.rightHand.get(k))))
                     .name);
                  else
                     System.out.println("Pushing " +
                     theStack.push(
                      terminals.get(searchBySymbolName(cell.rightHand.get(k))))
                      .name);
               }

               if (Character.isUpperCase(cell.leftHand.charAt(0)))
                     System.out.println("Pushing " +
                  theStack.push(
                   nonTerminals.get(searchBySymbolName(cell.leftHand)))
                     .name);
                  else
                     System.out.println("Pushing " +
                     theStack.push(
                      terminals.get(searchBySymbolName(cell.leftHand)))
                      .name);
            }
         }
      }
      System.out.println("HOLY PARSING BATMAN! IT WORKED!");
   }
   
   public void printInfo() {
      System.out.println("\nPrinting info for parser...");
      System.out.println("Printing list of Rules...");
      rules.stream().forEach(r -> {
         System.out.printf("Rule %-2d:\nLeft-Hand Symbol: %-10s;"
          + " Right-Hand Symbols: ", rules.indexOf(r), r.leftHand);
         r.rightHand.stream().forEach(rh -> {
            System.out.print(rh + " ");
         });
         System.out.println("");
      });
      
      System.out.println("\nPrinting list of non-terminal Symbols...");
      nonTerminals.stream().forEach(nt -> {
         System.out.printf("Symbol name: %-10s; Non-Terminal ID#: %-2d" 
          + "; Kid Rules: ", nt.name, + nonTerminals.indexOf(nt));
         nt.kidRules.stream().forEach(kr -> {
            System.out.print(rules.indexOf(kr) + " ");
         });
         System.out.println("");
      });
      
      System.out.println("\nPrinting list of terminal Symbols...");
      terminals.stream().forEach(t -> {
         System.out.printf("Symbol name: %-10s; Terminal ID#: %-2d; "
          + "Token: %-10s\n", t.name, terminals.indexOf(t), t.token);
      });
   }
   
   /**
    * @param args the command line arguments
    * @throws IOException
    */
   public static void main(String[] args) throws IOException {
      ParseIt parser = new ParseIt();
      
      BufferedReader gin = new BufferedReader(new FileReader("grammar.txt"));
      
      gin.lines().forEach(g -> {parser.grammar.add(g);});
      
      // Let's make our parser.
      parser.makeNonTerminalSymbolsAndRules();
      parser.makeTerminalSymbols();
      parser.printInfo();
      
      parser.makeLLTable();
//      parser.printLLTableCSVFormat();
      System.out.println("Printing (Incomplete) Table for A6 Grammar:");
      parser.printLLTable();
      
      
      System.out.println("\nMaking a little parser...");
      ParseIt littleParser = new ParseIt();
      littleParser.grammar.add("Pgm = kwdprog brace1 int brace2");
      
      // Let's make our parser.
      littleParser.makeNonTerminalSymbolsAndRules();
      littleParser.makeTerminalSymbols();
      littleParser.printInfo();
      
      littleParser.makeLLTable();
//    
      
      Scanner in = new Scanner(System.in);
      System.out.println("\n\nShowing example parser output for tiny grammar"
       + "\nPgm = kwdprog brace1 int brace2");
      
      littleParser.parseItUpm8("prog { 7 } $");
   }
}