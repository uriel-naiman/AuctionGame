import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
/**Class for reading config file, creating randomized numbers for each mini game, 
 * and each biding war.
 * is used as a single source of truth for getting game statistics. because we are using 
 * more than one thread it is important to use this class to not change the data in an
 * unsafe manner, resulting in wrong information.
 */
public class Data {
    static int n = 10;
    static int m = 20;
    static int x = 100;
    static int y = 2000;
    private static ArrayList<MiniGameData> miniGames = new ArrayList<>();
    static int miniGameNumber = 0;
    static int waitingLength = 30;

/** if config file exists, read data from it, and update default values. */
    public static void getDataFromFile(){
        Properties prop = new Properties();
        String fileName = "Dealer.config"; 
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            n = Integer.valueOf(prop.getProperty("Dealer.n"));
            m = Integer.valueOf(prop.getProperty("Dealer.m"));
            x = Integer.valueOf(prop.getProperty("Dealer.x"));
            y = Integer.valueOf(prop.getProperty("Dealer.y"));
            waitingLength = Integer.valueOf(prop.getProperty("Dealer.waitingLength"));
        } catch (Exception ex) {
            ClientHandler.outToEveryone("Using default configuration");        
        }
    }
/** randomizes and creates data for mini mini game */
    public static void createMiniGameData(){
        int range = (n - m) + 1;
        int numOfItems = (int)(Math.random() * range) + m;
        ++miniGameNumber;
        MiniGameData miniGame = new MiniGameData(miniGameNumber, numOfItems, m, x, y);
        miniGames.add(miniGame);
    }
/** Util function for printing main game stats to Dealer console */
    public static void printGameStats() {
        System.out.println("************************************************");
        System.out.println("total mini games played: " + miniGameNumber);
        System.out.println("Total auction wars played: " + getAuctionWars());
        System.out.println("items sold: " + getItemsSold());
        System.out.println("Total price payed for " + getItemsSold() + " items: " + getTotalPrice());
        System.out.println("Total players: " + Server.getPlayers());
    }
/**Util function for printing players stats to Player console */
    public static void printPlayerData(ClientHandler client) {
        client.output.println("************************************************");
        client.output.println("total items won: " + getItemsByPlayer(client.playerName));
        client.output.println("Total money spent: " + getSumSpent(client.playerName));
    }
/**Util function to determine the sum a certain player spent
 * @param playerName the number of the player,
 * @return number in american dollars.
 */
    private static int getSumSpent(int playerName) {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getSumSpentByPlayer(playerName);
        }
        return counter;
    }
/**Util function to determine the amount of items bought by a player 
 * @param playerName the number of the player,
 * @return number of items bought by the player
*/
    private static int getItemsByPlayer(int playerName) {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getItemsByPlayer(playerName);
        }
        return counter;
    }
/** @return the amount of items bid for (excludes items that reached the stage
 * but nobody bid for) */
    private static int getAuctionWars() {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getAuctionsPlayed();
        }
        return counter;
    }
/**@return the total amount of dollars spent in all mini games */
    private static  int getTotalPrice() {
        int totalPrice = 0;
        for (MiniGameData game: miniGames) {
            totalPrice += game.getTotalPrice();
        }
        return totalPrice;
    }
/**@return number of items sold in all mini games */
    private static int getItemsSold() {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getItemsSold();
        }
        return counter;
    }
/**@return the amount of mini games played */
    public static int getMiniGames() {
        return miniGameNumber;
    }
/** util function for printing the items and their prices to dealer's console
 * at the beginning of each mini game 
 */
    public static void printItemsOfMiniGame(int gameNumber) {
        miniGames.get(gameNumber - 1).printInitialItems();
    }

    /**
     * @returns the amount of auctions in mini Game
     * @param miniGameNumber gameNumber - to retrieve from Array send index (-1)
     */
    public static int getNumOfItems(int miniGameNumber) {
        return miniGames.get(miniGameNumber -1).numberOfItems;
    }
    /**
     * @returns the price of a certain item.
     * @param GameNumber gameNumber - to retrieve from Array send index (-1)
     * @param auctionNumber the number of the auction in the mini game.
     */
    public static int getItemPrice(int gameNumber, int auctionNumber) {
        return miniGames.get(gameNumber - 1).getItemPrice(auctionNumber);
    }
    /**
     * @returns the total price of all items in a mini game.
     * @param GameNumber gameNumber - to retrieve from Array send index (-1)
     */
    public static int getTotalInitialSum(int miniGameNumber) {
        return miniGames.get(miniGameNumber - 1).getTotalInitialSum();
    }
    /**
     * @returns the amount of money to add to late joiners
     * @param GameNumber gameNumber - to retrieve from Array send index (-1)
     * @param auctionNumber the number of the auction in the mini game.
     */
    public static int getInitialSumForLateJoiners(int miniGameNumber, int auctionNumber) {
        return miniGames.get(miniGameNumber - 1).getTotalInitialSumForLateJoiners(auctionNumber);
    }
    /**sets the data of a finished bidding war with the information of the winning bid.
     * @param winningBid holds the number of player and the value bid.
     * @param itemNumber in the mini game.
     * @param gameNumber in the big game.
     */
    public static void setItemSold(SingleUserBid winningBid, int itemNumber, int gameNumber) {
        miniGames.get(gameNumber - 1).setItemSold(winningBid, itemNumber);
    }

        
}
