package States;

import client.Request;
import server.ClientConnection;
import server.Server;

import java.io.IOException;

public class MessageRequestState implements RequestHandlingStates{

    ClientConnection connection;

    public MessageRequestState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException {
        String message = request.getUsername() + " : " + request.getMessage();
        connection.getServer().broadcast(message);
        connection.getOut().close();
        connection.getIn().close();
    }


}
