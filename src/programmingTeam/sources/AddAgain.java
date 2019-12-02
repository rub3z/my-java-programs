package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddAgain {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line = "";
      while((line = br.readLine()) != null) {
         line = br.readLine().replaceAll("\\s", "");
         int n = line.length();
         ArrayList<Long> arr = new ArrayList<>();

         permutation(line, arr);

         System.out.println(arr.stream().distinct().mapToLong(i -> i.longValue()).sum());

      }

   }

   public static void permutation(String str, ArrayList<Long> nums) {
      permutation("", str, nums);
   }

   private static void permutation(String prefix, String str, ArrayList<Long> nums) {
      int n = str.length();
      if (n == 0) nums.add(Long.parseLong(prefix));
      else {
         for (int i = 0; i < n; i++)
            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), nums);
      }
   }
}
