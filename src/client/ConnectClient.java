package client;
import java.io.IOException;
import java.net.Socket;

public class ConnectClient {

    Socket socket;

    public ConnectClient(String serverIP, int port) throws IOException {
        this.socket = new Socket(serverIP, port);
    }

    public Socket getSocket() {
        return socket;
    }
}
