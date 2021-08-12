import java.net.ServerSocket;

public class AuctionGame implements Runnable {
    static int gameNumber = 1;
    public AuctionGame (ServerSocket listen) {
        ServerSocket listener = listen;
    }

    @Override
    public void run() {
    System.out.println("Starting game number " + gameNumber);
      
    }
    
    public static int getGameNumber() {
        return gameNumber;
    }

}
