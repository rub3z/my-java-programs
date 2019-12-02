package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClockHands {

   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      br.lines().forEach(line -> {
         String[] nums = line.split(":");
         int hour = Integer.parseInt(nums[0]);
         int mins = Integer.parseInt(nums[1]);

         double minAngle = mins * 6;
         double hourAngle = hour * 30 + mins * 0.5;
         System.out.println(hourAngle + " " + minAngle);

         double ans = Math.abs(hourAngle - minAngle);
         ans = (ans > 180 ? 360 - ans : ans);
         if (hour != 0) {
            System.out.println(ans + "00");
         }
      });
   }
}
