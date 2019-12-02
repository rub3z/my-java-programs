package combolock;

/**
 * This class creates a simulacrum of a combination lock, such as those 
 * commonly found in locker rooms.
 * @author Rub3z
 */
public class ComboLock {
   
   private int lockCombo1, lockCombo2, lockCombo3;
   private int lockDialPosition;
   
   private boolean oneCorrectTurn, twoCorrectTurns, threeCorrectTurns;
   private boolean reset;
   
   private final int NUMBER_OF_POSITIONS = 40;

   /**
    * This is the constructor for a ComboLock object; which initializes
    * the lock's combination as well as its default state
    * (see lockDefaultState()).
    * 
    * @param position1 The first number of the combination.
    * @param position2 The second number of the combination.
    * @param position3 The third number of the combination.
    */
   public ComboLock(int position1, int position2, int position3) {
      lockCombo1 = position1;
      lockCombo2 = position2;
      lockCombo3 = position3;
      lockDefaultState();
   }
   
   private void lockDefaultState() {
      lockDialPosition = 0;
      oneCorrectTurn = false; 
      twoCorrectTurns = false;
      threeCorrectTurns = false;
      reset = false;
   }
   
   /**
    * This is an accessor for the lock dial's current position.
    * @return lockDialPosition
    */
   public int getDialPosition() {
      return lockDialPosition;
   }
   
   /**
    * This turns the dial on the lock counter-clockwise and checks
    * that it's the correct left turn in a sequence that would open
    * the lock.
    * 
    * @param ticks The number of ticks to turn the dial.
    */
   public void turnLeft(int ticks) {
      lockDialPosition = (lockDialPosition + ticks >= NUMBER_OF_POSITIONS ?
       lockDialPosition + ticks - NUMBER_OF_POSITIONS :
       lockDialPosition + ticks);
      twoCorrectTurns = (lockDialPosition == lockCombo2 && oneCorrectTurn);
   }
   
   /**
    * This turns the dial on the lock clockwise and checks that
    * it's a correct turn in a sequence that would open the lock.
    * 
    * @param ticks The number of ticks to turn the dial.
    */
   public void turnRight(int ticks) {
      lockDialPosition = (lockDialPosition - ticks < 0 ?
       lockDialPosition - ticks + NUMBER_OF_POSITIONS :
       lockDialPosition - ticks);  
      oneCorrectTurn = (lockDialPosition == lockCombo1);
      threeCorrectTurns = (lockDialPosition == lockCombo3 
       && !oneCorrectTurn && twoCorrectTurns);
   }
   
   /**
    * This method will set the lockDialPosition to 0 as well as 
    * indicate to the open() method that the lock has been reset before 
    * the attempt.
    */
   public void reset() {
      lockDialPosition = 0;
      reset = true;
   }
      
   /**
    * This method checks that three correct turns were made to
    * indicate whether or not the lock should open or not, as well
    * as resets the lock in either case.
    * 
    * @return True if the lock opens, false otherwise.
    */
   public boolean open() {
      if (threeCorrectTurns && !reset) {
         reset();
         lockDefaultState();
         return true;
      }
      else {
         reset();
         lockDefaultState();
         return false;
      }    
   }
   
   /**
    * This method was made purely for debugging purposes,
    * used in the ComboLockTester class. 
    * It prints the truth values of each turn conditional as 
    * well as the dial's position.
    */
   public void testTurnSequence() {
      System.out.println(oneCorrectTurn + ", " + twoCorrectTurns + ", " 
       + threeCorrectTurns + ", " + getDialPosition());
   } 
}
