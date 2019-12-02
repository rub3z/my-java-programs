package pastryClientServer;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 *
 * @author rubes
 */
public class UltimatePastryClient {
   
   public static void main(String args[]) throws SocketException,
    UnknownHostException,
    IOException {
//      int x = hopToIt("2133","13.58.192.214", 0);
//      System.out.println("Num hops: " + x);
      int[] numHops = new int[1000];
      for (int i = 0; i < numHops.length; i++) {
         String guid = String.format("%4s", 
           Integer.toString(new Random().nextInt(256),4))
           .replace(" ","0");
         System.out.println(guid);
         numHops[i] = hopToIt(
          guid,
          "13.58.192.214", 1);
         System.out.println(numHops[i]);
      }
      int[] nums = new int[6];
      
      for (int i : numHops) {
         switch(i) {
            case 0: nums[0]++; break;
            case 1: nums[1]++; break;
            case 2: nums[2]++; break;
            case 3: nums[3]++; break;
            case 4: nums[4]++; break;
            case 5: nums[5]++; break;
            default: break;
         }
      }
      
      for (int i = 0; i < nums.length; i++) {
         System.out.println("Number of " + i + "'s: " + nums[i]);
      }
   }
   
   public static int hopToIt (String id, String ip, int num) throws SocketException,
    UnknownHostException,
    IOException {
      if (num > 4)
         return num;
      DatagramSocket aSocket = null;
      DatagramPacket reply = null;
      try {
         aSocket = new DatagramSocket();
         aSocket.setSoTimeout(500);
         byte[] m = id.getBytes();
         InetAddress aHost = InetAddress.getByName(ip);
         int serverPort = 32710;
         DatagramPacket request = new DatagramPacket(m, m.length,
                                                      aHost, 
                                                      serverPort);
         aSocket.send(request);
         byte[] buffer = new byte[1000];
         reply = new DatagramPacket(buffer, buffer.length);
         aSocket.receive(reply);
         String rep = new String(reply.getData());
         
         if(rep.equals("NULL")) {
            return num;
         }
         
         if(rep.charAt(4) != ':') {
            return num;
         }
         
         if(rep.substring(0,4).equals(id)){
            return num;
         }
         
         ip = rep.substring(5);
         
      } catch (SocketException e) {
         System.out.println("Socket: " + e.getMessage());
         return num;
      } catch (IOException e) {
         System.out.println("IO: " + e.getMessage());
         return 0;
      } finally {
         if (aSocket != null) {
            aSocket.close();
         }
      }
      
      return hopToIt(id, ip, ++num);
   }
}
