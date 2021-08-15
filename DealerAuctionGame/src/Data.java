import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Data {
    static int n = 10;
    static int m = 20;
    static int x = 100;
    static int y = 2000;
    private static ArrayList<MiniGameData> miniGames = new ArrayList<>();
    static int miniGameNumber = 0;
    
    public static void getDataFromFile(){
        Properties prop = new Properties();
        String fileName = "Dealer.config"; 
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            n = Integer.valueOf(prop.getProperty("Dealer.n"));
            m = Integer.valueOf(prop.getProperty("Dealer.m"));
            x = Integer.valueOf(prop.getProperty("Dealer.x"));
            y = Integer.valueOf(prop.getProperty("Dealer.y"));
        } catch (Exception ex) {
            ClientHandler.outToEveryone("Using default configuration");        
        }
    }

    public static void createMiniGameData(){
        int range = (m - n) + 1;
        int numOfItems = (int)(Math.random() * range) + n;
        ++miniGameNumber;
        MiniGameData miniGame = new MiniGameData(miniGameNumber, numOfItems, m, x, y);
        miniGames.add(miniGame);
    }

    public void printGameStats() {
        System.out.println("************************************************");
        System.out.println("total mini games played: " + miniGameNumber);
        System.out.println("Total auction wars played: " + getAuctionWars());
        System.out.println("items sold: " + getItemsSold());
        System.out.println("Total price payed for " + getItemsSold() + " items: " + getTotalPrice());
        System.out.println("Total players: " + Server.getPlayers());
    }

    private int getAuctionWars() {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getAuctionsPlayed();
        }
        return counter;
    }

    private int getTotalPrice() {
        int totalPrice = 0;
        for (MiniGameData game: miniGames) {
            totalPrice += game.getTotalPrice();
        }
        return totalPrice;
    }

    private int getItemsSold() {
        int counter = 0;
        for (MiniGameData game: miniGames) {
            counter += game.getItemsSold();
        }
        return counter;
    }

    public static int getMiniGames() {
        return miniGameNumber;
    }

    public static void printItemsOfMiniGame(int gameNumber) {
        miniGames.get(gameNumber - 1).printInitialItems();
    }

    /**
     * returns the amount of auctions in miniGameNumber
     * @param miniGameNumber gameNumber - to retrieve from Array send index (-1)
     */
    public static int getNumOfItems(int miniGameNumber) {
        return miniGames.get(miniGameNumber -1).numberOfItems;
    }

    public static int getItemPrice(int gameNumber, int auctionNumber) {
        return miniGames.get(gameNumber - 1).getItemPrice(auctionNumber);
    }

    public static int getTotalInitialSum(int miniGameNumber) {
        return miniGames.get(miniGameNumber - 1).getTotalInitialSum();
    }

    public static int getInitialSumForLateJoiners(int miniGameNumber, int auctionNumber) {
        return miniGames.get(miniGameNumber - 1).getTotalInitialSumForLateJoiners(auctionNumber);
    }



    
}
