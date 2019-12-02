package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CrosswordAnswers232 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line;
      int puzzleNum = 0;
      while ((line = br.readLine()) != null) {
         String[] nums = line.split(" ");
         int row = Integer.parseInt(nums[0]);
         int col = Integer.parseInt(nums[1]);

         char[][] xWord = new char[row][col];
         int[][] squareNums = new int[row][col];

         for (int i = 0; i < row; i++) {
            char[] thisLine = br.readLine().toCharArray();
            for ( int j = 0; j < col; j++) {
               xWord[i][j] = thisLine[j];
            }
         }

         int num = 0;
         for (int i = 0; i < row; i++) {
            for ( int j = 0; j < col; j++) {
               if (xWord[i][j] == '*') {
                  squareNums[i][j] = -1;
               }
               else if (j == 0 || i == 0) {
                  squareNums[i][j] = ++num;
               }
               else if (i != 0) {
                  if (xWord[i-1][j] == -1) {
                     squareNums[i][j] = ++num;
                  }
               }
               else if (j != 0) {
                  if (xWord[i][j-1] == -1) {
                     squareNums[i][j] = ++num;
                  }
               }
            }
         }

         for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
               System.out.print(xWord[i][j]);
            }
            System.out.println();
         }
         for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
               System.out.print(squareNums[i][j]);
            }
            System.out.println();
         }


         System.out.println("puzzle #" + (++puzzleNum) + ":");
         System.out.println("Across");
         for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
               if(squareNums[i][j] > 0) {
                  System.out.print("  " + squareNums[i][j] + ".");

                  for (int k = 0; j + k < col ;k++) {
                     if (xWord[i][j+k] != '*')
                        System.out.print(xWord[i][j+k]);
                     else k = 9000;
                  }
                  System.out.println();
               }
            }
         }
      }
   }
}
