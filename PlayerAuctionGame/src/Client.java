import java.net.*;
import java.io.*;  

/** Class for connecting to game server on local host
 * for further development can add functionality for different
 * computers as well.
 * runs on two threads: one for input and output of the keyboard
 * and the second for listening to the server- running parallel*/
public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 6666;
    
    public static void startClientServer() throws IOException, InterruptedException {
        
        Socket socket = new Socket(SERVER_IP, PORT); 
        
        ServerConnection connection = new ServerConnection(socket);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); 
        boolean autoFlush = true;
        PrintWriter output = new PrintWriter(socket.getOutputStream(), autoFlush); 

        new Thread(connection).start();

        while(!socket.isClosed()) {  
            System.out.println("> ");
            String command = keyboard.readLine(); 

            if (command.equals("exit")) {
                Thread.sleep(100, 0);
                break; 
            }
            output.println(command);
        }  
        System.out.println("Game ended..");
        output.close();  
        socket.close();  
    }
}
