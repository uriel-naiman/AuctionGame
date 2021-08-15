import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/** Class connecting the game server to each player.
 * reads the input received from player, determines if the input is legit,
 * determines if the player has the money he asks to bid, and writs to the player 
 * if he won or lost.
*/
public class ClientHandler implements Runnable {
    
    public Socket client;
    private BufferedReader input;
    public PrintWriter output;
    private static ArrayList<ClientHandler> clients;
    int playerName;
    int wallet = 0;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientsArray, int name) throws IOException {
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
                if (!request.matches("[0-9]+")){
                    output.println("MUST TYPE DIGITS ONLY!");
                }

                if (request.matches("[0-9]+")) {
                    int bidingValue = Integer.parseInt(request);
                    if (bidingValue > wallet) {
                        output.println("YOU DONT HAVE THAT MUCH MONEY!");
                    } else {
                        if (bidingValue < SingleAuction.GetCurrentMinBid()) {
                            output.println("Your bid is lower than the allowed price.");
                        } else {
                            boolean success = AuctionGame.addBid(playerName, bidingValue);
                            if (success) {
                                output.println("Your bid: " + bidingValue + ". Waiting for war to end.");
                            } else {
                                output.println("NO BIDING TWICE!!!");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Player"+playerName + " has left the game");
        }finally {
            outToAll("Player"+playerName + " has left the game");
            output.close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/** Creates welcome msg based on if the player joined before the game started or not,
 * if after the game started- adding only the amount of money left for auctions in the 
 * mini game.
 */
    private String getWelcomeMessage() {
        int miniGame = Data.getMiniGames();
        if (miniGame == 0)
            return("Welcome! You are player" + playerName + ", we are waiting for the game to begin...\nType exit to end game at all times");
        int auction = SingleAuction.getBidingWarNumber();
        int sum = Data.getInitialSumForLateJoiners(miniGame, auction);
        wallet = sum;
        return("Welcome! You are player" + playerName + ", \nwe are waiting for a Bidding-War on Item" + auction + " to end and then you will be joining the game. \nWe added " + sum + "$ to your wallet for the coming auctions... \nType exit to end game at all times \n Enjoy!");
    }
/**Local function to print to all players */
    private void outToAll(String msg) {
        for (ClientHandler client : clients) {
            client.output.println(msg);
        }
    }
/**Static function used by Data Class to print to all players */
    static void outToEveryone(String msg) {
        for (ClientHandler client : clients) {
            client.output.println(msg);
        }
    }
/** function for winning player */
    public void winningAction(int value, int item) {
        output.println("YOU WON ITEM" + item  + "!!");
        wallet -= value;
        output.println("Left in your wallet: " + wallet);
    }
/** function for losing player */
    public void losingAction() {
        output.println("Sorry mate, you lost this bid...");
    }
/** function for item nobody bid for */
    public void itemNotSold() {
        output.println("Nobody bid for this item, item not sold.");
    }

    
}
