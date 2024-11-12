package States;

import client.Request;
import server.ClientConnection;
import server.Response;
import server.eResponseType;

import java.io.IOException;

public class ConnectRequestState implements RequestHandlingStates {

    ClientConnection connection;

    public ConnectRequestState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException {
        connection.getServer().getClients().add(connection);
        connection.getOut().writeObject(new Response("Welcome to the chat!", eResponseType.CONNECTION_ESTABLISHED));
        connection.getServer().broadcast("Has joined: " + request.getUsername());
        System.out.println("Sent connection response");
    }

}
