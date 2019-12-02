package paintitblack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CECS 323 MW 1 PM 
 * Lab 4: Paint It Black
 * Ruben Baerga ID 010366978
 * @author Rub3z using a template by Neal Terrell
 */

// A Map ADT structure using a red-black tree, where keys must implement
// Comparable.
public class RedBlackTreeMap<TKey extends Comparable<TKey>, TValue> {

   // A Node class.
   private class Node {
   private TKey mKey;
   private TValue mValue;
   private Node mParent;
   private Node mLeft;
   private Node mRight;
   private boolean mIsRed;

   public Node(TKey key, TValue data, boolean isRed) {
      mKey = key;
      mValue = data;
      mIsRed = isRed;
      mLeft = NIL_NODE;
      mRight = NIL_NODE;
		
   }
      @Override
      public String toString() {
         return "(" + mKey + ", " + mValue + ")";
      }
   }
	
   private Node mRoot;
   private int mCount;

   // Rather than create a "blank" black Node for each NIL, we use one shared
   // node for all NIL leaves.
   private final Node NIL_NODE = new Node(null, null, false);

   // Get the # of keys in the tree.
   public int getCount() {
      return mCount;
   }

   // Finds the value associated with the given key.
   public TValue find(TKey key) {
      Node n = bstFind(key, mRoot); // find the Node containing the key if any
      if (n == null || n == NIL_NODE)
         throw new RuntimeException("Key not found");
      return n.mValue;
   }

   // Inserts a key/value pair into the tree, updating the red/black balance
   // of nodes as necessary. Starts with a normal BST insert, then adjusts.
   public void insert(TKey key, TValue data) {
      Node n = new Node(key, data, true); // nodes start red
      // normal BST insert; n will be placed into its initial position.
      // returns false if an existing node was updated (no rebalancing needed)
      boolean insertedNew = bstInsert(n, mRoot); 
      if (!insertedNew) return;
      // check cases 1-5 for balance violations.
      checkBalance(n);
   }

   // Applies rules 1-5 to check the balance of a tree with newly inserted
   // node n.  
   private void checkBalance(Node n) {
      if (n == mRoot) { // case 1: new node is root.
         n.mIsRed = false; // Paint it black.
      }
      // Case 2: Parent is black...
      else if (!n.mParent.mIsRed) {} // nothing will happen.
      // Case 3: Parent and Uncle are red.
      else if (getUncle(n).mIsRed) {
         n.mParent.mIsRed = false;
         getUncle(n).mIsRed = false;      // Repaint P, U and G
         getGrandparent(n).mIsRed = true; 
         checkBalance(getGrandparent(n)); // Recurse on G
      }
      // Case 4: n is LR or RL grandchild
      else if (n == getGrandparent(n).mLeft.mRight) { // n is LR
         singleRotateLeft(n.mParent);   // Rotate on P
         singleRotateRight(n.mParent);  // Then on G... which is now n's parent
         n.mIsRed = false; n.mRight.mIsRed = true; // Repaint G and P
         // These two lines were case 5, modified so as to not screw up.
      }
      else if (n == getGrandparent(n).mRight.mLeft) {// n is RL
         singleRotateRight(n.mParent);
         singleRotateLeft(n.mParent);             // As above, reversed.
         n.mIsRed = false; n.mLeft.mIsRed = true;
      }
      // Case 5: n is LL or RR grandchild
      else if (n == getGrandparent(n).mLeft.mLeft) {// n is LL
         singleRotateRight(getGrandparent(n));
         n.mParent.mIsRed = false;  // Rotate at G, repainting as above
         n.mParent.mRight.mIsRed = true;
      }
      else if (n == getGrandparent(n).mRight.mRight) { // n is RR
         singleRotateLeft(getGrandparent(n));
         n.mParent.mIsRed = false;  // Same here.
         n.mParent.mLeft.mIsRed = true;
      }
   }

   // Returns true if the given key is in the tree.
   public boolean containsKey(TKey key) {
      return !(bstFind(key, mRoot) == null);
   }

   // Prints a pre-order traversal of the tree's nodes, printing the key, value,
   // and color of each node.
   public void printStructure() {
      printStructure(mRoot);
   }
   
   private void printStructure(Node n) {
      System.out.print(n.mKey + " " + n.mValue + " ");
      leftOrRightChildOf(n);
      System.out.println((n.mIsRed) ? "Red" : "Black");
      
      if (n.mLeft != NIL_NODE) printStructure(n.mLeft);
      if (n.mRight != NIL_NODE) printStructure(n.mRight);
   }
   
   // Display whether or not given node is a left or right child for testing
   private void leftOrRightChildOf(Node n) {
      if (!(n == mRoot)) {
         System.out.print(((n == n.mParent.mLeft) ? " left" : " right")
          + " child of " + n.mParent + " ");
      }
   }

   // Returns the Node containing the given key. Recursive.
   private Node bstFind(TKey key, Node currentNode) {
      return (currentNode == null || currentNode == NIL_NODE ||
       key.compareTo(currentNode.mKey) == 0) ?
       currentNode:
       ((key.compareTo(currentNode.mKey) < 0) ?
        bstFind(key, currentNode.mLeft) : 
        bstFind(key, currentNode.mRight));
      // That's right. A ternary operator WITHIN  a ternary operator.
      // That's how it's done, son.
   }

   //////////////// These functions are needed for insertion cases.
   // Gets the grandparent of n.
   private Node getGrandparent(Node n) {
      return n.mParent.mParent;
   }

   // Gets the uncle (parent's sibling) of n.
   private Node getUncle(Node n) {
      return (n.mParent == getGrandparent(n).mLeft) ?
       getGrandparent(n).mRight:
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
      }
      else if (p.mLeft == n) {
         p.mLeft = l;
         l.mParent = p;
      }
      else {
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
      }
      else if (p.mRight == n) {
         p.mRight = r;
         r.mParent = p;
      }
      else {
         p.mLeft = r;
         r.mParent = p;
      }
      n.mParent = r;
   }

   // This method is used by insert. It is complete.
   // Inserts the key/value into the BST, and returns true if the key wasn't 
   // previously in the tree.
   private boolean bstInsert(Node newNode, Node currentNode) {
      if (mRoot == null) {
         // case 1
         mRoot = newNode;
         return true;
      }
      else{
         int compare = currentNode.mKey.compareTo(newNode.mKey);
         if (compare < 0) {
         // newNode is larger; go right.
            if (currentNode.mRight != NIL_NODE)
               return bstInsert(newNode, currentNode.mRight);
            else {
               currentNode.mRight = newNode;
               newNode.mParent = currentNode;
               mCount++;
               return true;
            }
         }
         else if (compare > 0) {
            if (currentNode.mLeft != NIL_NODE)
               return bstInsert(newNode, currentNode.mLeft);
            else {
               currentNode.mLeft = newNode;
               newNode.mParent = currentNode;
               mCount++;
               return true;
            }
         }
         else {
            // found a node with the given key; update value.
            currentNode.mValue = newNode.mValue;
            return false; // did NOT insert a new node.
         }
      }
   }
   
   /**
    * @param args the command line arguments
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {
      File baseball = new File("players_homeruns.csv");
      
      RedBlackTreeMap homeruns = new RedBlackTreeMap();
      
      Scanner read = new Scanner(baseball);
      
      String[] line;
      int num = 0;
      while(num < 10) {
         line = read.nextLine().split(",");
         homeruns.insert(line[0], Integer.parseInt(line[1]));
         num++;
      }
      
      System.out.println("Tree Structure:");
      homeruns.printStructure();
      System.out.println("");
      
      System.out.println("Find Babe Ruth (a leaf)...");
      System.out.println(homeruns.find("Babe Ruth"));
      
      System.out.println("Find Honus Wagner (a root node)");
      System.out.println(homeruns.find("Honus Wagner"));
      
      System.out.println("Find Rogers Hornsby (has one NIL child)");
      System.out.println(homeruns.find("Rogers Hornsby"));
      
      System.out.println("Find Ted Williams "
       + "(a red node with two non-NIL children)");
      System.out.println(homeruns.find("Ted Williams"));
   }
}
