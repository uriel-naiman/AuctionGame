/** Main function, runs main game connection to server
 * added this class for future development - easier to add functionality
 *through this class */
public class DealerGameLogic {

    public static void main(String[] args) throws Exception {
        DealerGameLogic logic = new DealerGameLogic();
        
        logic.runGame();
    }
    public void runGame() throws Exception {
        System.out.println("Starting game server...");
        Server.startServer();     
    }
}
