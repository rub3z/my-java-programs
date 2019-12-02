package hashmap;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Rub3z, using a template by Neal Terrell
 */
// Implements the Map ADT using a hash table with open addressing and quadratic
// probing.
public class HashMap<KeyType, ValueType> {
   // The hashtable array will be an array of Entry objects. Each Entry stores
   // a key and value.
   private class Entry {
      public KeyType mKey;
      public ValueType mValue;
      
      public Entry(KeyType key, ValueType value) {
         mKey = key;
         mValue = value;
      }

      private Entry() {
         
      }
   }
   
   // This object will be used to mark NIL (a deleted entry) in the table.
   // You can use == to compare one entry against DELETED_ENTRY to see if the
   // entry has been deleted.
   private final Entry DELETED_ENTRY = new Entry();
   
   // The table itself.
   private Entry[] mTable;
   // The count of how many keys are already in the map.
   private int mCount;
   
   // Constructs an empty hashtable. The size of the table will be the smallest
   // power of 2 that is greater than or equal to the requested size.
   public HashMap(int requestedSize) {
      // The next line is a workaround for Java not liking us making an array
      // of a generic type. (Node is a generic type because it has generic
      // members.)
      
      // TODO: modify the tableSize variable so it is equal to the smallest 
      // power of 2 that is greater than or equal to requestedSize.
      int tableSize = 0;
      for (int i = 0; i < 64; i++) 
         if (Math.pow(2, i) > requestedSize) {
            tableSize = (int) Math.pow(2, i);
            break;
         }
      mTable = (Entry[])Array.newInstance(Entry.class, tableSize); 
      // mTable's entries are all null initially. null means never assigned.
   }
   
   // Inserts the given key and value into the table, assuming no entry with 
   // an equal key is present. If such an entry is present, override the entry's
   // value.
   public void insert(KeyType key, ValueType value) {
      // Every object in Java has a .hashCode() function that computes a h(k)
      // value for that object. Use that function for your hash table index
      // calculations.
      // Note that a .hashCode() can be negative, but array indices cannot.
      // Use Math.abs to make sure the index is positive.
      double load = ((double)mCount) / mTable.length;
      
      if (load >= 0.8)
         resizeTable(2 * mTable.length);
      
      if (!containsKey(key)) {
         for (int i = 0; i < mTable.length; i++) {
            if (!isEntryAtIndex(getHashIndex(key, i))) {
               mTable[getHashIndex(key, i)] = new Entry(key, value);
               break;
            }
         }
      }
      else {
         for (int i = 0; i < mTable.length; i++) {
            if (keyFoundAtIndex(key, getHashIndex(key, i))) {
               mTable[getHashIndex(key, i)] = new Entry(key, value);
               break;
            }
         }
      }
      mCount++;
   }
   
   // Returns the value associated with the given key, if it is present.
   // Returns null if the value is not present.
   public ValueType find(KeyType key) throws RuntimeException {
      if (!containsKey(key)) {
         throw new RuntimeException("Not here bro");
      }
      else {
         for (int i = 0; i < mTable.length; i++) {
            if (keyFoundAtIndex(key, getHashIndex(key, i))) {
               return mTable[getHashIndex(key, i)].mValue;
            }
         }
         throw new RuntimeException("Not here bro");
      }
   }
   
   // Removes the pair with the given key from the table.
   public void remove(KeyType key) {
      for (int i = 0; i < mTable.length; i++) {
         if (keyFoundAtIndex(key, getHashIndex(key, i)))
            mTable[getHashIndex(key, i)] = DELETED_ENTRY;
      }
      mCount--;
   }
   
   // Utility methods:
   private int getHashIndex(KeyType key) {
      // returns the index of where this key should be found in the table, 
      // WITHOUT probing.
      return Math.abs(key.hashCode() % mTable.length);
   }
   
   private int getHashIndex(KeyType key, int failures) {
      // returns the index of where this key should be found in the table, USING
      // probing with the given number of failures.
      return Math.abs((getHashIndex(key) + probe(failures)) % mTable.length);
   }
   
   private boolean isEntryAtIndex (int index) {
      return mTable[index] != null && mTable[index] != DELETED_ENTRY;
   }
   
   private boolean keyFoundAtIndex(KeyType key, int index) {
      // returns true if the hash table has an Entry at the given index, and 
      // that entry's key is .equals() to the given key.
      return isEntryAtIndex(index) && mTable[index].mKey.equals(key);
   }
   
   private static int probe(int i) {
      // implement the probing function specified in the assignment.
      return ((i * i) + i)/2;
   }
   
   private void resizeTable(int newSize) {
      // perform the (expensive) resizing of the table, by creating a new array
      // of the given size, and then inserting each non-deleted Entry in the 
      // current table into the new table.
      HashMap newCopy = new HashMap(newSize);
      for (Entry i : mTable)
         if (null != i && DELETED_ENTRY != i)
            newCopy.insert(i.mKey, i.mValue);
      
      int tableSize = newSize;
      mTable = newCopy.mTable;
      // It almost seems too easy...
   }
   
   // Return true if the given key is in the table, false otherwise.
   private boolean containsKey(KeyType key) {
      for (int i = 0; i < mTable.length; i++) 
            if (keyFoundAtIndex(key, getHashIndex(key, i))) 
               return true;
      return false;
   }
   
   // Return the number of data elements in the table.
   public int count(){
      return mCount;
   }
   
   // Return a list of all the keys in the table.
   private ArrayList<KeyType> keySet() {
      ArrayList theList = new ArrayList();
      for (int i = 0; i < mTable.length; i++) 
         if(isEntryAtIndex(i))
            theList.add(mTable[i].mKey);
      return theList;
   }
   
   /**
    * @param args the command line arguments
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {
      
      File baseball = new File("players_homeruns.csv");
      
      HashMap homeruns = new HashMap(256);
      
      Scanner read = new Scanner(baseball);
      
      String[] line;
      while(read.hasNextLine()) {
         line = read.nextLine().split(",");
         homeruns.insert(line[0], Integer.parseInt(line[1]));
      } 
      
//      These lines were used for testing.
//      ArrayList playerList = homeruns.keySet();
//      System.out.println(playerList.size());
//      
//      for (int i = 0; i < playerList.size(); i++) 
//         System.out.println(playerList.get(i));
//      System.out.println(homeruns.count());
      
      System.out.println();
      Scanner in = new Scanner(System.in);
      String playerName;
      
      do {
         System.out.println("Enter a player's name, or type \"exit\".");
         playerName = in.nextLine();
         if (!playerName.contains("exit")) {
            try {
               System.out.println(homeruns.find(playerName) + " homeruns.");
            }
            catch (RuntimeException rex) {
               System.out.println("No data found / invalid input.");
            }
         }
         if (playerName.equals("Neal Terrell"))
            System.out.println("He's pretty cool, though.");
         if (playerName.equals("Ruben Baerga"))
            System.out.println("Fun Fact: Carlos Baerga is actually "
             + "Ruben's distant cousin."); // :D
         System.out.println("");
      } while (!playerName.contains("exit"));
      System.out.println("Seeya.");
   }
}
