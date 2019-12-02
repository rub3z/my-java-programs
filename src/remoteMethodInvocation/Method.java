package remoteMethodInvocation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author rubes
 */

public class Method extends UnicastRemoteObject 
   implements MethodInterface {
      
      public Method() throws RemoteException {}
      
      @Override
      public int fibonacci (int n) {
         return fibonacci(n, 0, 1, 0);
      }
      
      // Indexes zero and one are both 1; not 0, 1
      public int fibonacci(int n, int i, int a, int b) {
         if (i == n) return a;
         return fibonacci(n, i + 1, a + b, a);
      }
   
      @Override
      public int factorial(int n) throws RemoteException {
         return factorial(n, 1);
      }
      
      public int factorial(int n, int a) {
         if (n == 0) return a;
         return factorial(n - 1, n * a);
      }
   }