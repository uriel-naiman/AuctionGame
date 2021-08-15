import java.util.ArrayList;
/** Class for initializing randomizing and storing data of mini games. */
public class MiniGameData {
    int miniGameNumber;
    int numberOfItems;
    private static ArrayList<SingleAuctionData> singleAuctions = new ArrayList<>();
     
    public MiniGameData(int number, int numOfItems, int m, int x, int y) {
        miniGameNumber = number;
        numberOfItems = numOfItems;
        int itemNumber = 1;

        while (itemNumber <= numOfItems) {
            int price = randomizePrice(x, y);
            SingleAuctionData singleAuction = new SingleAuctionData(itemNumber, price);
            singleAuctions.add(singleAuction);
            ++itemNumber;
        }
    }
/** Util function to randomize price between two values.
 * @param x first value
 * @param y second value.
  */
    private int randomizePrice(int x, int y) {
        int range = (y - x) + 1;
        int unRoundedPrice = (int)(Math.random() * range) + x;
        int modulo = unRoundedPrice % 10;
        int roundedPrice = (Math.round(unRoundedPrice / 10) * 10);
        if (modulo > 4) return roundedPrice + 10;
        return roundedPrice;
    }
/**@returns the amount of auctions played in the mini game. */
    public int getAuctionsPlayed() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.wasPlayed();
        }
        return counter;
    }
/**@returns the total value in dollars spent in the mini game. */
    public int getTotalPrice() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.priceSold;
        }
        return counter;
    }
/**@returns the total amount of items sold in the mini game. */
    public int getItemsSold() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.wasSold();
        }
        return counter;
    }
/**Util function to print to dealers console the items of the mini game
 *  and their initial prices. */
    public void printInitialItems() {
        int priceArr[] = new int[numberOfItems];
        int longestNumber = 1;
        for (int i = 0; i < numberOfItems; ++i) {
            priceArr[i] = (singleAuctions.get(i).itemInitialPrice);
            int length = String.valueOf(priceArr[i]).length();
            if (length > longestNumber) 
                longestNumber = length;
        }

        for (int i = 0; i < numberOfItems; ++i) {
            if (longestNumber == 3)
                System.out.println("Item" + (i + 1) + " = $" + priceArr[i]);
            if (longestNumber == 4) {
                if (String.valueOf(priceArr[i]).length() == 4){
                    String str = String.format("%,d", priceArr[i]);
                    System.out.println("Item" + (i + 1) + " = $" + str);
                } else if (String.valueOf(priceArr[i]).length() == 3){
                    System.out.println("Item" + (i + 1) + " = $  " + priceArr[i]); 
                } else if (String.valueOf(priceArr[i]).length() == 2){
                    System.out.println("Item" + (i + 1) + " = $   " + priceArr[i]); 
                }
            }
        }
    }
/**@returns the initial price of a single item */
    public int getItemPrice(int auctionNumber) {
        return singleAuctions.get(auctionNumber - 1).itemInitialPrice;
    }
/**@returns the total value of all initial prices in a mini game.
 * used to add this amount to each players wallet at the beginning of
 * each mini game.
 */
    public int getTotalInitialSum() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.itemInitialPrice;
        }
        return counter;
    }
/**returns the sum of initial prices for a certain mini game, from a certain
 * auction and forwards. used for late joiners.
 */
    public int getTotalInitialSumForLateJoiners(int auctionNumber) {
        int counter = 0;
        for (int i = auctionNumber; i > numberOfItems; i++) {
            counter += singleAuctions.get(i).itemInitialPrice;
        }
        return counter;
    }
/** util to set data of a single item if it was sold.*/
    public void setItemSold(SingleUserBid winningBid, int itemNumber) {
        singleAuctions.get(itemNumber - 1).setData(true, winningBid);
    }
/**
 * @returns the amount of items in a mini game that a certain player bought.
 * @param playerName - player number.
 */
    public int getItemsByPlayer(int playerName) {
            int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            if (auction.playerNumber == playerName){
                ++counter;
            }
        }
        return counter;
    }
/**
 * @returns the amount of money spent by a certain player in a mini game.
 * @param playerName - player number.
 */
    public int getSumSpentByPlayer(int playerName) {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            if (auction.playerNumber == playerName){
                counter += auction.priceSold;
            }
        }
        return counter;
    }
    
}
