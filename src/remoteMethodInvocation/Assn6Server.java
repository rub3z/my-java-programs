package remoteMethodInvocation; /**
 *
 * @author rubes
 */

import java.rmi.Naming;

public class Assn6Server {

   public static void main(String[] args) {
      try {
			Method yee = new Method();			   		   
			Naming.rebind("rmi://" + args[0] + "/cecs327", yee);
         
			System.out.println("Server is ready, bro.");
         System.out.println("Use rmi://" + args[0] + "/cecs327");
      }catch (Exception e) {
         System.out.println("Server failed, bro: " + e);
		}
   }
}
