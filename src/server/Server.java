package server;
import client.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static final int PORT = 8000;
    private List<ClientConnection> clients = new ArrayList<>();
    private List<User> users;

    public Server(){
        users = new ArrayList<>();
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

    public void broadcastObject(Object object) throws IOException {
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

    public List<User> getUsers(){
        return users;
    }

    public static void main(String[] args) throws UnknownHostException {
         Server listener = new Server();
    }
}
