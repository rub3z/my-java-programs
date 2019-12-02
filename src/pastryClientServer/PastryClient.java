package pastryClientServer;
/**
 *
 * @author rubes
 */

import java.io.IOException;
import java.net.*;

public class PastryClient {

   public static void main(String args[]) throws SocketException,
    UnknownHostException,
    IOException {
      // args give message contents and server hostname 
      DatagramSocket aSocket = null;
      try {
         aSocket = new DatagramSocket();
         byte[] m = args[0].getBytes();
         InetAddress aHost = InetAddress.getByName(args[1]);
         int serverPort = 32710;
         DatagramPacket request = new DatagramPacket(m, m.length,
                                                      aHost, 
                                                      serverPort);
         aSocket.send(request);
         byte[] buffer = new byte[1000];
         DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
         aSocket.receive(reply);
         System.out.println("Reply: " + new String(reply.getData()));
      } catch (SocketException e) {
         System.out.println("Socket: " + e.getMessage());
      } catch (IOException e) {
         System.out.println("IO: " + e.getMessage());
      } finally {
         if (aSocket != null) {
            aSocket.close();
         }
      }
   }
}
