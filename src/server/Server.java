package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static final int PORT = 8000;
    private List<ClientConnection> clients = new ArrayList<>();

    public Server(){
        try(ServerSocket serverSocket = new ServerSocket(PORT);){

            while(true) {
                Socket socket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(socket, this);
                clientConnection.start();
                clients.add(clientConnection);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void brodcast(String message, ClientConnection excludeClient){
        for(ClientConnection client : clients) {
            if(client != excludeClient){
                client.sendMessage(message);
            }
        }
    }
    public synchronized void removeClient(ClientConnection client) {
        clients.remove(client);
    }


    public static void main(String[] args) throws UnknownHostException {
         Server listener = new Server();
    }
}
