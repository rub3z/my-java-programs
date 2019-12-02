package powercrisis;//package powercrisis;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Rub3z
 */
class Main {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) 
{
       int[] cities = new int[100];
       int n; // number of cities
       Scanner in = new Scanner(System.in);
       ArrayList<Integer> input = new ArrayList<Integer>();
       int line = -1;
       while(line != 0) {
          line = in.nextInt();
          input.add(line);
       }
       
       for (Integer i : input) {
       for(int modTarget=1; modTarget< i; modTarget++)
       {
          
              for(int j=0; j< i; j++) // initialize the cities
                     cities[j] = j+1;
//            for(int x=0;x<100;x++)
//                   System.out.println(x + ":" + cities[x]);

              int count;
              count = i;
              int cur;
              cur = 0;
              int modCount;
              modCount = 0;
              while (count > 1)
              {
//                   System.out.println("Deleting City-Index:" + cur + 
//                     " Value:" + cities[cur]);
                     cities[cur]=0;
//                   for(int a=0; a<n; a++)
//                         System.out.println(cities[a] + "/");
                     count--;
                     cur = (cur+1)%i;

                     modCount = 1;
                     while(modCount < modTarget)
                     {
//                         System.out.println("modCount:" + modCount + 
//                          " modTarget:" + modTarget + 
//                          " cur:" + cur);
                           while (cities[cur%i] == 0) 
                           {
//                                System.out.println("skipping...cur:" + cur);
                                  cur = (cur+1)%i;
                                  
                           }
                           cur = (cur+1)%i;
                           while (cities[cur%i] == 0) // just incase the cur++ index is 0
                           {
//                                System.out.println("skipping...cur:" + cur);
                                  cur = (cur+1)%i;
//                                System.out.println("cur:" + cur);
                           }


                           modCount++;
//                         System.out.println"modCount:" + modCount);
                     }
//                   System.out.println("end of modCount loop - cur:" + cur);
              }
              int index;
              index = 0;
              while (cities[index] == 0)
                     index++;
              if (cities[index] == 13)
                 System.out.println(modTarget);
       }
       }
}

}
   

