import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      String artist = args[2];

      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);

         System.out.println("successfully connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

         out.writeUTF("Hello from " + client.getLocalSocketAddress());

         out.writeUTF(artist);

         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);

         System.out.println(in.readUTF());
         System.out.println(in.readUTF());
         int limit = Integer.parseInt(in.readUTF());
         System.out.println(limit);
         for(int i=0; i < limit; i++){
           System.out.println("The songs are: "+in.readUTF());
         }
         client.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
