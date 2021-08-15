
/** Class for holding data of a single auction
*/
public class SingleAuctionData {

    int itemNumber;
    boolean isSold = false;
    int priceSold = 0;
    int itemInitialPrice;
    boolean played = false;
    int playerNumber = 0;

    public SingleAuctionData(int number, int price) {
        itemNumber = number;
        itemInitialPrice = price;
    }
/** sets data of the bid war after determination of the winner.
 * @param wasSold if a player bought the item or not.
 * @param winningBid holding the name of player and value of bid.
*/
    public void setData(boolean wasSold, SingleUserBid winningBid) {
        if (wasSold) {
            isSold = true;
            priceSold = winningBid.value;
            playerNumber = winningBid.playerNumber;
        }
        played = true;
    }
/** Util function to determine if the war was up for bid or not.
 * @return boolean indicating if the auction was played.
 */
    public int wasPlayed() {
        if (played) return 1;
        return 0;
    }
/** Util function to determine if the item was sold.
 * @return boolean indicating if the item was sold.
 */
    public int wasSold() {
        if (isSold) return 1;
        return 0;
    }

}
