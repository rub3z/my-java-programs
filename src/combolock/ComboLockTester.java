package combolock;

/**
 *
 * @author Rub3z
 */
public class ComboLockTester {
   
   public static void main(String[] args) {
      
      // Milestone 2 Test 1: Turn to the correct numbers.
      ComboLock t1 = new ComboLock(20, 30, 10);
      t1.turnRight(20);
      t1.testTurnSequence();
      t1.turnLeft(10);
      t1.testTurnSequence();
      t1.turnRight(20);
      t1.testTurnSequence();
      System.out.println("Open test lock 1: " + t1.open() + "\n");
      
      // Test 2: Turn RIGHT to each correct number.
      ComboLock t2 = new ComboLock(10, 20, 30);
      t2.turnRight(30);
      t2.testTurnSequence();
      t2.turnRight(30);
      t2.testTurnSequence();
      t2.turnRight(30);
      t2.testTurnSequence();
      System.out.println("Open test lock 2: " + t2.open() + "\n");
      
      // Test 3: Right to correct number, then left and right to wrong numbers.
      ComboLock t3 = new ComboLock(5, 9, 39);
      t3.turnRight(35);
      t3.testTurnSequence();
      t3.turnLeft(10);
      t3.testTurnSequence();
      t3.turnRight(20);
      t3.testTurnSequence();
      System.out.println("Open test lock 3: " + t3.open() + "\n");
      
      // Test 4: Right and left to wrong numbers, then right to correct one.
      ComboLock t4 = new ComboLock(7, 6, 5);
      t4.turnRight(20);
      t4.testTurnSequence();
      t4.turnLeft(10);
      t4.testTurnSequence();
      t4.turnRight(25);
      t4.testTurnSequence();
      System.out.println("Open test lock 4: " + t4.open() + "\n");
      
      // Test 5: Enter correct sequence; then call reset() before open().
      ComboLock t5 = new ComboLock(1, 33, 7);
      t5.turnRight(39);
      t5.testTurnSequence();
      t5.turnLeft(32);
      t5.testTurnSequence();
      t5.turnRight(26);
      t5.testTurnSequence();
      t5.reset();
      t5.testTurnSequence();
      System.out.println("Open test lock 5: " + t5.open() + "\n");
   }
   
}
