package remoteMethodInvocation;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Assn6Client {
   public static void main(String[] args) {
      MethodInterface yee;
      try {
         yee = (MethodInterface)Naming.lookup(args[0]);
         if (args.length < 3) 
            System.out.println("The arguments should be factorial n or "
             + "fibonacci n");
         else {
            int in = Integer.parseInt(args[2]);
            int result = args[1].equals("fibonacci") ? 
             yee.fibonacci(in):
             yee.factorial(in);

            System.out.println(
             "The " + args[1] + " of " + in + " is " + result );
         }
      } catch (RemoteException rException) {
         System.out.println(rException.getMessage());
      } catch (Exception exception) {
         System.out.println("Client exception: " + exception.getMessage());
		}
   }
}
