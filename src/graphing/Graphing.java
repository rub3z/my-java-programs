package graphing;

//CECS 277 Project 4: 
// Mystery Graphing
//By Ruben Baerga ID #010366978

import java.util.Random;
import java.util.Scanner;

public class Graphing {
   
   /** TABLE OF CONTENTS.
 - Slots
 
 Inner classes
 - Node

 CTOR for Graphing, public
 
 The Node Cycle Generation MDs:
 - shuffleNodes() private void 
    // Make room order random.

 - generateGraph() private void 
    // Create a cycle of rooms. 
 
 - displayMatrix(Node[] showRooms) private void //Display all room exits.
 - sortNodes() private void // Bubble sort by room number.
    * 
    */
   
   //------------------------------------------------------ Slots ---
   private Random mRand;
   private Node[] mGraph;
   private int mOrder;
   private int mSize;
   private String mUserInput;
   private int mUserChoice;
   private boolean bipartite;
   
   public void plr() {
      validateInput();
      respond();
   }
   
   public void respond() {
      
      mGraph = new Node[mOrder];
      
      for(int i = 0; i < mOrder; i++) {
         mGraph[i] = new Node(i);
      }
      
      generateGraph();
      
      sortNodes();
      
      displayMatrix(mGraph);
      
      paintNodes();
      
      displayNodeColors();
      
      System.out.println(bipartite);
      
   }

   //------------------------------------------------------ Node ---
   // Has a number, a color and a set of adjacent rooms.
   public class Node {
      private int roomNum;
      private char color;
      Node[] adjacentRooms;

      Node(int num) {
         roomNum = num;
         color = 'g';
         adjacentRooms = new Node[mOrder];
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
      
      public char getRoomName() {
         return color;
      }
      
      public void setColor(char theColor) {
         color = theColor;
      }

      public void setAdjacentRoom(Node adjacentRoom, int exit) {
         adjacentRooms[exit] = adjacentRoom;
      } 

      // Method to display this room's exits in a row
      public void displayAdjacency() {
         for (Node adjacentRoom : adjacentRooms) {
            System.out.print((null == adjacentRoom) ? "0 " : "1 ");
         }
         System.out.println("");
      }
   }
   
   //------------------------------------------------------ CTOR ---
   public Graphing() {
      mRand = new Random();
      bipartite = true;
   }
   
   private void shuffleNodes() {
      int randomIndex;
      Node temp;
      for (int i = mOrder - 1; i > 0; i--) {
         randomIndex = mRand.nextInt(i);
         temp = mGraph[i];
         mGraph[i] = mGraph[randomIndex];
         mGraph[randomIndex] = temp;
      }
   }
   
   private void paintNodes() {
      mGraph[0].setColor('b');
      
      for (Node i : mGraph) {
         //if (!bipartite) break;
         for (Node j : i.adjacentRooms) {
            if (null != j) {
               if(i.color == 'b') {
                  if (j.color == 'g')
                     j.setColor('r');
                  else if (j.color == 'b')
                     bipartite = false;
               }
               if (i.color == 'r') {
                  if (j.color == 'g')
                     j.setColor('b');
                  else if (j.color == 'r')
                        bipartite = false;
               }
               //if (!bipartite) break;
               
               for (Node k : j.adjacentRooms) {
                  if (null != k) {
                     if (j.color == 'b') {
                        if (k.color == 'g')
                           k.setColor('r');
                        if (k.color == 'b')
                           bipartite = false;
                     }
                     if (j.color == 'r') {
                        if (k.color == 'g')
                           k.setColor('b');
                        if (k.color == 'r')
                           bipartite = false;
                     }
                     //if (!bipartite) break;
                  }
               }
            }
         }
      }
   }

   //------------------------------------------------------ generateGraph -
   // Create a cycle of rooms.
   private void generateGraph() {
      for (int i = 0; i < mSize; i++) {
         shuffleNodes();
         
         if(i >= mOrder - 1) {
            
            mGraph[i % mOrder].setAdjacentRoom(mGraph[(i + 1) % mOrder],
                                             mGraph[(i + 1) % mOrder].roomNum);
            mGraph[(i + 1) % mOrder].setAdjacentRoom(mGraph[i % mOrder], 
                                             mGraph[i % mOrder].roomNum);
         }
         else {
            mGraph[i].setAdjacentRoom(mGraph[i + 1], mGraph[i + 1].roomNum);
            mGraph[i + 1].setAdjacentRoom(mGraph[i], mGraph[i].roomNum);
         }
      }
   }

   //------------------------------------------------------ displayMatrix -
   // Display all room exits.
   private void displayMatrix(Node[] showRooms) {
      System.out.println("Adjacency Matrix:");
      
      System.out.println("");
      for (int i = 0; i < mGraph.length ; i++) {
         showRooms[i].displayAdjacency();
      }
   }
   
   //------------------------------------------------------ displayNodeColors -
   // Display all room names.
   private void displayNodeColors() {
      System.out.println("Displaying all room names...");
      for(Node i : mGraph)
         System.out.println(i.getRoomName());
   }
   
   //------------------------------------------------------ sortNodes ---
   // Bubble sort by room numbers.
   //
   // This is for testing and sanity: 
   // It can be used before making a cycle to connect the rooms in order.
   // Mostly though, it should be used after the house is all set and the
   // order of the mGraph array no longer matters... 
   // it will make the displayMatrix MD look nice. :)
   private void sortNodes () {
      Node temp;
      boolean swapped;
      for (int i = 0; i < mOrder - 1; i++) {
         swapped = false;
         for (int j = 0; j < mOrder - 1; j++) {
            if (mGraph[j].getRoomNum() > mGraph[j + 1].getRoomNum()) {
               temp = mGraph[j];
               mGraph[j] = mGraph[j + 1];
               mGraph[j + 1] = temp;
               swapped = true;
            }
         }
         if (swapped == false)
            break;
      }
   }
   
   
   
   //------------------------------------------------------ validateInput ---
   // Validate mUserInput.
   public void validateInput() {
      Scanner input = new Scanner(System.in);
      
      //Input checkers
      boolean hasMoreThanNumbers = false;
      boolean correctInput = false;
      
      String[] theNums = new String[10];
      
      theNums[1] = "";
      
      //A loop for the first player's input.
      while (!correctInput) {
         System.out.println("Enter two integers.");
         System.out.println("You do not have to hit enter between each number. "
          + "Just spaces is fine.");
         
         theNums = input.nextLine().split(" ");
         
         
         
         //Ignores whitespaces
         for (String i : theNums) {
            for (char j : i.toCharArray()) {
               if (!Character.isDigit(j) && j != '-') hasMoreThanNumbers = true;
            }
         }
         
         if (hasMoreThanNumbers) {
            System.out.println("Don't be a smart-aleck. Use only numbers,"
             + "dude.");
            hasMoreThanNumbers = false;
         }
         else {
            correctInput = true;
         } 
      }
      
      int[] nums = new int[2];
      
      for (int k = 0; k < 2; k++)
         nums[k] = Integer.parseInt(theNums[k]);
      
      mOrder = nums[0];
      mSize = nums[1];
      
      
   }
   
   //------------------------------------------------------ main ---
   public static void main(String[] args) {
      Graphing graph = new Graphing();
      graph.plr();
   }
}
