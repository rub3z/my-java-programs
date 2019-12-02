package aout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aout {

public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(
       new InputStreamReader(System.in));
      String line;
      
      br.lines().forEach(l -> {
         int cents = Integer.parseInt(l.split(" ")[0]);
         int half = Integer.parseInt(l.split(" ")[1]);
         
         int pen = 0, nic = 0, dim = 0, qua = 0, hDol = 0;
         
         if( cents % 10 >= 5 && cents > 25 ){
            qua +=1;
            cents -= 25;
         }
         
         while( cents >= 10 ){
            dim += 1;
            cents -= 10;
         }
         while( cents >= 5 ){
            nic += 1;
            cents -= 5;
         }
         while( cents > 0 ){
            pen += 1;
            cents -= 1;
         }
         
         if( dim > 4 ){
            dim -= 5;
            qua += 2;
         }
         if( half > 0 && qua > 1 ){
            qua -= 2;
            hDol += 1;
         }
         
         if( hDol > 0 ){
            System.out.print( hDol +"x50 ");
         }
         if( qua > 0 ){
            System.out.print( qua +"x25 ");
         }
         if( dim > 0 ){
            System.out.print( dim +"x10 ");
         }
         if( nic > 0 ){
            System.out.print( nic +"x5 ");
         }
         if( pen > 0 ){
            System.out.print( pen +"x1 ");
         }
         System.out.println();
      });
   }
}
