package server;
import States.ConnectRequestState;
import States.DisconnectRequestState;
import States.MessageRequestState;
import States.RequestHandlingStates;
import client.Request;

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
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while(in.readObject() instanceof Request request){

                switch (request.getRequestType()){
                    case CONNECT -> {
                        state = connectState;
                        state.handleRequest(request);
                    }
                    case MESSAGE -> {
                        state = messageState;
                        state.handleRequest(request);
                    }
                    case DISCONNECT -> {
                        state = disconnectState;
                        state.handleRequest(request);
                    }

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
