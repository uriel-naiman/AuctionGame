import java.util.ArrayList;

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

    private int randomizePrice(int x, int y) {
        int range = (y - x) + 1;
        int unRoundedPrice = (int)(Math.random() * range) + x;
        int modulo = unRoundedPrice % 10;
        int roundedPrice = (Math.round(unRoundedPrice / 10) * 10);
        if (modulo > 4) return roundedPrice + 10;
        return roundedPrice;
    }

    public int getAuctionsPlayed() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.wasPlayed();
        }
        return counter;
    }

    public int getTotalPrice() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.priceSold;
        }
        return counter;
    }

    public int getItemsSold() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.wasSold();
        }
        return counter;
    }

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
                } else {
                    System.out.println("Item" + (i + 1) + " = $ " + priceArr[i]); 
                }
            }
        }
    }

    public int getItemPrice(int auctionNumber) {
        return singleAuctions.get(auctionNumber - 1).itemInitialPrice;
    }

    public int getTotalInitialSum() {
        int counter = 0;
        for (SingleAuctionData auction: singleAuctions) {
            counter += auction.itemInitialPrice;
        }
        return counter;
    }

    public int getTotalInitialSumForLateJoiners(int auctionNumber) {
        int counter = 0;
        for (int i = auctionNumber; i > numberOfItems; i++) {
            counter += singleAuctions.get(i).itemInitialPrice;
        }
        return counter;
    }

    
}
