import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    
    private Socket client;
    private BufferedReader input;
    public PrintWriter output;
    private static ArrayList<ClientHandler> clients;
    private String playerName;
    int wallet = 0;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientsArray, String name) throws IOException {
        client = clientSocket;
        clients = clientsArray;
        playerName = name;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean autoFlush = true;
        output = new PrintWriter(client.getOutputStream(), autoFlush);
    }

    public void run() {
        output.println(getWelcomeMessage());
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

    private String getWelcomeMessage() {
        int miniGame = Data.getMiniGames();
        if (miniGame == 0)
            return("Welcome! You are player" + playerName + ", we are waiting for the game to begin...");
        int auction = SingleAuction.getBidingWarNumber();
        int sum = Data.getInitialSumForLateJoiners(miniGame, auction);
        wallet = sum;
        return("Welcome! You are player" + playerName + ", \nwe are waiting for a Bidding-War on Item" + auction + " to end and then you will be joining the game. \nWe added " + sum + "$ to your wallet for the coming auctions... \n Enjoy!");
    }

    private void outToAll(String msg) {
        for (ClientHandler client : clients) {
            client.output.println(msg);
        }
    }

    static void outToEveryone(String msg) {
        for (ClientHandler client : clients) {
            client.output.println(msg);
        }
    }

    
}
