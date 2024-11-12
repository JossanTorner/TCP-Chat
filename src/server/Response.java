package server;

import java.io.Serializable;
import client.User;

import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {

    private String message;
    private eResponseType responseType;
    List<ClientConnection> onlineList;

    public Response(String message, eResponseType responseType) {
        this.message = message;
        this.responseType = responseType;
    }

    public Response(List<ClientConnection> onlineList, eResponseType responseType){
        this.onlineList = onlineList;
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }


    public eResponseType getResponseType() {
        return responseType;
    }

    public List<ClientConnection> getOnlineList(){
        return onlineList;
    }
}
