import java.util.concurrent.TimeUnit;
/** Class for handling each bidding war in its tern.
 * it gets the number of the item and its initial price from the randomized data,
 * times the amount of time for bidding (can be determined as well from config file),
 * ends the bid, and fires the calculation for determining winner. */
public class SingleAuction implements Runnable{

    int gameNumber;
    static int auctionNumber;
    static int currentInitialPrice;

    public SingleAuction(int miniGameNumber, int auctionNum) {
        gameNumber = miniGameNumber;
        auctionNumber = auctionNum;
    }

    public void run() {
        AuctionGame.outToAll("Item" + auctionNumber + ": starting price: " + getItemPrice());
        AuctionGame.outToAll(Data.waitingLength + " seconds to bid...");
        try {
            TimeUnit.SECONDS.sleep(Data.waitingLength - (Data.waitingLength - 5));
            AuctionGame.outToAll("5 seconds left...");
            TimeUnit.SECONDS.sleep(5);
            AuctionGame.endOfBidWar(auctionNumber);

        } catch (InterruptedException e) {
            AuctionGame.outToAll("timer problems, lets move to next Item");
            return;
        }

    }
/**function for local class to get the initial price of the current item for bid.
 * @return the initial price of item. */
    private int getItemPrice() {
        currentInitialPrice = Data.getItemPrice(gameNumber, auctionNumber);
        return currentInitialPrice;
    }
/** Util function for updating the players the number of the bidding war.
 * @return the number of the bidding war in the mini game. 
 */
    public static int getBidingWarNumber() {
        return auctionNumber;
    }
 /** Util function for updating the players the initial price of the item in the 
  * current bidding war.
 * @return the initial price of current item. 
 */   
    public static int GetCurrentMinBid() {
        return currentInitialPrice;
    }
}
