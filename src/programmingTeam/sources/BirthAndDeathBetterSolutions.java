package programmingTeam.sources; /**
 * @author Ruben Baerga-Ortega, 5/29/2019 to provide better
 * solution(s) for the Zwift coding interview question.
 *
 * Hello,
 *
 * It came to my attention that the solution I submitted for your review earlier
 * was deeply flawed. For a bit there, I made the mistake of thinking that my
 * solution was clever. That was foolish.
 *
 * I was foolish to assume that using the TreeMap class and doing some hacky
 * workaround would be sufficient to demonstrate that I know what I am doing;
 * that I am cut out to work for Zwift. I am sorry about that.
 *
 * My biggest disappointment is not that I have been summarily rejected, but
 * that the work I had to show you was not an accurate reflection of my ability
 * and knowledge as a programmer. It was simply not good.
 *
 * So instead of resorting to careless use of Java libraries and silly
 * workarounds for them, I have decided to implement every single step of my
 * solution to this problem from start to finish; in the hopes of showing that
 * I do know what I am doing, and that I am quite capable. Or at the very least,
 * I can show that I'm not as much a noob as my previous work would suggest.
 *
 * I will:
 * 1. Create a red-black tree structure.
 * 2. Insert birth and death years of Persons into this tree, using the years
 *    as keys; and adjusting the structure of the tree as necessary.
 * 3. Upon inserting a new node into the tree or finding the key already exists,
 *    add to a "tally" or counter of births and deaths that is "mapped"*** (see
 *    note below) to this key.
 * 4. Use a recursive method to do an in-order traversal of the nodes in the
 *    tree, finding the year with the highest population of people alive using
 *    the birth and death tally values for each year.
 *
 * For completeness' sake, I will also include the previous two (bad) solutions
 * I submitted, and a new "counting" solution that runs in linear O(n) time,
 * and examine each solution's advantages and failings in terms of runtime,
 * memory requirements, and flexibility. And I will make the case for why my new
 * solution(s) are the best.
 *
 * The output of my program will show a comparison of results for each solution
 * across a variety of test cases.
 *
 *    ***Note: I use quotes here because I think it is a bit of a misnomer to
 *    consider this a mapping or key-value pair, since there are two values.***
 *
 * ****** Notable Lines: *******
 * Lines 78 - 330: RedBlackTreeMap class.
 *  Ctrl + F: "RedBlackTreeMapTally"
 *    Lines 133 - 174: Recursive Tree Traversal.
 *     Ctrl + F: "inOrderTraverseGetYear"
 *    Lines 185 - 278: Binary Search Tree Insert and Red-Black Tree balancing.
 *     Ctrl + F: "bstInsert"
 * Lines 353 - 410: Counting sort linear O(n) runtime complexity solution.
 *  Ctrl + F: "betterCountingSolution"
 * Lines 433 - 597: Main method.
 *  Ctrl + F: "main"
 * Lines 600 - 740: Analysis and (line 718) closing notes.
 *  Ctrl + F: "ANALYSIS" or "CLOSING"
 */

import java.util.*;

public class BirthAndDeathBetterSolutions {

   /**
    * A modified Map ADT structure using a red-black tree, with a modified
    * insertion method to tally births and deaths added to a given year.
    * I made this by modifying a RedBlackTreeMap class I made some time ago.
    *
    * Unlike a normal map structure, the nodes can be said to have two values
    * instead of one. I thought about making a "Tally" class that would hold
    * these two ints and act as the one "value" mapped to the keys... but since
    * this is just for the purposes of this program, that seemed a bit
    * pedantic.
    */
   static class RedBlackTreeMapTally {

      // A Node class.
      private class Node {
         private int mKey;
         private int mBirths;
         private int mDeaths;
         private Node mParent;
         private Node mLeft;
         private Node mRight;
         private boolean mIsRed;

         /**
          * @param key The year.
          * @param isBirth This will make the Node start with one birth or death
          *                tally appropriately.
          * @param isRed This will make the Node red or black appropriately.
          */
         public Node(int key, boolean isBirth, boolean isRed) {
            mKey = key;
            if (isBirth) {
               mBirths = 1;
               mDeaths = 0;
            }
            else {
               mBirths = 0;
               mDeaths = 1;
            }
            mIsRed = isRed;
            mLeft = NIL_NODE;
            mRight = NIL_NODE;
         }
      }

      private Node mRoot;

      // Rather than create a "blank" black Node for each NIL, we use one shared
      // node for all NIL leaves.
      private final Node NIL_NODE = new Node(0, false, false);

      /**
       * Now, THIS is where the magic happens. For real this time.
       * This recursive method performs an in-order traversal of the tree.
       * By doing this, we examine the nodes of the tree in numerical order.
       * Just like in a simpler counting implementation, we keep track of
       * the number of people alive each year, counting the number of people
       * born that year first to get the highest number.
       *
       * @param people List of Persons.
       * @param printYears If this is true, information on the tree traversal
       * and nodes visited will appear when run.
       */
      private int numberOfPeopleAlive;
      private int max;
      private int bestYear;
      public int getYearWithMostPeopleAlive(boolean printYears) {
         numberOfPeopleAlive = 0;
         max = 0;
         bestYear = 0;
         if (mRoot != null) {
            inOrderTraverseGetYear(mRoot, printYears);
         }
         if (printYears) System.out.println("Done.");
         return bestYear;
      }

      private void inOrderTraverseGetYear(Node currentNode,
                                          boolean printYears) {
         if (currentNode != null && currentNode != NIL_NODE) {

            if (printYears) {
               System.out.print("Traversing left...");
            }
            inOrderTraverseGetYear(currentNode.mLeft, printYears);

            numberOfPeopleAlive += currentNode.mBirths;
            if (numberOfPeopleAlive > max) {
               max = numberOfPeopleAlive;
               bestYear = currentNode.mKey;
            }

            if (printYears) {
               System.out.println("\nYear: " + currentNode.mKey +
                " Birth Tally: " + currentNode.mBirths +
                " Death Tally: " + currentNode.mDeaths +
                " Number of people alive: " + numberOfPeopleAlive +
                " Most alive so far: " + max +
                " Best year so far: " + bestYear);
            }
            numberOfPeopleAlive -= currentNode.mDeaths;

            if (printYears) {
               System.out.print("Traversing right...");
            }
            inOrderTraverseGetYear(currentNode.mRight, printYears);
         }
      }

      /**
       * Inserts a node into the tree, updating the red/black balance
       * of nodes as necessary. Starts with a normal BST insert, then adjusts.
       *
       * @param key The year.
       * @param isBirth Whether this insertion will tally a birth or death for
       *                the given year key.
       */

      public void insert(int key, boolean isBirth) {
         Node n = new Node(key, isBirth, true); // nodes start red
         boolean insertedNew = bstInsert(n, mRoot, isBirth);
         if (!insertedNew) return;
         checkBalance(n);
      }

      /**
       * This method is used by insert.
       * Inserts the key/value into the BST, and returns true if the key wasn't
       * previously in the tree.
       * @param newNode The new Node being inserted.
       * @param currentNode The current Node being compared to newNode.
       * @param isBirth will increment the birth or death tally at an existing
       * node appropriately depending on its truth value.
       * @return true if a new Node was added, false otherwise.
       */
      private boolean bstInsert(Node newNode,
                                Node currentNode,
                                boolean isBirth) {
         if (mRoot == null) {
            // case 1
            mRoot = newNode;
            return true;
         } else {
            if (currentNode.mKey < newNode.mKey) {
               if (currentNode.mRight != NIL_NODE)
                  return bstInsert(newNode, currentNode.mRight, isBirth);
               else {
                  currentNode.mRight = newNode;
                  newNode.mParent = currentNode;
                  return true;
               }
            } else if (currentNode.mKey > newNode.mKey) {
               if (currentNode.mLeft != NIL_NODE)
                  return bstInsert(newNode, currentNode.mLeft, isBirth);
               else {
                  currentNode.mLeft = newNode;
                  newNode.mParent = currentNode;
                  return true;
               }
            } else {
               // found a node with the given key; tally a birth or death.
               if (isBirth) {
                  currentNode.mBirths += 1;
               }
               else {
                  currentNode.mDeaths += 1;
               }
               return false; // did NOT insert a new node.
            }
         }
      }

      // Applies rules 1-5 to check the balance of a tree with newly inserted
      // node n; and makes rotations accordingly.
      private void checkBalance(Node n) {
         if (n == mRoot) { // case 1: new node is root.
            n.mIsRed = false; // Paint it black.
         }
         // Case 2: Parent is black...
         else if (!n.mParent.mIsRed) {
         } // nothing will happen.
         // Case 3: Parent and Uncle are red.
         else if (getUncle(n).mIsRed) {
            n.mParent.mIsRed = false;
            getUncle(n).mIsRed = false; // Repaint Parent, Uncle and Grandpa
            getGrandparent(n).mIsRed = true;
            checkBalance(getGrandparent(n)); // Recurse on Grandpa
         }
         // Case 4: n is LR or RL grandchild
         else if (n == getGrandparent(n).mLeft.mRight) { // n is LR
            singleRotateLeft(n.mParent);   // Rotate on Parent
            singleRotateRight(n.mParent);  // Rotate on Grandpa, now n's parent.
            n.mIsRed = false;
            n.mRight.mIsRed = true; // Repaint Grandpa and Parent.
            // These two lines were case 5, modified so as to not screw up.
         } else if (n == getGrandparent(n).mRight.mLeft) {// n is RL
            singleRotateRight(n.mParent);
            singleRotateLeft(n.mParent);             // As above, reversed.
            n.mIsRed = false;
            n.mLeft.mIsRed = true;
         }
         // Case 5: n is LL or RR grandchild
         else if (n == getGrandparent(n).mLeft.mLeft) {// n is LL
            singleRotateRight(getGrandparent(n));
            n.mParent.mIsRed = false;  // Rotate at Grandpa, repainting as above
            n.mParent.mRight.mIsRed = true;
         } else if (n == getGrandparent(n).mRight.mRight) { // n is RR
            singleRotateLeft(getGrandparent(n));
            n.mParent.mIsRed = false;  // Same here.
            n.mParent.mLeft.mIsRed = true;
         }
      }

      //////////////// These functions are needed for insertion cases.
      // Gets the grandparent of n.
      private Node getGrandparent(Node n) {
         return n.mParent.mParent;
      }

      // Gets the uncle (parent's sibling) of n.
      private Node getUncle(Node n) {
         return (n.mParent == getGrandparent(n).mLeft) ?
          getGrandparent(n).mRight :
          getGrandparent(n).mLeft;
      }

      // Rotate the tree right at the given node.
      private void singleRotateRight(Node n) {
         Node l = n.mLeft, lr = l.mRight, p = n.mParent;
         n.mLeft = lr;
         lr.mParent = n;
         l.mRight = n;
         if (n == mRoot) {
            mRoot = l;
            l.mParent = null;
         } else if (p.mLeft == n) {
            p.mLeft = l;
            l.mParent = p;
         } else {
            p.mRight = l;
            l.mParent = p;
         }
         n.mParent = l;
      }

      // Rotate the tree left at the given node.
      private void singleRotateLeft(Node n) {
         Node r = n.mRight, rl = r.mLeft, p = n.mParent;
         n.mRight = rl;
         rl.mParent = n;
         r.mLeft = n;
         if (n == mRoot) {
            mRoot = r;
            r.mParent = null;
         } else if (p.mRight == n) {
            p.mRight = r;
            r.mParent = p;
         } else {
            p.mLeft = r;
            r.mParent = p;
         }
         n.mParent = r;
      }
   }

   /**
    * This new counting sort solution runs in constant time O(Y), where Y is
    * the range of years for births and deaths.
    * However, the space complexity is also O(2Y), so you can't have a
    * huge range of years.
    *
    * Unlike the older implementation, it can handle negative values for years
    * by finding the smallest/earliest birthYear value (minYear), and making the
    * first index of the array(s) for the range of years correspond to that
    * value. By subtracting minYear from the birth or deathYear value tallied
    * in the array, we increment the appropriate index for that year.
    *
    * We find the best year by iterating through the arrays, keeping track of
    * how many people are alive and keeping the index of the highest population
    * so far. Returning the correct year value is just a matter of adding
    * minYear to that index.
    *
    * @param people List of Persons.
    * @param printYears If this is true, information on the highest population
    *                   found so far will be displayed.
    */
   public static int betterCountingSolution(List<Person> people,
                                            boolean printYears) {
      int minYear = Integer.MAX_VALUE;
      int maxYear = Integer.MIN_VALUE;

      for (Person p : people) {
         if (p.birthYear < minYear) {
            minYear = p.birthYear;
         }

         if (p.deathYear > maxYear) {
            maxYear = p.deathYear;
         }
      }

      long yearRange = maxYear - minYear + 1;
      if (printYears) {
         System.out.println("First birth: " + minYear +
          ", Last Death: " + maxYear +
          ", Year Range: " + yearRange);
      }

      int birthsEachYear[];
      int deathsEachYear[];

      try {
         birthsEachYear = new int[(int)yearRange];
         deathsEachYear = new int[(int)yearRange];
      }
      catch (OutOfMemoryError oom) {
         System.out.println("OUT OF MEMORY ERROR!! Array size too large.");
         return -1;
      }

      for (Person p : people) {
         birthsEachYear[p.birthYear - minYear]++;
         deathsEachYear[p.deathYear - minYear]++;
      }

      int max = 0;
      int numberOfPeopleAlive = 0;
      int bestYear = 0;

      for (int i = 0; i < birthsEachYear.length; i++) {
         numberOfPeopleAlive += birthsEachYear[i];

         if (numberOfPeopleAlive > max) {
            max = numberOfPeopleAlive;
            bestYear = i;
            if (printYears) {
               System.out.println("Solution 4. Year: " + i +
                                  " Num people alive: " + max);
            }
         }
         numberOfPeopleAlive -= deathsEachYear[i];
      }
      return bestYear + minYear;
   }

   static class Person {
      int birthYear;
      int deathYear;

      public Person(int b, int d) {
         this.birthYear = b;
         this.deathYear = d;
      }
   }

   /**
    * The main method. Allows user to select from 10 test cases, inserting
    * the given number of persons into a list, with randomly-generated birth
    * and death years in the specified range; then runs each of the four
    * solutions to the problem on the list.
    *
    * Further analysis and closing is below the main method, at line 600; and
    * the bad solutions to the problem are beneath that.
    *
    * @param args
    */
   public static void main(String[] args) {
      List<Person> people = new ArrayList<>();

      System.out.println("Enter a number to select an option.\n" +
       "  Note that print outs cause inaccurate runtime results.\n" +
       "  No input validation. Invalid input will run w/ default values.\n" +
       "(1) 5 Persons over 10 years, no negative values w/ print outs.\n" +
       "(2) 5 Persons over 10000 years, no negative values w/ print outs.\n" +
       "(3) 10 Persons over 100000000 years, no negative values.\n" +
       "(4) 100 Persons over 10000000 years, no negative values.\n" +
       "(5) 1000 Persons over 1000000 years, no negative values.\n" +
       "(6) 10000 Persons over 100000 years, no negative values.\n" +
       "(7) 100000 Persons over 10000 years w/ negative values.\n" +
       "(8) 1000000 Persons over 1000 years w/ negative values.\n" +
       "(9) 10000000 Persons over 100 years w/ negative values.\n" +
       "(0) 10000000 Persons over 100000000 years.");

      Scanner in = new Scanner(System.in);
      int choice = -1;

      // I thought it less problematic to run forward with a default case if
      // there is any bad input.
      try {
         choice = in.nextInt();
      }
      catch (InputMismatchException ime) {
         System.out.println("Bad input. Running default case...");
      }

      int numPeople = 10;
      int numYears = 10;
      boolean negative = false;
      boolean printYears = false;

      switch(choice) {
         case 1: numPeople = 5; numYears = 10;
                  negative = false; printYears = true;
            break;
         case 2: numPeople = 5; numYears = 1000;
                  negative = false; printYears = true;
            break;
         case 3: numPeople = 10; numYears = 100000000;
                  negative = false; printYears = false;
            break;
         case 4: numPeople = 100; numYears = 10000000;
                  negative = false; printYears = false;
            break;
         case 5: numPeople = 1000; numYears = 1000000;
                  negative = false; printYears = false;
            break;
         case 6: numPeople = 10000; numYears = 100000;
                  negative = false; printYears = false;
            break;
         case 7: numPeople = 100000; numYears = 10000;
                  negative = true; printYears = false;
            break;
         case 8: numPeople = 1000000; numYears = 1000;
                  negative = true; printYears = false;
            break;
         case 9: numPeople = 10000000; numYears = 100;
                  negative = true; printYears = false;
            break;
         case 0: numPeople = 10000000; numYears = 1000000000;
                  negative = true; printYears = false;
         default:
            break;

      }

      // Add some random Persons to the list, making sure they are born
      // before they die.
      System.out.println("Generating list of Persons...");
      for (int i = 0; i < numPeople; i++) {
         int birth, death;
         if (negative) {
            birth = (int) (Math.random() * numYears) - (numYears / 2);
            death = (int) (Math.random() * numYears) - (numYears / 2);
            while (death < birth) {
               death = (int) (Math.random() * numYears) - (numYears / 2);
            }
         }
         else {
            birth = (int) (Math.random() * numYears);
            death = (int) (Math.random() * numYears);

            while (death < birth) {
               death = (int) (Math.random() * numYears);
            }
         }
         people.add(new Person(birth, death));
      }

      // This prints out a list of people.add() lines for each person in the
      // list that could be used to test out manually if desired.
      if (printYears) {
         for (int i = 0; i < numPeople; i++) {
            System.out.println(
             "people.add(new Person(" + people.get(i).birthYear + ", "
             + people.get(i).deathYear + "));");
         }
      }

      RedBlackTreeMapTally years = new RedBlackTreeMapTally();

      int result1 = -1, result2 = -1, result3 = -1, result4 = -1;
      long runtime1 = -1, runtime2 = -1, runtime3 = -1, runtime4 = -1;
      long timer;
      System.out.println();

      if (numPeople > 100000 || numYears >= 100000000) {
         System.out.println(
          "O(n^2) algorithm will take too long for > 100000 Persons or " +
           "a range of > 100000000 years.");
      }
      else {
         System.out.println("Running first algorithm...");
         timer = System.nanoTime();
         result1 = firstCountingSolution(people, numYears, printYears);
         runtime1 = System.nanoTime() - timer;
      }

      if (numPeople > 1000000) {
         System.out.println("Bad tree algorithm takes too long or does not " +
          "complete (I didn't bother waiting > 2 min) for > 1000000 Persons.");
      }
      else {
         System.out.println("\nRunning bad tree algorithm...");
         timer = System.nanoTime();
         result2 = badTreeSolution(people, printYears);
         runtime2 = System.nanoTime() - timer;
      }

      System.out.println("\nRunning new tree algorithm...");
      timer = System.nanoTime();
      for (Person p : people) {
         years.insert(p.birthYear, true);
         years.insert(p.deathYear, false);
      }
      result3 = years.getYearWithMostPeopleAlive(printYears);
      runtime3 = System.nanoTime() - timer;

      System.out.println("\nRunning new counting algorithm...");
      timer = System.nanoTime();
      result4 = betterCountingSolution(people, printYears);
      runtime4 = System.nanoTime() - timer;

      System.out.println("\n***RESULTS***\nFor " + numPeople
       + " Persons being born and dying over " + numYears + " years.");
      System.out.println(
          "\n1. First simple counting solution O(n^2) result: " + result1
           + "\n   Total Runtime: " + runtime1 + " nanoseconds.");


      System.out.println(
          "\n2. Flawed tree solution O(n * y * log(n)) result: " + result2
           + "\n   Total Runtime: " + runtime2 + " nanoseconds.");

      System.out.println(
       "\n3. Fixed tree solution linearithmic O(n log(n)) result: " + result3
        + "\n   Total Runtime: " + runtime3 + " nanoseconds.");

      System.out.println(
       "\n4. Better counting solution linear O(n) result: " + result4
        + "\n   Total Runtime: " + runtime4 + " nanoseconds.");
   }

/*****************************************************************************
 * ********************** ANALYSIS AND CLOSING NOTES *************************
 * ***************************************************************************
 *  Now that we've arrived past the main method and all that's left to show is
 *  the bad solutions, I'll provide analysis and wrap up here.
 *
 *  I'll examine each solution, citing certain test cases displayed in the
 *  output of the program, starting with the first one I submitted.
 *  From here on I'll use N to refer to the length of the list of
 *  Persons and Y to refer to the range of years.
 *  Solution 1: It works sometimes, I guess.
 *     - It doesn't accept negative values for years.
 *     - Since it relies on array indexes to be used as return values, it needs
 *     to take in the total range of years for births and deaths as one of its
 *     parameters. It is inflexible... it doesn't adapt based on the size of
 *     its input.
 *     - The method it uses to find the best year makes things worse. Each
 *     index in the range of years between each Person's birth and death is
 *     incremented one at a time. While easy to understand, this means the
 *     runtime of the algorithm is O(N * Y).
 *     - This problem is made much worse as N or Y grows larger. For certain
 *     cases, I wouldn't even bother running it (see test case 3).
 *     - There is a problem with its space complexity that would be more
 *     apparent if we actually could/did bother to run it for a large Y value;
 *     which I'll examine in the improved counting solution.
 *
 *  Solution 2: Jesus wept.
 *     Instead of going over the logic behind this that ought to have made it
 *     work well, I am going to have to examine what makes it so bad. The
 *     offending line(s) of code here are these (edited for clarity):
 *
 *     double extraBirth = 0.000;
 *     String birthOrDeath;
 *     for (Person p : people) {
 *        while (true) {
 *           birthOrDeath = years.put(p.birthYear + extraBirth, "birth");
 *           if (birthOrDeath == null ) break;
 *           extraBirth += 0.001;
 *        }
 *     }
 *
 *     - The hacky workaround I used to ensure that I could store multiple birth
 *     and death counts in a year is not nearly as clever as it might appear.
 *     The problem is that the .put() method (an O(log Y) operation) isn't being
 *     run once. Every single time there is a collision, it is run AGAIN. This
 *     continues for every birth/death that is already logged for that year...
 *     which means that for every single Person, an O(log Y) operation is
 *     being performed up to Y times in the worst case (if it were possible);
 *     giving us a completely horrendous time complexity of O(N * Y * log(Y))!!!
 *     I feel very dumb for having done this. And it even gets worse!
 *     - If there are too many births or deaths logged in one year - in this
 *     case, more than 1000 - the algorithm completely fails, possibly logging
 *     a birth or death into the wrong year or even continuing to try and .put()
 *     for an indeterminate amount of time. You can see this happen in later
 *     test cases where the number of Persons is far in excess of the range of
 *     years.
 *     - To top it all off, the while (true) line is just bad practice in
 *     general, especially if you can't be sure the break condition will ever be
 *     met. For cases with huge numbers of Persons (case 9 and 0), the algorithm
 *     seems to run indefinitely (I waited three minutes with nothing happening
 *     a few times). So I don't bother running it.
 *
 *  Solution 3: The way my previous solution should be. To recap:
 *     1. Create a red-black tree structure.
 *     2. Insert birth and death years of Persons into this tree, using the
 *        years as keys; and adjusting the structure of the tree as necessary.
 *     3. Upon inserting a new node into the tree or finding the key already
 *        exists, add to a "tally" or counter of births and deaths that is
 *        "mapped" to this key.
 *     4. Use a recursive method to do an in-order traversal of the nodes in the
 *        tree, allowing us to visit the nodes of the tree in numerical order,
 *        finding the year with the highest population of people alive using
 *        the birth and death tally values for each year.
 *
 *     - This solution basically uses a BST Sort (or Tree Sort) to reach the
 *     answer. The Tree Sort algorithm runs in linearithmic time, since adding
 *     to the tree is a O(log Y) operation, and we add to the tree N times,
 *     giving us a complexity of O(N * log(Y)). After this, the recursive
 *     in-order traversal of the tree to find the answer is trivial, running in
 *     linear O(Y) time (it's as fast as the fourth counting solution, I tested
 *     it).
 *     - Typically, the worst case time complexity for an insertion operation
 *     is O(n), meaning the worst case time complexity for this algorithm ought
 *     to be O(N * Y). That's where the red-black tree comes in. By ensuring the
 *     tree is always balanced, the worst-case complexity remains linearithmic,
 *     since insertion into a balanced tree is O(log n).
 *     - The result is a solution that scales very well to large numbers of
 *     Persons or ranges of years. In cases 3, 4, and 5 it even runs faster than
 *     the last solution (there's an interesting reason).
 *
 *  Solution 4: Linear time complexity.
 *     - It takes negative values! And it scales to the size of its input;
 *     finding the range of years from first birth to last death and sizing its
 *     arrays accordingly.
 *     - ONE CAVEAT: Space complexity. The solution forms two arrays of size Y
 *     to perform its work. For huge ranges of years, this can cause problems,
 *     even making it fail altogether (Case 0). It's the reason why it runs
 *     slower than my new tree solution in cases 3, 4, and 5... it takes time to
 *     allocate memory for such large arrays.
 *
 *             ************** FINAL ANALYSIS *****************
 *     Fortunately for my sanity and pride as a programmer, my exhaustive new
 *     tree solution runs several times if not orders of magnitude faster than
 *     my broken/bad tree solution I submitted earlier in almost every scenario.
 *     Case 5 is the best showcase for how the old tree solution *should have*
 *     performed (almost on par with the new solution), showing that the logic
 *     was sound; but the code was shoddy; and as a result the bad tree solution
 *     lags behind or outright fails in every other case.
 *
 *     The argument I'll make for why my new tree solution is the best among
 *     the others shown (besides the fact that I think it is beautiful) is that
 *     it is consistent, flexible, and it scales well. It's the only solution
 *     that runs to completion in every test case shown, and it does so in an
 *     amount of time that runs from admirable to acceptable (in many cases just
 *     a few seconds), and without the restrictions or constraints of the
 *     other solutions. On my machine, it completes the last test case
 *     (10000000 Persons over a range of 100000000 years) in under a minute.
 *
 *
 *             ***************** CLOSING *********************
 * In case you haven't figured it out by now, I really want to work at Zwift.
 * The vision for the future of a connected and fun online gaming experience
 * brought to the fold of exercise and indoor training is a concept extremely
 * intriguing and special to me as a lifelong gamer and lover of fitness.
 * Contributing to the advancement of this vision - especially if it means,
 * through the medium of programming, working towards solutions to the problems
 * this kind of modern venture presents - is something that I would so, so much
 * love to become a valuable part of.
 *
 * Should any part of this be convincing enough for you to think that I do have
 * what it takes to be a valuable employee for this sources; it would mean the
 * world to me if you, dear reader, would make a case for having me be put
 * back into consideration for a position at Zwift.
 *
 * Thank you for reading.
 *
 * Sincerely,
 *
 * Ruben Baerga-Ortega
 *             ***********************************************
 */

   /**
    * @param people List of persons.
    * @param numYears Range of years.
    * @param printYears If this is true, information on the highest population
    *                   found so far will be displayed.
    * @return The year with the most people alive, or -1 if failed.
    */
   public static int firstCountingSolution(List<Person> people,
                                           int numYears,
                                           boolean printYears) {
      for (Person p : people) {
         if (p.birthYear < 0) {
            System.out.println("FAILED! First algorithm does not work for " +
             "years < 0.");
            return -1;
         }
      }
      int numberOfPeopleAlive[] = new int[numYears];
      for (Person p : people) {
         for (int i = p.birthYear; i <= p.deathYear; i++) {
            numberOfPeopleAlive[i]++;
         }
      }
      int max = 0;
      int bestYear = 0;
      for (int i = 0; i < numberOfPeopleAlive.length; i++) {
         if (numberOfPeopleAlive[i] > max) {
            max = numberOfPeopleAlive[i];
            bestYear = i;
            if (printYears) {
               System.out.println(
                "Solution 1. Year: " + i + " Num people alive: " + max);
            }
         }
      }
      return bestYear;
   }

   /**
    *
    *
    * @param people List of persons.
    * @param printYears If this is true, information on the highest population
    *                   found so far will be displayed.
    * @return The year with the most people alive, or -1 if failed.
    */
   public static int badTreeSolution(List<Person> people,
                                     boolean printYears) {
      TreeMap<Double, String> years = new TreeMap<>();
      double extraBirth = 0.000;
      double extraDeath = 0.999;
      String birthOrDeath;
      for (Person p : people) {
         while (true) {
            birthOrDeath = years.put(p.birthYear + extraBirth, "birth");
            if (birthOrDeath == null ) break;
            extraBirth += 0.001;
            if (extraBirth >= 0.999) {
               System.out.println("FAILED!!! Too many people born or died " +
                "in one year. This algorithm really is broken.");
               return -1;
            }
         }
         while (true) {
            birthOrDeath = years.put(p.deathYear + extraDeath, "death");
            if (birthOrDeath == null) break;
            extraDeath -= 0.001;
            if (extraDeath <= 0.001) {
               System.out.println("FAILED!!! Too many people born or died " +
                "in one year. This algorithm really is broken.");
               return -1;
            }
         }
         extraBirth = 0.000;
         extraDeath = 0.999;
      }
      int numberOfPeopleAlive = 0;
      int maxNumPeople = 0;
      int bestYear = 0;
      Set<Map.Entry<Double, String>> yearSet = years.entrySet();
      Iterator<Map.Entry<Double,String>> i = yearSet.iterator();
      while (i.hasNext()) {
         Map.Entry<Double,String> e = i.next();
         if(e.getValue().equals("birth")) {
            numberOfPeopleAlive++;
         }
         if(e.getValue().equals("death")) {
            numberOfPeopleAlive--;
         }
         if (numberOfPeopleAlive > maxNumPeople) {
            maxNumPeople = numberOfPeopleAlive;
            bestYear = e.getKey().intValue();
            if (printYears) {
               System.out.println(
                "Solution 2. Year: " + bestYear +
                " Num people alive: " + maxNumPeople);
            }
         }
      }
      return bestYear;
   }
}