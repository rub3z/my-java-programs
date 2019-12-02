package pastryClientServer; /**
 * 
 * @author rubes
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class PastryServer {

   static Map<String, String> mLeafSet;
   static Map<String, String> mRoutingTable;
   
   public static void createLeafSet() {
      mLeafSet = new HashMap<String,String>();
      mLeafSet.put("0322", "0322:18.188.30.228");
      mLeafSet.put("1013", "1013:18.144.32.67");
      mLeafSet.put("1100", "1100:18.217.157.190");
      mLeafSet.put("1101", "1101:18.219.52.56");
   }
   
   public static void createRoutingTable() {
      mRoutingTable = new HashMap<String,String>();
      mRoutingTable.put("0", "0032:18.188.6.10");
      mRoutingTable.put("1", "1022:13.58.192.214");
      mRoutingTable.put("2", "2223:54.153.51.70");
      mRoutingTable.put("3", "3023:18.144.55.5");
      mRoutingTable.put("10", "1022:13.58.192.214");
      mRoutingTable.put("11", "1103:18.218.112.185");
      mRoutingTable.put("12", "1220:52.53.151.57");
      mRoutingTable.put("13", "1312:54.153.51.70");
      mRoutingTable.put("101", "1013:18.144.32.67");
      mRoutingTable.put("102", "1022:13.58.192.214");
      mRoutingTable.put("103", "NULL");
      mRoutingTable.put("104", "NULL");
      mRoutingTable.put("1020", "NULL");
      mRoutingTable.put("1021", "NULL");
      mRoutingTable.put("1022", "1022:13.58.192.214");
      mRoutingTable.put("1023", "NULL");
      
      mRoutingTable.put("0032", "0032:18.188.6.10");
      mRoutingTable.put("2223", "2223:54.153.51.70");
      mRoutingTable.put("3023", "3023:18.144.55.5");
      mRoutingTable.put("1103", "1103:18.218.112.185");
      mRoutingTable.put("1220", "1220:52.53.151.57");
      mRoutingTable.put("1312", "1312:54.153.51.70");
      mRoutingTable.put("1013", "1013:18.144.32.67");
      
   }
   
   public static void main(String args[]) {
      DatagramSocket aSocket = null;
      try {
         aSocket = new DatagramSocket(32710);
         byte[] buffer = new byte[1000];
         createLeafSet();
         createRoutingTable();
         System.out.println("I'M WAITING");
         
         while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            String req = new String(buffer).trim();
            boolean val = req.length() > 4;
            // Check if there are any non-numerical characters.
            for(char c : req.toCharArray())
               if(!Character.isDigit(c))
                  val = true;
            
            String replyString;
            if (val) {
               replyString = "INVALID REQUEST";
            }
            else {
               replyString = mLeafSet.get(req);
            }
            if (null == replyString) {
               for(int i = req.length(); replyString == null && i > 0; i--) {
                  System.out.println(req.substring(0,i));
                  replyString = mRoutingTable.get(req.substring(0, i));
               }
            }
            if (null == replyString) {
               replyString = "NULL";
            }
            
            byte[] rep = replyString.getBytes();
            DatagramPacket reply = new DatagramPacket(rep, 
             rep.length, request.getAddress(), request.getPort());
            aSocket.send(reply);
         }
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
