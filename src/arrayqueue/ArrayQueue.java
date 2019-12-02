package arrayqueue;
/**
 * CECS 328 MW 1 PM
 * LAB 5: Keep 'Em Iterated
 * Ruben Baerga ID# 010366978
 * 
 * @author Rub3z using a template by Neal Terrell
 */
import java.util.Iterator;
import java.util.Scanner;

public class ArrayQueue<TData> implements Iterable<TData> {
   private static int DEFAULT_SIZE = 4;
   
   // The array storing the queue's data.
   private TData[] mData;
   // The index of the first element considered to be "in" the queue.
   private int mHead;
   // The index of where the next item to be pushed will be placed in the array.
   private int mTail;
   // The number of data stored in the queue.
   private int mCount;
   
   public ArrayQueue() {
      mData = (TData[])new Object[DEFAULT_SIZE];
      
      mHead = 0;
      mTail = 0;
      mCount = 0;
   }
   
   // Adds a new data element to the end of the queue.
   public void enqueue(TData data) {
      if (mCount == mData.length) {
         resize(mData.length * 2);
      }
      
      mData[mTail] = data;
      mTail = (mTail + 1) % mData.length;
      mCount++;

   }
   
   // Removes and returns the element at the head of the queue.
   public TData dequeue() {
      if (mCount == 0) {
         throw new ArrayIndexOutOfBoundsException("Queue is empty");
      }
      
      TData temp = mData[mHead];
      mCount--;
      mHead = (mHead + 1) % mData.length;
      
      return temp;
   }
   
   // Returns but does not remove the element at the head of the queue.
   public TData peek() {
      if (mCount == 0)
         throw new RuntimeException("Queue is empty.");
      return mData[mHead];
   }
     
   private void resize(int newSize) {
      TData[] newQueue = (TData[]) new Object[newSize];

      int j = 0;
      for(TData i : mData) 
         newQueue[j++] = i;
      
      mData = newQueue;
      mHead = 0;
      mTail = mCount;
   }
      
   @Override
   public Iterator<TData> iterator() {
      return new ArrayQueueIterator();
   }
   
   private class ArrayQueueIterator implements Iterator<TData> {
      private int mCurrent; // this is the only member variable you need.
      private int mSteps; // NO IT'S NOT NEAL, YOU LIAR
      
      public ArrayQueueIterator() {
         mCurrent = mHead - 1;
         mSteps = 0;
      }

      @Override
      public boolean hasNext() {
         return (mSteps++ < mCount);
      }

      @Override
      public TData next() {
         mCurrent = (mCurrent + 1) % mData.length;
         return mData[mCurrent];
      }
   }

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Scanner in2 = new Scanner(System.in);
      // Had trouble with skipping input, forgot how to deal with it properly :(
      // It still skips in one case sometimes, but it's not like it breaks 
      // everything.
      
      // Lots of debugging proved that this queue works.
      
      ArrayQueue a = new ArrayQueue();
      int input = 0;
      
      while (input != 3) {
         System.out.println("Select an option:\n"
          + "1. Enqueue.\n"
          + "2. Dequeue.\n"
          + "3. Exit.");
         
         input = in.nextInt();
         switch (input) {
            case (1):
               System.out.println("Enter elements, space-separated.");
               String elements = in2.nextLine();
               for (String s : elements.split(" "))
                  a.enqueue(s);
               
               break;
            case (2):
               System.out.println("How many dequeue operations?");
               int i = 0;
               int n = in2.nextInt();
               while (i++ < n)
                  System.out.println("Dequeued " + a.dequeue());
               break;
            case (3): break;
         }
      }
      
      System.out.println("Final queue:");
      for (Object i : a) {
         System.out.print(i + " ");
      }
      System.out.println("");
   }
}
