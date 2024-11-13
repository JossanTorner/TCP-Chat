package server;
import States.ConnectRequestState;
import States.DisconnectRequestState;
import States.MessageRequestState;
import States.RequestHandlingStates;
import client.Request;
import client.User;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread implements Serializable {

    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    RequestHandlingStates state;
    RequestHandlingStates messageState;
    RequestHandlingStates disconnectState;
    RequestHandlingStates connectState;
    public User user;

    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        messageState = new MessageRequestState(this);
        disconnectState = new DisconnectRequestState(this);
        connectState = new ConnectRequestState(this);
    }

    public void run(){
        initializeConnection();
    }

    public void initializeConnection(){
        try{
            System.out.println("Connection established with client");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(in.readObject() instanceof Request request){
                System.out.println("Taking requests of request-type");
                switch (request.getRequestType()){
                    case CONNECT -> {//får du ut denna "connect request" till terminalen?
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
            e.printStackTrace();
        } finally {
            server.removeClient(this);
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
