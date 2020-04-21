Have two windows open, one for the Server and one for the Client

Both need to be compiled using
javac Server.java
javac Client.java

Server
To run the server it needs
-port number
e.g java Server 6066

Client
To run the client it needs
-name
-port number
e.g java Client localHost 6066

It will then ask for input from the user for an artist or they can type 'quit' to leave
and will cause the server and client to close

The log files are called server.log and client.log respectively

To run the bash script have two windows open, one for the Server, one for the Client
bash server.sh
bash client.sh
