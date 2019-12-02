package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TheMartian {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line;
      while ((line = br.readLine()) != null) {
         String[] chars = line.split(",");
         String hex = "";
         for (String c : chars) {
            if (c.equals("0")) hex += "0";
            if (c.equals("22.5")) hex += "1";
            if (c.equals("45")) hex += "2";
            if (c.equals("67.5")) hex += "3";
            if (c.equals("90")) hex += "4";
            if (c.equals("112.5")) hex += "5";
            if (c.equals("135")) hex += "6";
            if (c.equals("157.5")) hex += "7";
            if (c.equals("180")) hex += "8";
            if (c.equals("202.5")) hex += "9";
            if (c.equals("225")) hex += "A";
            if (c.equals("247.5")) hex += "B";
            if (c.equals("270")) hex += "C";
            if (c.equals("292.5")) hex += "D";
            if (c.equals("315")) hex += "E";
            if (c.equals("337.5")) hex += "F";
         }

         hex = "0x" + hex;
         //System.out.println(hex);
         int asciiChar = Integer.decode(hex);
         //System.out.println(asciiChar);
         char cx = 0;
         cx += asciiChar;
         System.out.print(cx);
      }
   }
}















