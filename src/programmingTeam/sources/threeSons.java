package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class threeSons {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      ArrayList<Integer> factors = new ArrayList<>();

      br.lines().forEach(line -> {
         String[] nums = line.split(" ");
         int numSons = Integer.parseInt(nums[0]);
         int product = Integer.parseInt(nums[1]);

         for(int i = 1; i <= product; ++i)
            if (product % i == 0) factors.add(i);

         factors.removeIf(i -> i > 31 || i <= 1);
         factors.forEach(System.out::println);

         product_up(factors, product);

      });
   }

   static void product_up_recursive(ArrayList<Integer> numbers, int target, ArrayList<Integer> partial) {
      int s = 1;
      for (int x: partial) s *= x;
      if (s == target)
         System.out.println("product("+Arrays.toString(partial.toArray())+")="+target);
      if (s >= target)
         return;
      for(int i=0;i<numbers.size();i++) {
         ArrayList<Integer> remaining = new ArrayList<Integer>();
         int n = numbers.get(i);
         for (int j=i+1; j<numbers.size();j++) remaining.add(numbers.get(j));
         ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
         partial_rec.add(n);
         product_up_recursive(remaining,target,partial_rec);
      }
   }
   static void product_up(ArrayList<Integer> numbers, int target) {
      product_up_recursive(numbers, target, new ArrayList<Integer>());
   }
}
