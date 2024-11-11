package States;

import FileLog.FileLogHandler;
import client.Request;
import client.User;
import server.ClientConnection;
import server.Response;
import server.eResponseType;

import java.io.IOException;
import java.util.List;

public class ConnectRequestState implements RequestHandlingStates {

    ClientConnection connection;
    //private FileLogHandler fileLogHandler;

    public ConnectRequestState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException {
        connection.getServer().getClients().add(connection);
        connection.getOut().writeObject(new Response("Welcome to the chat!", eResponseType.CONNECTION_ESTABLISHED));

        //steg: ett

        //vi kör här först sen får vi se om det känns fel/rätt. 110% att det komer kännas fel när logiken är klar
        //ahaaaaa det är getname och equals med listor HAHAH
        //hahahaha
        List<User> currentUserList = FileLogHandler.readLogFile();
        boolean foundUser = false;
        for(User user : currentUserList){
            if(user.getUser().equals(request.getUsername())){
                foundUser = true;
            }
        }
        if (!foundUser) {
            //pushade upp förändringarna ifall min dator dör vilket den kommer göra snart för min laddare är intryckt i sovrummet där den minsta sover haha
            FileLogHandler.writeToFile();
        }
        connection.getServer().broadcast("Has joined: " + request.getUsername());

    }

}
