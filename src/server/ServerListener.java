package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ServerListener {

    static final int PORT = 8000;
    private List<ChatServer> clients = new ArrayList<>();

    public ServerListener() throws UnknownHostException {
//        InetAddress hamachiAddress = InetAddress.getByName("25.16.11.103");
        try(ServerSocket serverSocket = new ServerSocket(PORT);){

            while(true) {
                Socket socket = serverSocket.accept();
                ChatServer chatServer = new ChatServer(socket, this);
                chatServer.start();
                clients.add(chatServer);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void brodcast(String message, ChatServer excludeClient){
        for(ChatServer client : clients) {
            if(client != excludeClient){
                client.sendMessage(message);
            }
        }
    }
    public synchronized void removeClient(ChatServer client) {
        clients.remove(client);
    }


    public static void main(String[] args) throws UnknownHostException {
         ServerListener listener = new ServerListener();
    }
}
