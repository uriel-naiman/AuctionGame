import java.net.*;
import java.io.*;  
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    
    private static int playerNumber = 1;
    private static final int PORT = 9090;
    // private static PrintWriter output;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    
    public static void startServer() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        // output = new PrintWriter(client.getOutputStream(), autoFlush);
        AuctionGame game = new AuctionGame(listener);
        final ScheduledThreadPoolExecutor newGame = new ScheduledThreadPoolExecutor(1);
        System.out.println("server up and running...waiting for players to join");  
        addPlayer(listener);               
        System.out.println("Starting first auction in 10 seconds...");

        newGame.schedule(game, 10, TimeUnit.SECONDS);

        while (true) {
            addPlayer(listener);
            if (clients.isEmpty()) break;
        }

        System.out.println("All players left the game.");
        System.out.println("Game stats:");
        listener.close();
    }

    private static void addPlayer(ServerSocket listener) throws IOException {
        Socket client = listener.accept();
        System.out.println("Player" + playerNumber + " joined the game");
        ClientHandler clientThread = new ClientHandler(client, clients, "Player" + playerNumber);
        clients.add(clientThread);
        ++playerNumber;
        pool.execute(clientThread);
    }
    
    // private static anyPlayerLeft() {
    //     while (true){
    //         if (clients.isEmpty()) break;
    //     }
    //     break;
    // }
}
