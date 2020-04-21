# network_coursework

Network Coursework from 2nd year of University 

Instructions to run can be found in readme.txt
The file to be passed can be found in the repository and is called '100worst.txt'

Assessment tasks are below

You are required to implement a Client-Server system. Your system should include the
following:

Server Program.
- Write a server program in Java.
- Once the server starts, it is passed a text file enlisting artists plus their songs which is read in and held in a map, i.e. the map holds tuples of type artist-song.
- The server must listen at a predefined host and port (you can specify the server
name (local host) and port in your program) for incoming TCP/IP requests.
- Client program(s) connecting to the server will communicate with it via the local host
and the specified port.
- The server receives an artist name from client and finds all the song(s) associated to
that artist.
- The server informs client(s) that it received the request successfully.
- The server retrieves the required information (songs associate to a given artist) from
the text file and returns the result to the client.
- The server should handle situations such as unavailable/busy ports, client requesting
a non-existing artist in the input file and no songs associated to a given artist.

Client program
 Write a client program in Java
 Once the client program is running, it automatically connects to the server via the
predefined host and port number that the server accepts connections from. For this
assignment, the server should run on the same machine as the client program.
 The client informs the user that server connection is successful, otherwise it should
display appropriate error message(s) to the user.
 The client prompts the user for the name of an artist.
 The client receives the artist name from the user and sends it to the server.
 The client receives a response (which should contain song(s) associated to the artist)
from the server and displays them on the screen (e.g., the terminal) for the user.
Note the server program should retrieve the songs from the supplied text file.
 Your setup should handle situations that may occur prior, during and after clientserver interaction. As minimum the client program should handle situations such as:
◦ the server is not running/unavailable;
◦ the port number is busy;
◦ user did not input artist name; and
◦ server response is not received.
You should print out those problems on screen.

Log files
 The server should record the following events to a log file
◦ the date and time the server has started;
◦ the date and time of incoming client connection request;
◦ whether connection was successful or not;
◦ the received artist name from client; and
◦ the length of time the server was connected to a client (the period between client
connection and disconnection).
 The client program should record the following events that occur during the clientserver communication in a log file
◦ server response time for a request
◦ server response length
◦ the date and time the client received a response from the server.

Bash files
 Write a bash file to contain instructions to start the server program. The bash file
should be executed from a terminal
 Write a bash file to contain instructions to start the client program. The bash file
should be executed from a terminal

Terminate client-server connection
 After the client sent a request, received and processed the server’s response for the
user, it should prompt the user to disconnect from the server.
 The user should send the command (quit) to the server to disconnect from the
server.
 The server should close the connection.
 The client should confirm to the user that the connection is closed.

Other requirements:
 Your system is compatible for different operating systems (Windows, Mac and Linux).
