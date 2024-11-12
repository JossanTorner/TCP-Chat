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
    //ahaaa men behövs den? tänker, servern kommer ju kanske va den som skickar med user-objekt i en disconnect/connect request, men hmmm svårt aa det e saNT
//eller blir det Client Connetion? tänker för när man kopplar upp och får connection ska man hoppa till att skriva till fil? Beroende på resopone och state?
    public Server(){
        try(ServerSocket serverSocket = new ServerSocket(PORT);){

            while(true) {
                Socket socket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(socket, this);
                clientConnection.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastObjects(Object object) throws IOException {
        for(ClientConnection client : clients) {
            client.sendObject(object);
        }
    }

    public void broadcast(String message) throws IOException {
        for(ClientConnection client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void removeClient(ClientConnection client) {
        clients.remove(client);
    }


    public List<ClientConnection> getClients() {
        return clients;
    }

    public static void main(String[] args) throws UnknownHostException {
         Server listener = new Server();
    }
}
