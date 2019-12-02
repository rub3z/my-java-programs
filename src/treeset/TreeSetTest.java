package treeset;


public class TreeSetTest {

   public static void main(String[] args) {
      TreeSet set = new TreeSet();
      System.out.println("Empty tree count: " + set.getCount() + 
       "; height: " + set.getHeight());
      System.out.println("Empty tree contains: ");
      set.printAll();
      
      System.out.println();
      System.out.println("Add \"longbeachstate\" to the set:");
      set.add('l');
      set.add('o');
      set.add('n');
      set.add('g');
      set.add('b');
      set.add('e');
      set.add('a');
      set.add('c');
      set.add('h');
      set.add('s');
      set.add('t');
      set.add('a');
      set.add('t');
      set.add('e');
      System.out.println("Tree count: " + set.getCount() + "; height: " +
       set.getHeight());
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println("Tree structure:");
      set.printTreeStructure();      
      System.out.println();
      
      System.out.println("Test the find method:");
      System.out.println("Contains 'h'? " + set.find('h'));
      System.out.println("Contains 'n'? " + set.find('n'));
      System.out.println("Contains 'a'? " + set.find('a'));
      System.out.println("Contains 'x'? " + set.find('x'));
      System.out.println("Contains '@'? " + set.find('@'));
      System.out.println("Contains '5'? " + set.find('5'));
      System.out.println("Tree count: " + set.getCount() + "; height: " +
       set.getHeight());
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      
      System.out.println("Test the remove method.");
      System.out.println("Remove key with no children:");
      System.out.println("Remove 'n': ");
      set.remove('n');
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      
      System.out.println("Remove key with one (left) child:");
      System.out.println("Remove 'e': " );
      set.remove('e');
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      
      System.out.println("Remove key with one (right) child:");
      System.out.println("Remove 'o': ");
      set.remove('o');
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      
      System.out.println("Remove key with two children:");
      System.out.println("Remove 'g': ");
      set.remove('g');
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      
      System.out.println("Remove root key:");
      System.out.println("Remove 'l': ");
      set.remove('l');
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
      System.out.println("Tree count: " + set.getCount() + "; height: " +
       set.getHeight());
      set.printTreeStructure();
      
      System.out.println("Remove all remaining keys");
      set.remove('b');
      set.remove('a');
      set.remove('c');
      set.remove('s');
      set.remove('h');
      
      System.out.println("Removing the LAST AND FINAL KEY:");
      set.remove('t');
      
      System.out.println("Tree count: " + set.getCount() + "; height: " +
       set.getHeight());
      System.out.println("Tree contains: ");
      set.printAll();
      System.out.println();
   }
}
