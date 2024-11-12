package States;

import client.Request;
import client.Status;
import client.User;
import server.ClientConnection;
import server.Response;
import server.eResponseType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        User user = new User(request.getUsername(), Status.ONLINE);
        connection.setUser(user);
        connection.getServer().getUsers().add(user);

        connection.getServer().getUsers().add(user);


        connection.getServer().broadcastObject(new Response(connection.getServer().getUsers(), eResponseType.USER_STATUS_CHANGED));

        List<User> currentUsers = new ArrayList<>(connection.getServer().getUsers());
        connection.getServer().broadcastObject(new Response(currentUsers, eResponseType.USER_STATUS_CHANGED));
        System.out.println("Sent connection response");
    }

}
