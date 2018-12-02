import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Server extends Thread {
   private ServerSocket serverSocket;

   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " +
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());



            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
               );
            out.writeUTF("Request has been recieved successfully");
            server.close();

         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }

   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      File file = new File("100worst.txt");

      try{

         FileReader fileReader = new FileReader(file);

         BufferedReader bufferedReader = new BufferedReader(fileReader);
         List<String> lines = new ArrayList<String>();
         List<String> titles = new ArrayList<String>();
         List<ArrayList> splitString = new ArrayList<ArrayList>();
         Map<String, String> info = new HashMap<String, String>();
         String line = null;

         while((line = bufferedReader.readLine()) != null){
            lines.add(line);
         }

         bufferedReader.close();

         for(int i=0; i<6; i++){
           lines.remove(0);
         }

         for(int i=0; i<23; i++){
           lines.remove(lines.size()-1);
         }

         for(int i=0; i<lines.size(); i++){
           String temp = lines.get(i);
           String[] splitString = temp.split("\\s+\\s");
           for(int j=0; j<splitString.length()-1; j++){
             info.put(splitString.get(0), splitString.get(1));
           }
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
      try {
         Thread t = new Server(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
