package server;
import States.ConnectRequestState;
import States.DisconnectRequestState;
import States.MessageRequestState;
import States.RequestHandlingStates;
import client.Request;
import client.User;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread {

    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    RequestHandlingStates state;
    RequestHandlingStates messageState;
    RequestHandlingStates disconnectState;
    RequestHandlingStates connectState;
    User user;

    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        messageState = new MessageRequestState(this);
        disconnectState = new DisconnectRequestState(this);
        connectState = new ConnectRequestState(this);

    }

    public void run(){
        try {
            initializeConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initializeConnection() throws IOException {
        try{
            System.out.println("Connection established with client");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while(in.readObject() instanceof Request request){
                System.out.println("Taking requests of request-type");
                switch (request.getRequestType()){
                    case CONNECT -> {
                        System.out.println("Connect request");
                        state = connectState;
                        state.handleRequest(request);
                    }
                    case MESSAGE -> {
                        System.out.println("Message request");
                        state = messageState;
                        state.handleRequest(request);
                    }
                    case DISCONNECT -> {
                        System.out.println("Disconnect request");
                        state = disconnectState;
                        state.handleRequest(request);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            server.removeClient(this);
            out.close();
            in.close();
        }
    }

    public void sendObject(Object object) throws IOException {
        out.writeObject(object);
    }

    public void sendMessage(String message) throws IOException {
       out.writeObject(new Response(message, eResponseType.BROADCAST));
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
