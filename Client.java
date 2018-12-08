import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

   public static void main(String [] args) {
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);

      try {

        //Creates the socket and connects to the Server
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);

         //Creates the output stream
         System.out.println("successfully connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         PrintWriter out = new PrintWriter(outToServer, true);

         //Gets the user input of the artist and sends to the Server
         System.out.println("Enter Artist here: ");
         Scanner userInput = new Scanner(System.in);
         String artist = userInput.nextLine();
         out.println(artist);

         //Creates the input stream
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

         //Prints out the songs from the server
         String input;

         //while loop while there is still input from the server
         while ((input = in.readLine()) != null) {
           //checks to see if user want to quit
           if(input.equals("Connection is closed")){
             System.out.println(input);
           }else{
             //prints the input from the server
             System.out.println(input);
             //Gets the next input from the user and sends to the Server
             System.out.println("Enter 'quit' to exit");
             String answer = userInput.nextLine();
             out.println(answer);
           }
         }




         //Reads the data from the Server and decided what to do enxt
         //String incoming = in.readLine();


      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
