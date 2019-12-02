package remoteMethodInvocation; /**
 *
 * @author rubes
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MethodInterface extends Remote {
	public int fibonacci(int n) throws RemoteException;
	public int factorial(int n) throws RemoteException;
}