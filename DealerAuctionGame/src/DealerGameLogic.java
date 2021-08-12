public class DealerGameLogic {

    public void runGame() throws Exception {
        Utils.openTerminal();
        System.out.println("Starting game server...");
        Server.startServer();  

        
    }
    
    public static void main(String[] args) throws Exception {
        DealerGameLogic logic = new DealerGameLogic();

        logic.runGame();
    }
}
