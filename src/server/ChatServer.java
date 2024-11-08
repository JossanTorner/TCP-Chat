package server;

import java.net.Socket;

public class ChatServer extends Thread {

    static final int PORT = 8000;
    Socket socket;

    public ChatServer(Socket socket) {
        this.socket = socket;
    }

    public void run(){

        //Här ska vi hantera inström och utström

    }
}
