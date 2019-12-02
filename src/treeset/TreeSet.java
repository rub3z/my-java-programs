package treeset;

/**
 *
 * @author Rub3z
 */
public class TreeSet {

   public class Node {
      private int mKey;
      private Node mLeft;
      private Node mRight;
      private Node mParent;


      public void setKey(int key) {
         mKey = key;
      }

      public void setLeft(Node lefty) {
         mLeft = lefty;
      }

      public void setRight(Node righty) {
         mRight = righty;
      }

      public void setParent(Node singleParent) {
         mParent = singleParent;
      }
   }   
    
   private Node mRoot;
   private int mCount;
   
   
   //Private helper methods :)
   private Node findKey(int key, Node n) {
      if (n == null) {
         return n;
      }
      
      if (n.mKey == key) {
         return n;
      }
      
      return (key < n.mKey ? findKey(key, n.mLeft) : findKey(key, n.mRight));
   }

   private Node findMaximum(Node n) {
      if (n.mRight == null) {
         return n;
      }
      else {
         return findMaximum(n.mRight);
      }
   }
   
   //Helper method for the helper method (lol) to do recursion
   private void findMaximum() {
      findMaximum(mRoot);
   }
   
   private void printInOrder(Node n) {
      if (mRoot == null) {
         System.out.println("");
         return;
      }
      
      if (n.mLeft != null) {
         printInOrder(n.mLeft);
      }
      System.out.print(n.mKey + " ");
      if (n.mRight != null) {
         printInOrder(n.mRight);
      }
      
   }
   
   //Public methods
   public void add(int key) {
      if (mRoot == null) {
         Node newNode = new Node();
         newNode.setKey(key);
         mRoot = newNode;
         mCount++;
      }
      else {
         addAt(key, mRoot);
      }   
   }
   
   //Helper method for add()     
   private void addAt(int key, Node n) {
      if (key == n.mKey) {
         return;
      }
      
      if (key < n.mKey) {
         if (n.mLeft == null) {
            Node newNode = new Node();
            newNode.setKey(key);
            n.setLeft(newNode);
            newNode.setParent(n);
            mCount++;
         }
         else {
            addAt(key, n.mLeft);
         }
      }
      else {
         if (n.mRight == null) {
         Node newNode = new Node();
         newNode.setKey(key);
         n.setRight(newNode);
         newNode.setParent(n);
         mCount++;
         }
         else {
            addAt(key, n.mRight);
         }
      }
   }
   
   
   public void remove(int key) {
      removeAt(key, mRoot);
   }
   
   //Helper methods for remove()
   private void removeAt(int key, Node n) {
      if (n == null) {
         throw new RuntimeException("The data isn't in here, bro.");
      }
      
      else if (key < n.mKey) {
         removeAt(key, n.mLeft);
      }
      
      else if (key > n.mKey) {
         removeAt(key, n.mRight);
      }
      else {
         removeNode(n);
      }
   }
   
   //The gigantinormous, hideous, disgusting, odious and utterly vile
   // helper method for remove()
   private void removeNode(Node n) {
      if (n.mLeft == null && n.mRight == null) {
         if (n == mRoot) {
            mRoot = null;
         }
         else {
            if (n.mParent.mLeft == n) {
               n.mParent.setLeft(null);
            }
            else {
               n.mParent.setRight(null);
            }
         }
         mCount--; 
      }
      
      else if (n.mLeft == null || n.mRight == null) {
         if (n == mRoot) {
            if (n.mLeft != null) { 
               mRoot = n.mLeft;
               n.setParent(null);
            }
            else if (n.mRight != null) {
               mRoot = n.mRight;
               n.setParent(null);
            }
         }
         else if (n.mParent.mLeft == n) {
            if (n.mLeft != null) {
               n.mParent.setLeft(n.mLeft);
               n.mLeft.setParent(n.mParent);
            }
            else {
               n.mParent.setLeft(n.mRight);
               n.mRight.setParent(n.mParent);
            }
         }
         else {
            if (n.mRight != null) {
               n.mParent.setRight(n.mRight);
               n.mRight.setParent(n.mParent);
            }
            else {
               n.mParent.setRight(n.mLeft);
               n.mLeft.setParent(n.mParent);
            }
         }
         mCount--;
      }
      else {
         Node newNode = new Node();
         newNode = findMaximum(n.mLeft);
         n.setKey(newNode.mKey);
         removeNode(newNode);
      }
   }
   
   public void clear() {
      mCount = 0;
   }
   
   public boolean find(int key) {
      return findKey(key, mRoot) != null; 
   }
   
   public int getCount() {
      return mCount;
   }
   
   public int getHeight() {
      return getHeight(mRoot);
   }
   
   //Helper method for getHeight()
   private int getHeight(Node n) {
      if (n == null) {
         return -1;
      }
      
      if (n.mLeft == null && n.mRight == null) {
         return 0;
      }
      
      //Find the height of the current subtree with root n 
      //in terms of the height of its own subtrees.
      int leftSubtreeHeight = getHeight(n.mLeft) + 1;
      int rightSubtreeHeight = getHeight(n.mRight) + 1;
      
      //Whichever is bigger is the height of the tree
      return Math.max(leftSubtreeHeight, rightSubtreeHeight);

      //Thank you, Neal. :)
   }
   
   public int sumOfAllFears() {
      return sumOfAllFears(mRoot);
   }
   
   private int sumOfAllFears(Node n) {
      // This should only probably hopefully happen if tree is empty
      if (n == null) { 
         return 0;
      }
      
      return n.mKey + sumOfAllFears(n.mLeft) + sumOfAllFears(n.mRight);
      
   }
   
   public int theGreatPath() {
      return theGreatPath(mRoot);
   }
   
   private int theGreatPath(Node n) {
      if (n == null) {
         return 0;
      }
      
      if (n.mLeft == null && n.mRight == null) {
         return n.mKey;
      }
      
      return n.mKey + Math.max(theGreatPath(n.mLeft), theGreatPath(n.mRight));
   }
   
   
   public void printAll() {
      printInOrder(mRoot);
      System.out.println("");
   }
   
   //NetBeans says you have redundant type arguments 
   // in your new expression, bro.
   //NetBeans says you should use a diamond operator instead, bro.
   public void printTreeStructure() {
      java.util.LinkedList<Node> list = new java.util.LinkedList<Node>();
      java.util.LinkedList<Integer> spaces = 
       new java.util.LinkedList<Integer>();
      list.add(mRoot);
      spaces.add(0);

      while (!list.isEmpty()) {
         Node n = list.removeLast();
         int s = spaces.removeLast();
        
         for (int i = 0; i < s; i++) {
            System.out.print(" ");
         }
         if (n != null)
            System.out.println(n.mKey);
         else 
            System.out.println("-");
         
         if (n != null && (n.mRight != null || n.mLeft != null)) {
            list.addLast(n.mRight);
            spaces.addLast(s + 3);
            list.addLast(n.mLeft);
            spaces.addLast(s + 3);
         }
      }
   }
   
   public static void main(String[] args) {
      TreeSet fun = new TreeSet();
      
      //int sum = 0;
      int rando = 0;
      System.out.println("Adding rando nums...");
      for (int i = 0; i < 10; i++) {
         rando = (int) (Math.random()*100);
         fun.add(rando);
         //sum += rando;
         //System.out.print(rando + " ");
      }
      
      fun.printAll();
      fun.printTreeStructure();
      //System.out.println("This is the sum of numbers added: " + sum);
      
      System.out.println("\nHere is the result of the recursive method:");
      System.out.println(fun.theGreatPath());
      
   }
}
