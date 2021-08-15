

public class SingleAuctionData {

    int itemNumber;
    boolean isSold = false;
    int priceSold = 0;
    int itemInitialPrice;
    boolean played = false;

    public SingleAuctionData(int number, int price) {
        itemNumber = number;
        itemInitialPrice = price;
    }

    public void setData(boolean wasSold, int price) {
        if (wasSold) {
            isSold = true;
            priceSold = price;
        }
        played = true;
    }

    public int wasPlayed() {
        if (played) return 1;
        return 0;
    }

    public int wasSold() {
        if (isSold) return 1;
        return 0;
    }



}
