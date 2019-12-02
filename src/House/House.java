//CECS 277 Project 4: 
// Mystery House
//By Ruben Baerga ID #010366978

package House;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class House extends Arepl {
   
   /** TABLE OF CONTENTS.
    * - Constants
    * - Slots
    * 
    * Inner classes
    * - Room
    * - Player
    * - ZomDuck
    * - MedKit
    * 
    * CTOR for House, public
    * 
    * The Room Cycle Generation MDs:
    * - shuffleRooms() private void 
    *    // Make room order random.
    * - generateExitCycle() private String 
    *    // Create a base-six number string.
    * - generateRoomCycle(String connections) private void 
    *    // Link all the rooms together.
    * - generateRoomCycle(String connections, int cycleSize) private void 
    *    // OVERLOAD: Create a smaller cycle of rooms. 
    * 
    * - displayRooms(Room[] showRooms) private void //Display all room exits.
    * - sortRooms() private void // Bubble sort by room number.
    * - fixRoomExits() private void // Fix rooms with one exit.
    * - checkZombiePresence() private boolean // Check for nearby zombies.
    * 
    */
   
   //------------------------------------------------------ Constants ---
   final int MIN_ROOMS = 10;
   final int MAX_ADDITIONAL_ROOMS = 11;
   final int MAX_ROOM_EXITS = 4;
   final int MIN_ROOM_EXITS = 2;
   final int DRAWING_ROOM = 0;
   final int OUTSIDE = 1;
   final int ZOM_START_1 = 2;
   final int ZOM_START_2 = 3;
   final int ZOM_START_3 = 4;
   final int MEDKIT_ROOM = 5;
   
   //------------------------------------------------------ Slots ---
   private Random mRand;
   private Room[] mRooms;
   private int mNumRooms;
   private Player mPlayer;
   private ZomDuck mHuey;
   private ZomDuck mDewey;
   private ZomDuck mLouie;
   private MedKit mMeds;
   private boolean mGameStart;
   private String mUserInput;
   private char mUserInputChar;
   private int mUserChoice;
   private ArrayList<String> mRoomNameList;

   @Override
   public void hello() {
      System.out.println("Greetings, player. Welcome to... \n\n"
       + "THE QUACKING DEAD: THE QUACKENING!!!! (working title)\n\n");
      System.out.println(
         "Within these walls there roams indescribably terrible web-footed "
       + "monstrosities...\n"
       + "Though groaning and otherwise still as death; the walls and doors"
       + " belie the true \nnature of some incomprehensible force that "
       + "transforms the mansion's walls in \nimperceptible ways that will "
       + "nonetheless make themselves apparent once you open a door...\n\n");
      
      System.out.println("*ahem*\n Anyway, to exit a room you can press a key "
       + "then hit enter to go through the\ncorresponding exit if there is "
       + "one: (n)orth, (s)outh, (e)ast, (w)est, (u)p and (d)own.\nYeah, for "
       + "some reason there's a bunch of trapdoors in this house.\nAvoid the "
       + "horrible monsters, as one too many scrapes with them will surely "
       + "spell your doom. \nGood Luck.\n\n"
       + "You've awoken in the Drawing Room.");
   }

   @Override
   public void setup() {
      
      // Give HALF of the rooms an exit or two, with a chance of
      // overwriting existing exits.
      // See the README.txt for more information on how this
      // algorithm works.
      for (int i = 0; i < MIN_ROOM_EXITS; i++) {
         shuffleRooms();
         generateRoomCycle(generateExitCycle(), mNumRooms / 2);
      }
      
      // Give each room two exits, with a chance
      // of overwriting an existing exit.
      // See the README.txt for more information on how this
      // algorithm works.
      for (int i = 0; i < MIN_ROOM_EXITS; i++) {
         shuffleRooms();
         generateRoomCycle(generateExitCycle());
      }
      
      sortRooms();
      fixRoomExits();
      
      mPlayer = new Player(mRooms[DRAWING_ROOM], 2);
      mHuey = new ZomDuck(mRooms[ZOM_START_1], 
       "Huey the Horrific Haunted Waterfowl");
      mDewey = new ZomDuck(mRooms[ZOM_START_2], 
       "Dewey the Disgusting Dead Duckling");
      mLouie = new ZomDuck(mRooms[ZOM_START_3], 
       "Louie the Loathsome Long-Billed Lich");
      // Just FYI, "Dewey" is the correct spelling.
      mMeds = new MedKit(MEDKIT_ROOM);
      
   }

   @Override
   public void listen() {
      if (!mGameStart)
         System.out.println("You've entered the " 
          + mPlayer.getCurrentRoomName() + ".");
      mGameStart = false;
      
      int doors = mPlayer.getCurrentRoom().getDoorCount();
      
      if (doors == 1 && doors != 0)
         System.out.print("There is a door to the ");
      else if (doors > 1)
         System.out.print("There are doors to the ");
      
      for (int i = 0; i < 4; i++)
         if (null != mPlayer.getCurrentRoom().adjacentRooms[i]) {
            switch (i) {
               case (0):
                  System.out.print("north");
                  break;
               case (1):
                  System.out.print("south");
                  break;
               case (2):
                  System.out.print("east");
                  break;
               case (3):
                  System.out.print("west");
                  break;
            }
            
            switch (doors) {
               case (4):
                  System.out.print(", ");
                  break;
               case (3):
                  System.out.print(", ");
                  break;
               case (2):
                  System.out.print(" and ");
                  break;
               case (1):
                  System.out.println(".");
               default:
                  break;
            }
            
            doors--;
         }
      
      if (null != mPlayer.getCurrentRoom().adjacentRooms[4])
         System.out.println("There is a trapdoor in the ceiling.");
      if (null != mPlayer.getCurrentRoom().adjacentRooms[5])
         System.out.println("There is a trapdoor in the floor.");
      
      System.out.println("");
      
      analyzePlayerRoom();
      
      if (checkZombiePresence(mHuey) || 
          checkZombiePresence(mDewey) || 
          checkZombiePresence(mLouie))
         System.out.println("The moist smacking of oily damp "
          + "feathers nearby sends chills down your spine.\n");
      
      System.out.println("Choose an exit... ");
      
      validateInput();
      
   }

   @Override
   public void respond() {
      mPlayer.move();
      
      mHuey.move();
      mDewey.move();
      mLouie.move();
   }

   @Override
   public boolean endChk() {
      if (mPlayer.getCurrentRoomNum() == OUTSIDE) {
         System.out.println("!!!!!CONGLATURATION!!!!! \n\n"
          + "You've escaped the walls of this forsaken mansion! "
          + "You're free!\n");
         
         System.out.println("Returning to main menu...\n");
         return false;
      }
      else if (mPlayer.getCurrentHealth() <= 0) {
         System.out.println("You've succumbed to too many ugly wounds from "
          + "these monsters, and now you\ncould be doomed to recursively "
          + "shuffle within the labyrinthine walls\nof this house for an "
          + "interminable time, never to be known again...\n");
         
         System.out.println("Returning to main menu...\n");
         return false;
      }
      else
         return true;
   }
   
   //------------------------------------------------------ Room ---
   // Has a number, a name and a set of adjacent rooms.
   public class Room {
      private int roomNum;
      private String mName;
      Room[] adjacentRooms = new Room[6];

      Room(int num) {
         roomNum = num;
         mName = "";
         
      }
      
      // This counts the number of cardinal-direction exits... hence doors.
      public int getDoorCount() {
         int count = 0;
         for (int i = 0; i < 4; i++)
            if (null != adjacentRooms[i])
               count++;
         
         return count;
      }

      public int getRoomNum() {
         return roomNum;
      }

      public void setRoomNum(int roomNum) {
         this.roomNum = roomNum;
      }
      
      public String getRoomName() {
         return mName;
      }

      public void setAdjacentRoom(Room adjacentRoom, int exit) {
         adjacentRooms[exit] = adjacentRoom;
      } 

      // Method to display this room's exits in a row
      public void displayExits() {
         for (int i = 0; i < adjacentRooms.length; i++) {

            if (null == adjacentRooms[i])
               System.out.print("X ");
            else
               System.out.print(adjacentRooms[i].roomNum + " ");
         }
         System.out.println("");
      }
   }
   
   //------------------------------------------------------ Player ---
   // Has a current room, health, and MDs to alter them
   public class Player {
      private Room mCurrentRoom;
      private int mHealth;
      

      public Player(Room startRoom, int startHealth) {
         mCurrentRoom = startRoom;
         mHealth = startHealth;
      }
      
      public Room getCurrentRoom() {
         return mCurrentRoom;
      }
      
      public int getCurrentRoomNum() {
         return mCurrentRoom.getRoomNum();
      }
      
      public String getCurrentRoomName() {
         return mCurrentRoom.getRoomName();
      }
      
      public int getCurrentHealth() {
         return mHealth;
      }

      public void move() {
         mCurrentRoom = mCurrentRoom.adjacentRooms[mUserChoice];
      }

      public void bitten () {
         System.out.println("The hideous waterborne wretch clasps its blood-"
          + "soaked\nbill into your flesh as you fight it off!!!\n");
         mHealth--;
      }

      public void healthGet () {
         System.out.println("With this, you could survive another bite...\n");
         mHealth++;
         mMeds.medKitFound();
      }

   }
   
   //------------------------------------------------------ ZomDuck ---
   // Has a current room and fierce quacking attack
   public class ZomDuck {
      private Room mCurrentRoom;
      private String mName;
      private boolean mZedIsDed;

      public ZomDuck (Room startRoom, String name) {
         mCurrentRoom = startRoom;
         mName = name;
         mZedIsDed = false;
      }

      public Room getCurrentRoom() {
         return mCurrentRoom;
      }
      
      public int getCurrentRoomNum() {
         return mCurrentRoom.getRoomNum();
      }
      
      public String getName() {
         return mName;
      }
      
      public void move() {
         if (!mZedIsDed) {
            if (mPlayer.getCurrentRoomNum() == getCurrentRoomNum()) {
               System.out.print("As you enter the room, ");
               quackAttack();
               System.out.println("The monster howls and scurries away!!!\n");
            }

            for (;;) {
               int randExit = mRand.nextInt(6);
               if (null != getCurrentRoom().adjacentRooms[randExit]) {
                  mCurrentRoom = getCurrentRoom().adjacentRooms[randExit];
                  break;
               }
            }

            if (getCurrentRoomNum() == OUTSIDE)
               mZedIsDed = true;
            
            if (mPlayer.getCurrentRoomNum() == getCurrentRoomNum()
             && !mZedIsDed) {
               System.out.print("A door opens and ");
               quackAttack();
            }
         }
      }
      
      public void quackAttack() {
         System.out.println(getName() + " lunges towards \nyou in a deluge of"
          + " blood-curdling quacks and filthy feathers!\n");
         if (mRand.nextInt() % 4 == 0) 
            mPlayer.bitten();
         else
            System.out.println("You just manage to escape its furious attack!"
             + "\n");
         
      }
   }
   
   //------------------------------------------------------ MedKit ---
   public class MedKit {
      private int mStartRoom;
      private boolean found;

      public MedKit(int startRoom) {
         mStartRoom = startRoom;
      }
      
      public void medKitFound() {
         found = true;
      }
   }


   //------------------------------------------------------ CTOR ---
   public House() {
      mGameStart = true;
      mRand = new Random();
      mNumRooms = mRand.nextInt(MAX_ADDITIONAL_ROOMS) + MIN_ROOMS;
      mRooms = new Room[mNumRooms];
      
      mRoomNameList = new ArrayList();
      
      // Creating a list of room names has to be done the old-fashioned way.
      mRoomNameList.add("Master Bedroom");
      mRoomNameList.add("Master Bathroom");
      mRoomNameList.add("Foyer");
      mRoomNameList.add("Sitting Room");
      mRoomNameList.add("Family Room");
      mRoomNameList.add("Kitchen");
      mRoomNameList.add("Pantry");
      mRoomNameList.add("Wine Closet");
      mRoomNameList.add("Cellar");
      mRoomNameList.add("Attic");
      mRoomNameList.add("Playroom");
      mRoomNameList.add("Trophy Room");
      mRoomNameList.add("Nursery");
      mRoomNameList.add("Main Hall");
      mRoomNameList.add("Secret Passage");
      mRoomNameList.add("Crawlspace");
      mRoomNameList.add("Butler Room");
      mRoomNameList.add("Arboretum");
      mRoomNameList.add("Guest Bedroom");
      mRoomNameList.add("Guest Bathroom");
      mRoomNameList.add("Ballet Room");
      mRoomNameList.add("Roof");
      mRoomNameList.add("Balcony");
      mRoomNameList.add("Stairwell");
      mRoomNameList.add("Hidden Room");
      mRoomNameList.add("Observatory");
      mRoomNameList.add("Dining Room");
      mRoomNameList.add("Office");
      mRoomNameList.add("Smoking Room");
      mRoomNameList.add("Library");
      mRoomNameList.add("Furnace");
      mRoomNameList.add("Electrical Room");
      mRoomNameList.add("Scrooge Saferoom");
      mRoomNameList.add("Dewey Dormitory");
      mRoomNameList.add("Louie Laboratory");
      mRoomNameList.add("Huey Hideaway");
      mRoomNameList.add("Storage Room");
      mRoomNameList.add("Lounge");
      mRoomNameList.add("Billiards Hall");
      mRoomNameList.add("Lurid and Luxurious Bespoke Gold-Trimmed Silk-Lined "
       + "Bathroom \nwith an Exceptionally Gorgeous Diamond-and-Ruby-Encrusted "
       + "Marble Toilet \nand Deep Crimson Velvet-Seated Bidet and Pure "
       + "Limestone Basin Sink \nas well as an Excruciatingly Tasteful Set of "
       + "Shower Curtains for an \nOnyx-and-Molten-Lava Tiled Jetstream Tub "
       + "with Damascus Steel Showerheads...\n\n"
       + "...with user-defined temperature control.\n"
       + "And a fully stocked cupboard of Charmin UltraSoft toilet paper");
      //I just HAD to do this.
      
      for(int i = 0; i < mNumRooms; i++) {
         mRooms[i] = new Room(i);
      }
      
      mRooms[0].mName = "Drawing Room";
      mRooms[1].mName = "Outside";
      
      for(int i = 2; i < mNumRooms; i++) {
         int randomName = mRand.nextInt(mRoomNameList.size());
         mRooms[i].mName = mRoomNameList.get(randomName);
         mRoomNameList.remove(randomName);
      }
   }
   
   /********** THE ROOM CYCLE GENERATION AND GRAPH ALGORITHM *********
    * The following three methods (4 if you count the overload on the 
    * generateRoomCycle MD) are used to create the directional graph that
    * describes the layout of the House. 
    * Instead of being crammed into one larger method - a possibility -
    * they're capable of functioning independently of one another, altering
    * or designing the mRooms or their exits in a variety of ways.
    * 
    * See the README.txt for more info on how this algorithm works.
    * 
    */
   
   
   //------------------------------------------------------ shuffleRooms ---
   // Make room order random.
   private void shuffleRooms() {
      int randomIndex;
      Room temp;
      for (int i = mNumRooms - 1; i > 0; i--) {
         randomIndex = mRand.nextInt(i);
         temp = mRooms[i];
         mRooms[i] = mRooms[randomIndex];
         mRooms[randomIndex] = temp;
      }
   }

   //------------------------------------------------------ generateExitCycle -
   // Create a large base-six number string.
   private String generateExitCycle() {
      long x = (long)(mRand.nextDouble() * Long.MAX_VALUE);
      // This is for the sake of certainty; as well as the possibility
      // of wanting extra rooms.
      long y = (long)(mRand.nextDouble() * Long.MAX_VALUE);

      String z = "";

      z = Long.toString(x, 6) + Long.toString(y, 6);

      return z;
   }

   //------------------------------------------------------ generateRoomCycle -
   // Link all the rooms together.
   private void generateRoomCycle(String connections) {
      generateRoomCycle(connections, mNumRooms);
   }
   
   //------------------------------------------------------ generateRoomCycle -
   // OVERLOAD: Create a smaller cycle of rooms.
   private void generateRoomCycle(String connections, int cycleSize) {
      for (int i = 0; i < cycleSize; i++) {
         if(i == cycleSize - 1)
            mRooms[i].setAdjacentRoom(mRooms[0],
                                       connections.charAt(i) - '0');
         else
            mRooms[i].setAdjacentRoom(mRooms[i + 1],
                                       connections.charAt(i) - '0');
      }
   }

   //------------------------------------------------------ displayRooms -
   // Display all room exits.
   private void displayRooms(Room[] showRooms) {
      System.out.println("Displaying exits for all rooms...");
      System.out.println("Rn: N S E W U D");
      for (int i = 0; i < mRooms.length ; i++) {
         System.out.print(showRooms[i].getRoomNum() + "   ");
         showRooms[i].displayExits();
      }
   }
   
   //------------------------------------------------------ displayRoomNames -
   // Display all room names.
   private void displayRoomNames() {
      System.out.println("Displaying all room names...");
      for(Room i : mRooms)
         System.out.println(i.getRoomName());
   }
   
   //------------------------------------------------------ sortRooms ---
   // Bubble sort by room numbers.
   //
   // This is for testing and sanity: 
   // It can be used before making a cycle to connect the rooms in order.
   // Mostly though, it should be used after the house is all set and the
   // order of the mRooms array no longer matters... 
   // it will make the displayRooms MD look nice. :)
   private void sortRooms () {
      Room temp;
      boolean swapped;
      for (int i = 0; i < mNumRooms - 1; i++) {
         swapped = false;
         for (int j = 0; j < mNumRooms - 1; j++) {
            if (mRooms[j].getRoomNum() > mRooms[j + 1].getRoomNum()) {
               temp = mRooms[j];
               mRooms[j] = mRooms[j + 1];
               mRooms[j + 1] = temp;
               swapped = true;
            }
         }
         if (swapped == false)
            break;
      }
   }
   
   //------------------------------------------------------ fixRoomExits ---
   // Fix rooms with one exit.
   private void fixRoomExits() {
      int count = 0;
      boolean firstExitOpen = false;
      for (Room i : mRooms) {
         if (null == i.adjacentRooms[0])
            firstExitOpen = true;
         for (Room j : i.adjacentRooms) 
            if (null == j)
               count++;
         
         if (count > 4) 
            if (firstExitOpen) i.setAdjacentRoom(mRooms[DRAWING_ROOM], 0);
            else               i.setAdjacentRoom(mRooms[DRAWING_ROOM], 1);
         
         count = 0;
         firstExitOpen = false;
      }  
   }
   
   
   //------------------------------------------------------ checkZombiePresence
   // Check for nearby zombies.
   private boolean checkZombiePresence(ZomDuck undeadDuck) {
      // Is it ded?
      if (!undeadDuck.mZedIsDed)
         for (Room i : undeadDuck.getCurrentRoom().adjacentRooms)
            if (null != i)
               for (Room j : mPlayer.getCurrentRoom().adjacentRooms)
                  if (null != j)
                     // Shared exits
                     if (j.getRoomNum() == i.getRoomNum())
                        return true;
                     // Player can enter duck room
                     else if (j.getRoomNum() == undeadDuck.getCurrentRoomNum())
                        return true;
                     // Duck can enter player room
                     else if (i.getRoomNum() == mPlayer.getCurrentRoomNum())
                        return true;

      return false;
   }
   
   //------------------------------------------------------ validateInput ---
   // Validate mUserInput.
   private void validateInput() {
      Scanner in = new Scanner(System.in);
      boolean hasMoreThanLetters = false;
      int letterCount = 0;
      mUserInputChar = ' ';
      
      while (mUserInputChar == ' ') {
         
         mUserInput = in.nextLine().toLowerCase();
         
         //Checking if the input is...
         for (char i : mUserInput.toCharArray()) {
            if (Character.isLetter(i)) {  //1. A letter
               mUserInputChar = i;
               letterCount++;             //2. Only one letter and...
            }
            if (!Character.isLetter(i) && !Character.isWhitespace(i)) {
               hasMoreThanLetters = true; //3. Not a number or other character
            }
         }

         //Tell 'em
         if (letterCount > 1 || hasMoreThanLetters) {
            System.out.println("Please enter one letter, and no digits or "
             + "special characters.");
            mUserInputChar = ' ';
         }
         else if (mUserInputChar != 'n' && mUserInputChar != 's' && mUserInputChar != 'e'
          && mUserInputChar != 'w' && mUserInputChar != 'u' && mUserInputChar != 'd') {
            System.out.println("Please enter a valid letter.");
            mUserInputChar = ' ';
         }
         
         switch (mUserInputChar) {
            case ('n'):
               mUserChoice = 0;
               break;
            case ('s'):
               mUserChoice = 1;
               break;
            case ('e'):
               mUserChoice = 2;
               break;
            case ('w'):
               mUserChoice = 3;
               break;
            case ('u'):
               mUserChoice = 4;
               break;
            case ('d'):
               mUserChoice = 5;
               break;
            default:
               break;
         }
         
         //Checking for a valid room selection should be done here,
         //before the Player.move() method is called.
         if (null == mPlayer.getCurrentRoom().adjacentRooms[mUserChoice]
          && mUserInputChar != ' ') {
            System.out.println("Please enter a valid direction.");
            mUserInputChar = ' ';
         }
         
         hasMoreThanLetters = false;
         letterCount = 0;
      }
      
      //Because the influx of new text can be disorienting and tough to 
      //separate from the existing text above, I've inserted all these 
      //newline characters to separate them, removing the existing text from
      //view. This also reinforces the idea that the player has just entered 
      //a new room.
      System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
       + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      
      //This line further communicates the player's traversal into a new room
      //and lets them know that they've taken their desired direction.
      switch(mUserChoice) {
         case (0):
            System.out.println("You exit to the north.\n");
            break;
         case (1):
            System.out.println("You exit to the south.\n");
            break;
         case (2):
            System.out.println("You exit to the east.\n");
            break;
         case (3):
            System.out.println("You exit to the west.\n");
            break;
         case (4):
            System.out.println("You clamber up through the ceiling.\n");
            break;
         case (5):
            System.out.println("You slide down through the trapdoor.\n");
            break;
         
      }
      
   }
   
   //------------------------------------------------------ validateInput ---
   // Tell the user what's in their room.
   private void analyzePlayerRoom() {
      if (mPlayer.getCurrentRoomNum() == MEDKIT_ROOM 
       && !mMeds.found) {
         System.out.println("You've found a medkit on a nearby shelf!");
         mPlayer.healthGet();
      }
      if (mPlayer.getCurrentRoomNum() == mHuey.getCurrentRoomNum())
         System.out.println(mHuey.getName() 
          + " howls a harrowing honk at you!!!\n");
      if (mPlayer.getCurrentRoomNum() == mDewey.getCurrentRoomNum())
         System.out.println(mDewey.getName() + " drags his dendritic dregs "
          + "towards you!!!\n");
      if (mPlayer.getCurrentRoomNum() == mLouie.getCurrentRoomNum())
         System.out.println(mLouie.getName() + " loosens his yawning beak and "
          + "low-hanging wings!!!\n");
   }
   
   //------------------------------------------------------ main ---
   public static void main(String[] args) {
      // TODO code application logic here
      
      //Scanner in = new Scanner(System.in);
      
      IRepl test = new House();
      test.repl();
      
   }
}
