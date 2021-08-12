import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<ClientHandler> clients;
    private String playerName;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientsArray, String name) throws IOException {
        client = clientSocket;
        clients = clientsArray;
        playerName = name;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean autoFlush = true;
        output = new PrintWriter(client.getOutputStream(), autoFlush);
    }

    public void run() {
        try {
            while (true) {
                String request = input.readLine();
                if (request == null) break;

                if (request.contains("Name")) {
                    output.println("Name also");
                } else {
                    output.println("No Name");
                }
            }
        } catch (Exception e) {
            System.out.println(playerName + " has left the game");
        }finally {
            outToAll(playerName + " has left the game");
            output.close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String msg) {
        for (ClientHandler client : clients) {
            client.output.println(msg);
        }
    }
}
