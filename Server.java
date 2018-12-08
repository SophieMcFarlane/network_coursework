import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Server extends Thread {
   private ServerSocket serverSocket;
   private String name;
   private static Map<String, ArrayList<String>> info = new HashMap<String, ArrayList<String>>();

   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {

        try (
            //connecting to the Client and setting up the stream in and out
            Socket server = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            OutputStream outToClient = server.getOutputStream();
            PrintWriter out = new PrintWriter(outToClient, true);

        ) {

            System.out.println("Waiting for client on port " +
               serverSocket.getLocalPort() + "...");

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            String input;

            //while there is input coming in from the Client
            while ((input = in.readLine()) != null) {
              StringBuilder sb = new StringBuilder();

              //loops through the map of the artist and songs to see if it matches
              for(Map.Entry<String, ArrayList<String>> entry : info.entrySet()){

                //if it matches creates a string of the songs for the chosen artist
                if(entry.getKey().equals(input)){
                  ArrayList<String> strings = entry.getValue();
                  sb.append("The Songs are: ");
                  for(String s : strings){
                    sb.append(s+", ");
                  }
                }
              }

              //reads the input and sends the right response to the Client
              if(input.equals("quit")){
                out.println("Connection is closed");
                server.close();
              }else if(sb.toString().equals("")){
                out.println("No songs for this artist");
              }else{
                out.println(sb.toString());
              }
            }



         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
         } catch (IOException e) {
            e.printStackTrace();
         }

   }

   public static void main(String [] args) throws Exception {
      int port = Integer.parseInt(args[0]);
      File file = new File("100worst.txt");

      try{
        //Reads in the file
         FileReader fileReader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(fileReader);

         //Creates a list of the lines in the file and the song titles
         List<String> lines = new ArrayList<String>();
         List<String> titles = new ArrayList<String>();
         String line = null;

         //adds the lines of the file to the list of lines
         while((line = bufferedReader.readLine()) != null){
            lines.add(line);
         }

         bufferedReader.close();

         //removes the irrelevant lines at the top
         for(int i=0; i<6; i++){
           lines.remove(0);
         }

         //removes the irrelevant lines at the bottom
         for(int i=0; i<23; i++){
           lines.remove(lines.size()-1);
         }

         //runs through the lines and finds the white space
         for(int i=0; i<lines.size(); i++){
           String temp = lines.get(i);
           Pattern p = Pattern.compile("\\s+\\s");
           Matcher m = p.matcher(temp);
           List<String> stuff = new ArrayList<String>();

           if(m.find()){
             int start = m.start();
             int end = m.end();

             //finds the artist and title that run over 2 lines
             //joins the 2 lines together
             if(start == 0){
               String t = lines.get(i-1);
               String tt = lines.get(i);
               String ttt = t+tt;

               lines.remove(i-1);
               lines.add(i, (ttt));

             }

             //finds the whitespace and separtes into artist and title
             Pattern pp = Pattern.compile("\\s+\\s");
             Matcher mm = pp.matcher(lines.get(i));
             if(mm.find()){
               int start2 = mm.start();
               int end2 = mm.end();
               String s = lines.get(i).substring(0,start2);
               String ss = s.substring(4);
               String sss = lines.get(i).substring(end2);
               String ssss = sss.substring(0, sss.length()-4);

               ArrayList<String> values = info.get(ssss.trim());

               //creates an arraylist for the map if one isnt already made
               //adds the artist and songs to the map
               if(values == null){
                 values = new ArrayList<String>();
                 values.add(ss);
                 info.put(ssss.trim(), values);
               }else{
                 values.add(ss);
                 info.put(ssss.trim(), values);
               }
              }
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
