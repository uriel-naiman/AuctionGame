/** Main function, runs client connection to server
 * added this class for future development - easier to add functionality
 *through this class */
public class PlayerGameLogic {
    public static void main(String[] args) throws Exception {
        PlayerGameLogic logic = new PlayerGameLogic();    
        logic.runGame();
    }
    //runs new instance of the player game created in main method
    public void runGame() throws Exception {
        Client.startClientServer();        
    }
}
