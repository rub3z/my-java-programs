package arraylist;

/**
 *
 * @author Rub3z
 */
public class ArrayList {

   /**
    * @param args the command line arguments
    */
   private int[] mints = new int[0];
   private int mCount = 0;
   
   
   // Private methods
   private void resize() {
      if (mCount == 0) {
         mints = new int[1];
      }
      else if (mCount == 1) {
         int[] nationalParksCopy = new int[2];
         System.arraycopy(mints, 0, nationalParksCopy, 0, mCount);
         mints = nationalParksCopy;
      }
      else if (mCount == mints.length) {
         int[] nationalParksCopy = 
          new int[mCount + (mCount / 2)];
         System.arraycopy(mints, 0, nationalParksCopy, 0, mCount);
         mints = nationalParksCopy;
      }
   }
   
   private void shiftRight(int index) {
      if (mCount > 0) {
         for (int i = mCount; i > index ; i--) {
            mints[i] = mints[i - 1];
         }
      }
   }
   
   private void shiftLeft(int index) {
      for (int i = index; i < mCount - 1 || i < mints.length - 1; i++) {
         mints[i] = mints[i + 1];
      }
   }
   
   
   // Public methods
   public void addFirst(int data) {
      resize();
      
      shiftRight(0);
      mints[0] = data;
      mCount++;
   }
   
   public void addLast(int data) {
      resize();

      mints[mCount] = data;
      mCount++;
   }
   
   public void insert(int index, int data) {
      resize();

      shiftRight(index);
      mints[index] = data;
      mCount++;
   }
   
   public int removeFirst() {
      if (mCount == 0) {
         throw new RuntimeException("It's empty, bro");
      }
      
      int toBeRemoved = mints[0];
      shiftLeft(0);
      mCount--;
      return toBeRemoved;
   }
   
   public int removeLast() { 
      if (mCount == 0) {
         throw new RuntimeException("It's empty, bro");
      }
      
      shiftLeft(mCount - 1);
      mCount--;
      return mints[mCount];
   }
   
   public int removeAt(int index) { 
      if (mCount == 0) {
         throw new RuntimeException("It's empty, bro");
      }
      if (index == 0) {
         return removeFirst();
      }
      
      if (index == mCount - 1) {
         return removeLast();
      }
      else {
         int tobeRemoved = get(index);
         shiftLeft(index);
         mCount--;
         return tobeRemoved;
      }
   }
   
   public void remove(int data) { 
      int dataIndex = indexOf(data);
      if (dataIndex == -1) {
         throw new RuntimeException("The data isn't in here, bro");
      }
      
      removeAt(dataIndex);
   }
   
   public int get(int index) { 
      if (index < 0 || index >= mCount) {
         throw new RuntimeException("Index is OOB.");
      }
      
      return mints[index];
   }
   
   public void clear() { 
      mCount = 0;
   }
   
   private int indexOf(int data, int startIndex) {
      if (startIndex == mCount) {
         return -1;
      }
      
      if (mints[startIndex] == data) {
         return startIndex;
      }
      return indexOf(data, startIndex + 1);
   }
   
   public int indexOf(int data) {
      return indexOf(data, 0);
   }
   
   
   public void printAll() { 
      if (mCount != 0) {
         for (int i = 0; i < mCount; i++) {
               System.out.println(mints[i]);
         }
      }
   }
   
   public int getCount() {
      return mCount;
   }
   
   // The following method was used for debugging the add and remove
   // methods as necessary to make sure that they behaved properly.
   private int getArrayLength() {
      return mints.length;
   }
}
