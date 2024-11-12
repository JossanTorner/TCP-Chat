package client;

import server.Server;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private Status status;



    public User(String user, Status status) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
