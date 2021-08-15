import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
 /**second thread for listening to server (dealer) at all times
  * add the prefix "Dealer:" to every message coming from the server.
  */
public class ServerConnection implements Runnable {
    private Socket server;
    private BufferedReader input;

    public ServerConnection(Socket s) throws IOException {
        server = s;
        input = new BufferedReader(new InputStreamReader(server.getInputStream()));

    }
    @Override
    public void run() {
        try {
            while (!server.isClosed()) {
                String serverResponse = input.readLine();

                if (serverResponse == null) break;

                System.out.println("Dealer: " + serverResponse);
            }
         } catch (IOException e) {
            System.out.println("Thanks For Playing!");
        } finally {
                try {
                    input.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
            }
        }
        
}
