package server;

import client.User;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private String message;
    private eResponseType responseType;
    public List<User> users;

    public Response(String message, eResponseType responseType) {
        this.message = message;
        this.responseType = responseType;
    }

    public Response(List<User> users, eResponseType responseType) {
        this.responseType = responseType;
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public eResponseType getResponseType() {
        return responseType;
    }

    public List<User> getUsers() {
        return users;
    }

}
