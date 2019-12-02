package linkedlist;

public class LinkedListTest {
   public static void main(String[] args) {
      LinkedList list = new LinkedList();
      System.out.println("Add numbers 1 through 5:");
      list.addFirst(2);
      list.addFirst(1);
      list.addLast(3);
      list.addLast(4);
      list.addLast(5);
      System.out.println("List is now: ");
      list.printAll();
      
      System.out.println();
      System.out.println("Remove item @ index 1.");
      System.out.println("Removing " + list.removeAt(1));
      System.out.println("List is now: ");
      list.printAll();
      
      System.out.println();
      System.out.println("Insert 1 at index 2.");
      list.insert(2, 1); // index first, then data
      System.out.println("List is now: ");
      list.printAll();
      
      System.out.println();
      System.out.println("Remove the first item.");
      System.out.println("Removing " + list.removeFirst());
      list.printAll();
      
      System.out.println();
      System.out.println("Insert a copy of the item at index 1 into the index "
       + "specified by the first item in the list:");
      list.insert(list.get(0), list.get(1));
      System.out.println("List is now: ");
      list.printAll();
      
      System.out.println();
      System.out.println("Add two more than half the sum of the existing list "
       + "to the end:");
      int sum = 0;
      for (int i = 0; i < list.getCount(); i++)
         sum += list.get(i);
      list.addLast(sum / 2 + 2);
      list.printAll();
      
      System.out.println("Clear the list:");
      list.clear();
      list.printAll();
   }
}
