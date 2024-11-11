package States;

import client.Request;
import com.sun.jdi.connect.spi.Connection;
import server.ClientConnection;
import server.Response;
import server.eResponseType;

import java.io.IOException;

public class DisconnectRequestState implements RequestHandlingStates{

    ClientConnection connection;

    public DisconnectRequestState(ClientConnection connection){
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException {
        connection.getServer().removeClient(connection);
        connection.getOut().writeObject(new Response("You have been disconnected ", eResponseType.CONNECTION_TERMINATED));
        connection.getServer().broadcast(request.getUsername() + " has left the server </3");
        connection.getOut().close();
        connection.getIn().close();
        connection.getSocket().close();
    }
}
