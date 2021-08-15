import java.net.*;
import java.io.*;  
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**main game server. creates server socket, waits for first player and continues to
 * listen for more joining players. Parallel to this, running new thread playing the 
 * game with currently connected users. */
public class Server {
    
    private static int playerNumber = 1;
    private static final int PORT = 6666;
    // private static PrintWriter output;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    
    public static void startServer() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        // output = new PrintWriter(client.getOutputStream(), autoFlush);
        AuctionGame game = new AuctionGame(listener, clients);
        final ScheduledThreadPoolExecutor newGame = new ScheduledThreadPoolExecutor(1);
        System.out.println("server up and running...waiting for players to join");  
        addPlayer(listener);               
        System.out.println("Starting first auction in 10 seconds...");

        newGame.schedule(game, 10, TimeUnit.SECONDS);

        while (clients.size() > 0) {
            addPlayer(listener);
            if (clients.isEmpty()) break;
        }

        System.out.println("All players left the game.");
        System.out.println("Game stats:");
        Data.printGameStats();
        listener.close();
    }
/**Function for creating a new player and adding it to the list of players 
 * @param listener is the server socket on which to open new socket for the 
 * new player.*/
    private static void addPlayer(ServerSocket listener) throws IOException {
        Socket client = listener.accept();
        System.out.println("Player" + playerNumber + " joined the game");
        ClientHandler clientThread = new ClientHandler(client, clients, playerNumber);
        clients.add(clientThread);
        ++playerNumber;
        pool.execute(clientThread);
    }

    /** function for stats to get the amount of players that participated in the game
     * because the counter is incremented immediately after creating new players, 
     * the amount is allways one more than real number, thus subtracting by one. */
    public static int getPlayers() {
        return playerNumber - 1;
    }

}
