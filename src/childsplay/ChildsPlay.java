package childsplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.Scanner;

public class ChildsPlay {

   public static void main(String[] args) throws IOException {
      //Scanner in = new Scanner(System.in);
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line;
      
      while((line = br.readLine()) != null) {
         String[] doit = line.split("\\s+");
         
         System.out.println((doit[1].equals("/")) 
          ? Integer.parseInt(doit[0]) / Integer.parseInt(doit[2])
          : Integer.parseInt(doit[0]) % Integer.parseInt(doit[2]));
      }
   }  
}
