package bigchoco;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BigChoco {

   public static void main(String[] args) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.lines().forEach(line -> {
         String[] nums = line.split(" ");
         int prod = Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
         System.out.println(prod - 1);
      });
   }
}
