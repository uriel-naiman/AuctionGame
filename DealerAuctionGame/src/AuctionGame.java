import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
/**Class for running the main game thread called after first player joins the game.
 * runs a loop, each time creating a new mini game is there are any players still 
 * connected, and if a random number between 1 and 10 is 5 or greater.
 */
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
    runNewMiniGame();
    
    while (!clientArray.isEmpty()) {
        if (Math.random() * 10 > 4) {
            ++gameNumber;
            runNewMiniGame();
        } else {
            outToAll("Im tired and going home... your stats:");
            printPlayerStats();
            try {
                closeClients();
            } catch (IOException e) {
               outToAll("Problem kicking you out...");
            }
            break;
        }
    }
    Data.printGameStats();
}
    
    /**function for closing connections to players, if random number determines to end
     * the game. */
    private void closeClients() throws IOException {
        for (ClientHandler client : clientArray) {
            client.client.close();
        }
    }
/** function for printing personal player stats before closing connection */
    private void printPlayerStats() {
        for (ClientHandler client : clientArray) {
            Data.printPlayerData(client);
        }
    }
/** function for while loop to create new mini game and run it */
    private void runNewMiniGame() {
        Data.createMiniGameData();
        outToAll("Starting game number " + gameNumber);
        System.out.println("***************************************");
        System.out.println("GAME NUMBER: " + gameNumber);
        Data.printItemsOfMiniGame(gameNumber);
        MiniGame miniGame = new MiniGame(gameNumber);
        miniGame.run();
    }
/**Util function for printing a msg to all the players */
    public static void outToAll(String msg) {
        for (ClientHandler client : clientArray) {
            client.output.println(msg);
        }
    }
/** function for adding the amount of money determined to the players wallets */
    public static void addMoneyToWallets(int miniGameInitialSum) {
        for (ClientHandler client : clientArray) {
            client.wallet += miniGameInitialSum;
        }
    }
/** function for adding players bids into an array of bids */
    public static boolean addBid(int playerName, int bidingValue) {
        return CurrentBid.addBid(playerName, bidingValue);
    }

    /**function for determining winner of current bid, and sending apropriate msgs 
     * to each player if they won the bid or lost */
    public static void endOfBidWar(int itemNumber) {
        SingleUserBid winningBid = CurrentBid.getWinningBid();
        for (ClientHandler client : clientArray) {
            if (winningBid.value == 0) {
                client.itemNotSold();
                return;
            }
            if (client.playerName == winningBid.playerNumber) {
                client.winningAction(winningBid.value, itemNumber);
                Data.setItemSold(winningBid, itemNumber, gameNumber);
            } else {
                client.losingAction();
            }
        }
    }
/** Util function used by the mini game, after each bid war- to determine if 
 * there are any players still connected to the server. if not, 
 * the mini game + game ends */
    public static int getNumOfPlayers() {
        return clientArray.size();
    }

    

    
}
