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

            Socket server = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            OutputStream outToClient = server.getOutputStream();
            PrintWriter out = new PrintWriter(outToClient, true);

        ) {

            System.out.println("Waiting for client on port " +
               serverSocket.getLocalPort() + "...");

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            String input;

            while ((input = in.readLine()) != null) {
              StringBuilder sb = new StringBuilder();
              int temp = 0;
              for(Map.Entry<String, ArrayList<String>> entry : info.entrySet()){
                if(entry.getKey().equals(input)){
                  temp += 1;
                  ArrayList<String> strings = entry.getValue();
                  sb.append("The Songs are: ");
                  for(String s : strings){
                    sb.append(s+", ");
                  }
                }
              }
              if(input.equals("quit")){
                out.println("Connection is closed");
                server.close();
              }else if(sb.toString().equals("")){
                out.println("No songs for this artist");
              }else{
                out.println(sb.toString());
              }
            }

            //server.close();


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

               lines.remove(i-1);
               lines.add(i, (ttt));

             }

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
