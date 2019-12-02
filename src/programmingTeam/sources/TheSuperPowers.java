package programmingTeam.sources;

import java.util.ArrayList;
import java.util.HashMap;

public class TheSuperPowers {
   public static void main(String[] args) {
      HashMap<Long, String> map = new HashMap<>();
      ArrayList<Long> superPowers = new ArrayList<>();

      long x = Long.parseUnsignedLong("4294967295");
      String ls = Long.toUnsignedString(x * x);
      System.out.println(ls);
      //String ls = Long.toUnsignedString();
//      for (int i = 0; i < 64; i++) {
//         if (i == 2 || i ==  3 || i ==  5 || i ==  7 || i ==  11 || i ==  13 || i ==  17 || i ==  19 || i ==  23 || i ==  29 || i ==  31 || i ==  37 || i ==  41 || i ==  43 || i ==  47 || i ==  53 || i ==  59 || i == 61) {}
//         else {
//            for (int j = 1; j < 16; j++) {
//
//               superPowers.add((long) Math.pow(j, i));
//            }
//         }
//      }
//      System.out.println("1");
//      superPowers.stream()
//       .sorted().forEach(System.out::println);
   }
}