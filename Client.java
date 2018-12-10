import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
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
         PrintWriter writer = new PrintWriter("client.log", "UTF-8");


         //Gets the user input of the artist and sends to the Server
         System.out.println("Enter Artist here: ");
         Scanner userInput = new Scanner(System.in);
         String artist = userInput.nextLine();
         long start = System.currentTimeMillis();
         out.println(artist);

         //Creates the input stream
         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

         //Prints out the songs from the server
         String input;

         //while there is still input from the server
         while ((input = in.readLine()) != null) {

           //needs to be logged
           writer.println("Server response received on: "+ new Date().toString());

           //needs to be logged
           writer.println("The response length was " + input.getBytes().length + " bytes");

           //checks to see if user want to quit
           if(input.equals("Connection is closed")){
             System.out.println(input);
           }else{
             //prints the input from the server
             long end = System.currentTimeMillis();

             //needs to be logged
             writer.println("It took " + (end-start)+ " ms to recieve a response from the server the request to get songs");

             System.out.println(input);
             //Gets the next input from the user and sends to the Server
             System.out.println("Enter 'quit' to exit");
             String answer = userInput.nextLine();
             out.println(answer);
           }
         }
         writer.close();
      //Handles errors
      } catch (BindException b){
        System.out.println("Port already in use");
      } catch(ConnectException c){
        System.out.println("Cant connect to the Server");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
