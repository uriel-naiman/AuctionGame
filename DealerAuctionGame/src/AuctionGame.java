import java.net.ServerSocket;
import java.util.ArrayList;

public class AuctionGame implements Runnable {
    static int gameNumber = 1;
    ServerSocket listener;
    private static ArrayList<ClientHandler> clientArray;

    public AuctionGame (ServerSocket listen, ArrayList<ClientHandler> clients) {
        listener = listen;
        clientArray = clients;
    }

    @Override
    public void run() {
    Data.getDataFromFile();
    runNewMiniGame(gameNumber);
    
    while (Math.random() * 10 > 4) {
        ++gameNumber;
        runNewMiniGame(gameNumber);
    }
}
    
    private void runNewMiniGame(int gameNumber2) {
        Data.createMiniGameData();
        outToAll("Starting game number " + gameNumber2);
        Data.printItemsOfMiniGame(gameNumber2);
        MiniGame miniGame = new MiniGame(gameNumber2);
        miniGame.run();
    }

    public static void outToAll(String msg) {
        for (ClientHandler client : clientArray) {
            client.output.println(msg);
        }
    }

    public static void addMoneyToWallets(int miniGameInitialSum) {
        for (ClientHandler client : clientArray) {
            client.wallet += miniGameInitialSum;
        }
    }

    

    
}
