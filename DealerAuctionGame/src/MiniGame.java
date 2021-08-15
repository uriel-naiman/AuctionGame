
/**Class for running one mini game.
 * Reminder: the amount of bids in each mini game is determined by config file.
 * the Class determines and adds the amount of money to add to each wallet,
 * and runs all the bidding wars in the mini game.
 */
public class MiniGame implements Runnable{
    int miniGameNumber;

    public MiniGame(int gameNumber) {
        miniGameNumber = gameNumber;
    }

    @Override
    public void run() {
        int auctionNumber = 1;
        CurrentBid.reset(miniGameNumber, auctionNumber);
        int miniGameInitialSum = Data.getTotalInitialSum(miniGameNumber);
        AuctionGame.addMoneyToWallets(miniGameInitialSum);
        AuctionGame.outToAll(miniGameInitialSum + "$ has been added to your wallets");
        while (auctionNumber < Data.getNumOfItems(miniGameNumber) && AuctionGame.getNumOfPlayers() > 0) {
            CurrentBid.reset(miniGameNumber, auctionNumber);
            AuctionGame.outToAll("Auction war " + auctionNumber + ":");
            SingleAuction bidingWar = new SingleAuction(miniGameNumber, auctionNumber);
            bidingWar.run();
            ++auctionNumber;
        }
    }

}
