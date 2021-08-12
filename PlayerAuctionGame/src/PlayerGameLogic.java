public class PlayerGameLogic {

    public void runGame() throws Exception {
        Utils.openTerminal();
        Client.startClientServer();
        
    }

    public static void main(String[] args) throws Exception {
        PlayerGameLogic logic = new PlayerGameLogic();

        logic.runGame();
    }
}
