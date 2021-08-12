import java.net.*;
import java.io.*;  

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 9090;
    
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
