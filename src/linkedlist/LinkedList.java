package linkedlist;
/**
 *
 * @author Rub3z
 */
public class LinkedList {

   
   public class Node {
      private int mData;
      private Node mNext;
      private Node mPrev;
      
      public Node(int nodeData) {
         mData = nodeData;  
      }
      
      public void setMNext(Node theNextNode) {
         mNext = theNextNode;
      }
      
      public void setMPrev(Node thePreviousNode) {
         mPrev = thePreviousNode;
      }
      
      public void setMData(int newData) {
         mData = newData;
      }
   }
   
   private Node mHead;
   private int mCount;
   
   public void addFirst(int data) { 
      if (mCount == 0) {
         Node firstNode = new Node(data);
         firstNode.setMNext(firstNode);
         firstNode.setMPrev(firstNode);
         mHead = firstNode;
         mCount++;
      }
      else {
         Node newNode = new Node(data);

         newNode.setMNext(mHead);
         mHead.mPrev.setMNext(newNode);
         newNode.setMPrev(mHead.mPrev);
         mHead.setMPrev(newNode);

         mHead = newNode;

         mCount++;
      }
   }
   
   public void addLast(int data) { 
      if (mCount == 0) {
         addFirst(data);
      }
      else {
         Node newNode = new Node(data);

         newNode.setMNext(mHead);
         newNode.setMPrev(mHead.mPrev);
         mHead.mPrev.setMNext(newNode);
         mHead.setMPrev(newNode);

         mCount++;
      }
   }
   
   public void insert(int index, int data) { 
      if (index < 0 || index > mCount) {
         throw new RuntimeException("Index is OOB. D:");
      }
      
      if (index == 0) {
         addFirst(data);
         return;
      }
      
      int i = 0;
      Node tempNode = mHead;
      
      while (i < index) {
         tempNode = tempNode.mNext;
         i++;
      }
      
      Node newNode = new Node(data);

      tempNode.mPrev.setMNext(newNode);
      newNode.setMPrev(tempNode.mPrev);
      tempNode.setMPrev(newNode);
      newNode.setMNext(tempNode);
      
      mCount++;
   }
   
   public int removeFirst() { 
      if (mHead == null) {
         throw new RuntimeException("Removing from empty list. :(");
      }
      
      int removedData = mHead.mData;
      
      if (mCount == 1) {
         mHead = null;
         mCount--;
         return removedData;
      }
      
      mHead.mPrev.setMNext(mHead.mNext);
      mHead.mNext.setMPrev(mHead.mPrev);
      
      mHead = mHead.mNext;
      mCount--;
      return removedData;
   }
   
   public int removeLast() { 
      if (mHead == null) {
         throw new RuntimeException("Removing from empty list...");
      }
      
      if (mCount == 1) {
         try {
            return removeFirst();
         }
         catch (RuntimeException ohNoes) {
            System.out.println("RemoveFirst failed");
         };
      }
      int removedData = mHead.mPrev.mData;
      mHead.mPrev.mPrev.setMNext(mHead);
      mHead.setMPrev(mHead.mPrev.mPrev);
      mCount--;
      
      return removedData;
   }
   
   public int removeAt(int index) { 
      if (index < 0 || index >= mCount) {
         throw new RuntimeException("Index is OOB.");
      }
      
      if (index == 0) {
         try {
            return removeFirst();
         }
         catch (RuntimeException ohNoes) {
            System.out.println("RemoveFirst failed");
         };      
      }
      else if (index == mCount - 1) {
         try {
            return removeLast();
         }
         catch (RuntimeException ohNoes) {
            System.out.println("RemoveLast failed");
         };
      }
      //else {
         int i = 0;
         Node tempNode = mHead;

         while (i < index) {
            tempNode = tempNode.mNext;
            i++;
         }

         int removedData = tempNode.mData;
         tempNode.mNext.setMPrev(tempNode.mPrev);
         tempNode.mPrev.setMNext(tempNode.mNext);
         mCount--;

         return removedData;
      //}
   }
   
   public void remove(int data) { 
      if (mCount == 0) {
         throw new RuntimeException("Removing from empty list...");
      }
      
      int i = 0;
      Node tempNode = mHead;
      
      while (i < mCount && tempNode.mData != data) {
         tempNode = tempNode.mNext;
         i++;
      }
      
      if (i == mCount) {
         throw new RuntimeException("");
      }
      else if (i == 0) {
         removeFirst();
      }
      else if (i == mCount - 1) {
         removeLast();
      }
      else {
         tempNode.mNext.setMPrev(tempNode.mPrev);
         tempNode.mPrev.setMNext(tempNode.mNext);
      }
      mCount--;
   }
   
   public int get(int index) { 
      if (index < 0 || index >= mCount) {
         throw new RuntimeException("Index is OOB.");
      }
      
      Node tempNode = mHead;
      
      for( int i = 0; i < index ; i++) {
         tempNode = tempNode.mNext;
      }
      
      return tempNode.mData;
   }
   
   public void clear() { 
      mHead = null;
      mCount = 0;
   }
   
   public int getCount() { 
      return mCount;
   }
    
   
   public int indexOf(int data) { 
      if (mCount == 0) {
         throw new RuntimeException("Searching empty list...");
      }
      
      int i = 0;
      Node tempNode = mHead;
      
      while (i < mCount && tempNode.mData != data) {
         tempNode = tempNode.mNext;
         i++;
      }
      
      if (i == mCount) {
         return -1;
      }

      return i;
   }
   
   public void printAll() { 
      Node tempNode = mHead;
      
      for( int i = 0; i < mCount ; i++) {
         System.out.print(tempNode.mData + " ");
         tempNode = tempNode.mNext;
      }
   }
}
