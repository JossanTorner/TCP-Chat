package States;

import FileLog.FileLogHandler;
import client.Request;
import client.Status;
import client.User;
import server.ClientConnection;
import server.Response;
import server.eResponseType;

import java.io.IOException;
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

        connection.user = new User(request.getUsername(), Status.ONLINE);
        System.out.print("Try to write to file");
        FileLogHandler.writeObjectToFile(connection.user);

        connection.getServer().broadcastObjects(new Response(connection.getServer().getClients(), eResponseType.USER_STATE_CHANGED));

    }

}
