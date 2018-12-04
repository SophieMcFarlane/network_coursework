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
   private static Map<String, String> info = new HashMap<String, String>();

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
            //BufferedReader is = new BufferedReader(new InputStreamReader(server.getInputStream()));
            //name = is.readLine();
            //artist = is.readLine();
            //info.get(artist);

            //System.out.println("The name is: "+ name);
            //System.out.println("The artist is: "+ artist);

            System.out.println(in.readUTF());
            String artist = in.readUTF();
            System.out.println("The artist is:" + artist+ " ");

            //out.wrtieUTF("The request has been received successfully")
            //Iterator it = info.entrySet().iterator();
            //while(it.hasNext()){
              //Map.Entry pair = (Map.Entry).it.next();
              //if(pair.getKey() == artist){
                  //System.out.println("The song title is: " + pair.getValue());
              //}
              for(Map.Entry<String, String> entry : info.entrySet()){
                if(entry.getKey().equals(artist)){
                  System.out.println("The songs are: " + entry.getValue());
                }
              }

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
               );
            out.writeUTF("Your request has been recieved successfully");

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

   public static void main(String [] args) throws Exception {
      int port = Integer.parseInt(args[0]);
      File file = new File("100worst.txt");

      try{

         FileReader fileReader = new FileReader(file);

         BufferedReader bufferedReader = new BufferedReader(fileReader);
         List<String> lines = new ArrayList<String>();
         List<String> titles = new ArrayList<String>();
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
           Pattern p = Pattern.compile("\\s+\\s");
           Matcher m = p.matcher(temp);
           List<String> stuff = new ArrayList<String>();

           if(m.find()){
             int start = m.start();
             int end = m.end();

             if(start == 0){
               String t = lines.get(i-1);
               String tt = lines.get(i);
               String ttt = t+tt;
               //System.out.println(ttt);
               lines.remove(i-1);
               lines.add(i, (ttt));
               //lines.remove(i);
             }
               //System.out.println(lines.get(i));
               //need to use start of the line before or resuse matcher and pattern
                   Pattern pp = Pattern.compile("\\s+\\s");
                   Matcher mm = pp.matcher(lines.get(i));
                   if(mm.find()){
                     int start2 = mm.start();
                     int end2 = mm.end();
                     String s = lines.get(i).substring(0,start2);
                     String ss = s.substring(4);
                     String sss = lines.get(i).substring(end2);
                     String ssss = sss.substring(0, sss.length()-4);

                   info.put(ssss.trim(),ss);
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
