import java.util.ArrayList;
/** Class for keeping track of the current bidding of the different players.
 * is cleaned before every bidding war, is called during war to determine if
 * the user place his bid yet or not(bidding twice id prohibited), and calculates
 * winner of each bidding war.
 */

public class CurrentBid {
    static int miniGameNumber;
    static int auctionNumber;
    private static ArrayList<SingleUserBid> bidingArray;

    public static boolean addBid(int playerName, int bidingValue) {
        for (SingleUserBid bid : bidingArray) {
            if (bid.playerNumber == playerName) return false;
        }
        SingleUserBid bid = new SingleUserBid(playerName, bidingValue);
        bidingArray.add(bid);
        return true;
    }
/** function for cleaning the bid array before each bidding war.
 * @param gameNumber the number of miniGame
 * @param auctionNum the number of the current auction in the mini game.
 */
    public static void reset(int gameNumber, int auctionNum) {
        miniGameNumber = gameNumber;
        auctionNumber = auctionNum;
        bidingArray = new ArrayList<SingleUserBid>();
    }
/**function to calculate the winning bid.
 * @return the highestBid (including the Player name and the price he bid)
 */
    public static SingleUserBid getWinningBid() {
        SingleUserBid highestBid = new SingleUserBid(0, 0);
        for (SingleUserBid bid : bidingArray) {
            if (bid.value > highestBid.value) {
                highestBid = bid;
            }
        }
        return highestBid;
    }

}
