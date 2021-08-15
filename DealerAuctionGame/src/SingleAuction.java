import java.util.concurrent.TimeUnit;

public class SingleAuction implements Runnable{

    int gameNumber;
    static int auctionNumber;

    public SingleAuction(int miniGameNumber, int auctionNum) {
        gameNumber = miniGameNumber;
        auctionNumber = auctionNum;
    }

    public void run() {
        AuctionGame.outToAll("Item" + auctionNumber + ": starting price: " + getItemPrice());
        AuctionGame.outToAll("15 seconds to bid...");
        try {
            TimeUnit.SECONDS.sleep(10);
            AuctionGame.outToAll("5 seconds left...");
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
            AuctionGame.outToAll("timer problems, lets move to next Item");
            return;
        }

    }

    private int getItemPrice() {
        return Data.getItemPrice(gameNumber, auctionNumber);
    }

    public static int getBidingWarNumber() {
        return auctionNumber;
    }



}
