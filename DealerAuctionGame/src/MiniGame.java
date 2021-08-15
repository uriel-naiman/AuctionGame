

public class MiniGame implements Runnable{
    int miniGameNumber;

    public MiniGame(int gameNumber) {
        miniGameNumber = gameNumber;

    }

    @Override
    public void run() {
        int auctionNumber = 1;
        int miniGameInitialSum = Data.getTotalInitialSum(miniGameNumber);
        AuctionGame.addMoneyToWallets(miniGameInitialSum);
        AuctionGame.outToAll(miniGameInitialSum + "$ has been added to your wallets");
        while (auctionNumber < Data.getNumOfItems(miniGameNumber)) {
            AuctionGame.outToAll("Auction war " + auctionNumber + ":");
            SingleAuction bidingWar = new SingleAuction(miniGameNumber, auctionNumber);
            bidingWar.run();
        }
    }

}
