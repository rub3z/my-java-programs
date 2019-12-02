package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TwoSumLC1 {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line;
      while ((line = br.readLine()) != null) {
         ArrayList<int[]> arr = new ArrayList<>();
         String[] nums = line.split(" ");
         for (int i = 0; i < nums.length; i++) {
            arr.add(new int[]{Integer.parseInt(nums[i]), i});
         }



         int target = Integer.parseInt(br.readLine());

         List<int[]> arr2 = arr.stream()
          .filter(n -> (n[0] <= target))
          .sorted((a,b) -> a[0]-b[0])
          .collect(Collectors.toList());

         arr2.forEach(n -> System.out.print("(" + n[0] + "," + n[1] + ")"));
         System.out.println();

         int[] indices = new int[2];

         int i = 0, j = arr2.size() - 1;
         while (i < j) {
            int sum = arr2.get(i)[0] + arr2.get(j)[0];

            if (sum == target) {
               indices[0] = arr2.get(i)[1]; indices[1] = arr2.get(j)[1];
               break;
            }
            if (sum > target) {
               j--;
            }
            if (sum < target) {
               i++;
            }
         }

         System.out.println(indices[0] + " " + indices[1]);

//         for(String s : line.split(" ")) {
//            arr.add(Integer.parseInt(s));
//         }
//
//         int target = Integer.parseInt(br.readLine());
//
//         List<int[]> arr2 = arr.stream().map(n -> {
//            int[] a = {n, arr.indexOf(n)};
//            return a;
//         }).filter(n -> (n[0] < target))
//          .sorted((a,b) -> a[0]-b[0])
//          .collect(Collectors.toList());
//
//         int[] indices = new int[2];
//
//         List<Integer> x = Arrays.stream(indices).boxed().collect(Collectors.toList());
//
//         int i = 0, j = arr2.size() - 1;
//         while (i < j) {
//            int sum = arr2.get(i)[0] + arr2.get(j)[0];
//
//            if (sum == target) {
//               indices[0] = arr2.get(i)[1]; indices[1] = arr2.get(j)[1];
//               break;
//            }
//            if (sum > target) {
//               j--;
//            }
//            if (sum < target) {
//               i++;
//            }
//         }


//         System.out.println(indices[0] + " " + indices[1]);
      }
   }
}

//class Solution {
//   public int[] twoSum(int[] nums, int target) {
//      List<Integer> arr =
//       Arrays.stream(nums).boxed().collect(Collectors.toList());
//      List<int[]> arr2 = arr.stream().map(n -> {
//         int[] a = {n, arr.indexOf(n)};
//         return a;
//      }).filter(n -> (n[0] <= target))
//       .sorted((a,b) -> a[0]-b[0])
//       .collect(Collectors.toList());
//
//      int[] indices = new int[2];
//      int i = 0, j = arr2.size() - 1;
//      while (i < j) {
//         int sum = arr2.get(i)[0] + arr2.get(j)[0];
//
//         if (sum == target) {
//            indices[0] = arr2.get(i)[1]; indices[1] = arr2.get(j)[1];
//            break;
//         }
//         if (sum > target) {
//            j--;
//         }
//         if (sum < target) {
//            i++;
//         }
//      }
//
//      return indices;
//   }
//}
