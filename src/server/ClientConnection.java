package server;
import States.RequestHandlingStates;
import client.Request;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread {

    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private RequestHandlingStates state;

    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        initializeConnection();
    }

    public void initializeConnection(){
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(in.readObject() instanceof Request request){

                switch (request.getRequestType()){
                    case CONNECT -> {}
                    case MESSAGE -> {}
                    case DISCONNECT -> {}

                }

            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            server.removeClient(this);
        }
    }

    public void sendMessage(String message) throws IOException {
       out.writeObject(new Response(message, eResponseType.BROADCAST));
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getServer(){
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }
}
