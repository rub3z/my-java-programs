package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author Rub3z
 */
public class Sudoku {
   
   
   // These member variables can be fiddled with to change the frequency of 
   // simulated annealing and the maximum number of sidesteps allowed 
   // during a hillclimb before restarting the process.
   static final int MAX_NUMBER_OF_SIDESTEPS = 5;
   static final int MAX_NUMBER_OF_BAD_STEPS = 1;
   static final int RESET = 50;
   
   // Here is the aptly named method for solving a Sudoku grid.
   // It does the following:
   // 1. It takes in the given grid - a 9x9x2 array - and makes two copies
   // to represent the current and neighbor states.
   // 2. It creates an initial state by calling a helper method to fill
   // in the empty slots with random numbers such that each row has the 
   // numbers 1 - 9 without duplicates.
   // 3. It begins the hillclimb; after which it modifies the given array
   // (int[][][] grid) into what is (hopefully) a solution state.
   public static void solveSudoku(int[][][] grid) {
      int errors = 133742069; // Number of errors in the current state
      int best = 133742069;   // Number of errors in solution state
      
      // Initialize variables to control annealing / sidestepping
      int sidesteps = MAX_NUMBER_OF_SIDESTEPS;
      int badSteps = MAX_NUMBER_OF_BAD_STEPS;
      
      // This is used to determine appropriate conditions for adjusting
      // in the event of too many bad swaps.
      int reset = RESET;
      
      // Copy grid
      int[][][] current = new int[9][9][2];
      int[][][] next = new int[9][9][2];
      
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 2; k++) {
               current[i][j][k] = grid[i][j][k];
               next[i][j][k] = grid[i][j][k];
            }
         }
      }
      
      // Make initial state
      initializeSudoku(current, next);
      errors = checkErrors(current);
      
      
      // THE HILLCLIMB
      // The loop will perform random a random swap within a row followed 
      // by an error check; if the resulting state has equal or less errors 
      // than the previous best error check, then that is put into the 
      // current state. The current state can also be changed to have more
      // errors with bad swaps; the best current state obtained is transferred 
      // into a solution state with number of errors represented by best.
      do {
         int i = (int)(Math.random() * 8) + 1;
         int j = (int)(Math.random() * 8) + 1;
         int k = (int)(Math.random() * 8) + 1;
         
         if (next[i][j][1] != 1337 && next[i][k][1] != 1337 && j != k) {
            swap(next, i, j, k);
            
            int result = checkErrors(next);
            
            if (result > errors) {
               if (sidesteps == 0 && badSteps > 0) {
                  System.out.println("Bad step permitted for simulated "
                   + "annealing!!! Steps left: " + --badSteps);
                  errors = result;
                  for (int l = 0; l < 9; l++) {
                     for (int m = 0; m < 9; m++) {
                        for (int n = 0; n < 2; n++) {
                           current[l][m][n] = next[l][m][n];
                        }
                     }
                  }
               }
               
               else if (sidesteps == 0 && badSteps == 0) {
                  System.out.println("No more bad steps permitted."
                  + "\nReset permissions...");
                  System.out.println("");
                  sidesteps = MAX_NUMBER_OF_SIDESTEPS;
                  badSteps = MAX_NUMBER_OF_BAD_STEPS;
                  errors = 133742069;               
               }
               
               else {
                  System.out.print("b");
                  swap(next, i, j, k);
                  reset--;
                  if (reset == 0) {
                     swap(next, i, j, k);
                     reset = RESET;
                     sidesteps = MAX_NUMBER_OF_SIDESTEPS;
                     badSteps = MAX_NUMBER_OF_BAD_STEPS;
                     errors = result;
                  }
               }
            }
            
            else if (result <= errors) {
               errors = result;
               reset = RESET;
               
               if (result == best && sidesteps > 0) 
                  System.out.println("\nSidesteps left: " + sidesteps--);
               
               System.out.println("\nSetting new current state....");
               
               for (int l = 0; l < 9; l++) {
                  for (int m = 0; m < 9; m++) {
                     for (int n = 0; n < 2; n++) {
                        current[l][m][n] = next[l][m][n];
                     }
                  }
               }
               if (errors < best)  {
                  best = errors;
                  for (int l = 0; l < 9; l++) {
                     for (int m = 0; m < 9; m++) {
                        for (int n = 0; n < 2; n++) {
                           grid[l][m][n] = current[l][m][n];
                        }
                     }
                  }
               }
               printSudoku(current);
               System.out.println("                          "
                + "                                 " + errors + " " + best);
            }
         }
      } while (best > 0);
   }
   
   
   // Helper method to fill in blank slots randomly.
   public static void initializeSudoku(int[][][] grid1, int[][][] grid2) {
      for (int i = 0; i < 9; i++) {
         ArrayList<Integer> oneThruNine = new ArrayList<Integer>();
         IntStream.range(1, 10).forEach(oneThruNine::add);

         for (int j = 0; j < 9; j++) 
            if (grid1[i][j][1] == 1337) 
               oneThruNine.remove((Integer)(grid1[i][j][0]));

         for (int j = 0; j < 9; j++) 
            if (grid1[i][j][1] < 1337) {
               grid1[i][j][0] = 
                oneThruNine.remove(
                 (int)(Math.random() * oneThruNine.size()));
               grid2[i][j][0] = grid1[i][j][0];
               grid1[i][j][1] = 0;
               grid2[i][j][1] = 0;
            }
         
      }
   }
   
   // Check the given column and area for errors.
   public static int checkErrors(int[][][] grid) {
      int total = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (grid[i][j][1] < 1337) {
               grid[i][j][1] = 0;
               for (int k = 0; k < 9; k++) {
                  if(grid[k][j][0] == grid[i][j][0] && i != k) {
                     grid[i][j][1]++;
                     total++;
                  }
               }
               
               for (int k = (i / 3)*3; k < ((i / 3)*3) + 3; k++) {
                  for (int l = (j / 3)*3; l < ((j / 3)*3) + 3; l++) {
                     if (grid[k][l][0] == grid[i][j][0] 
                      && i != k && j != l) {
                        grid[i][j][1]++;
                        total++;
                     }
                  }
               }
            }
         }
      }
      return total;
   }
   
   // Print.
   public static void printSudoku(int[][][] grid) {
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++)
            System.out.print((grid[i][j][0] + "" + grid[i][j][1] + " ")
             .replace("1337", "X"));
         System.out.println("");
      }
   }
   
   // Swap.
   public static void swap(int[][][] array, int i, int j, int k) {
      int temp = array[i][j][0];
      array[i][j][0] = array[i][k][0];
      array[i][k][0] = temp;
   }
   
   public static void main(String[] args) throws FileNotFoundException {
      File grid = new File("grid.txt");
      Scanner fin = new Scanner(grid);
      
      int[][][] sudokuGrid = new int[9][9][2];
      
      for (int i = 0; i < 9; i++)
         for (int j = 0; j < 9; j++) {
            sudokuGrid[i][j][0] = fin.nextInt();
            if (sudokuGrid[i][j][0] > 0)
               sudokuGrid[i][j][1] = 1337;
         }
      
      solveSudoku(sudokuGrid);
      
      printSudoku(sudokuGrid);
      
   }
}
