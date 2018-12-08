import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);

      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);

         System.out.println("successfully connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         PrintWriter out = new PrintWriter(outToServer, true);

         //out.println("Hello from " + client.getLocalSocketAddress());
         System.out.println("Enter Artist here: ");

         Scanner userInput = new Scanner(System.in);
         String artist = userInput.nextLine();
         userInput.close();
         out.println(artist);


         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

         System.out.println(in.readLine());

         client.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
