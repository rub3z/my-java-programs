package assignment5cecs327;

/**
 *
 * @author rubes
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Assn5Server {

   public static void main(String args[]) {
      try {
         int serverPort = 7896;
         ServerSocket listenSocket = new ServerSocket(serverPort);
         while (true) {
            Socket clientSocket = listenSocket.accept();
            Connection c = new Connection(clientSocket);
         }
      } catch (IOException e) {
         System.out.println("Listen :" + e.getMessage());
      }
   }
}

class Connection extends Thread {

   DataInputStream in;
   DataOutputStream out;
   Socket clientSocket;

   public Connection(Socket aClientSocket) {
      try {
         clientSocket = aClientSocket;
         in = new DataInputStream(clientSocket.getInputStream());
         out = new DataOutputStream(clientSocket.getOutputStream());
         this.start();
      } catch (IOException e) {
         System.out.println("Connection:" + e.getMessage());
      }
   }

   public void run() {
      try {
// an echo server
         String data = in.readUTF();
         for(char i : data.toCharArray()) data = i + data;
         out.writeUTF(data.substring(0, data.length()/2));
      } catch (EOFException e) {
         System.out.println("EOF:" + e.getMessage());
      } catch (IOException e) {
         System.out.println("IO:" + e.getMessage());
      } finally {
         try {
            clientSocket.close();
         } catch (IOException e) {/*close failed*/
         }
      }
   }
}
