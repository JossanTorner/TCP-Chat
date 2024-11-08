package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    static final int PORT = 8000;

    public ServerListener() {

        try(ServerSocket serverSocket = new ServerSocket(PORT);){

            while(true) {

                Socket socket = serverSocket.accept();
                ChatServer clientHandler = new ChatServer(socket);
                clientHandler.start();

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
