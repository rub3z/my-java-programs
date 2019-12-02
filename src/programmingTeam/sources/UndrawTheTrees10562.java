package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UndrawTheTrees10562 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.readLine();
      String line;
      while ((line = br.readLine()) != null) {
         char[][] tree = new char[200][200];
         for (int j = 0; j < line.length(); j++) {
            tree[0][j] = line.toCharArray()[j];
         }

         for (int i = 1; !line.equals("#"); i++) {
            line = br.readLine();
            char[] chars = line.toCharArray();
            for (int j = 0; j < line.length(); j++) {
               tree[i][j] = chars[j];
            }
         }

         int i = 0; int j = 0;
         while (tree[i][0] != '#' && j != 1337) {
            j = findNode(tree, i);
            if (j == 200) i += 2;
            else if (!isLeaf(tree, i, j)) {
               
            }


         }


//         for (char[] i : tree) {
//            System.out.println();
//            for (char j : i) {
//               System.out.print(j);
//            }
//         }
      }
   }

   public static int findNode(char[][] tree, int i) {
      int j = 0;
      while(j <= 200 || !Character.isLetter(tree[i][j++])){
         //System.out.println(j + " is now: " + tree[i][j]);
      }
      j--;
      //System.out.println(j + " is now: " + tree[i][j]);
      return j;
   }

   public static boolean isLeaf(char[][] tree, int i, int j) {
      return tree[i + 1][j] != '|';
   }

   public static int walkLeft(char[][] tree, int i, int j)  {
      i += 2;
     // while (tree[i][j] !)
      return 0;
   }

}