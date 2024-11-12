package client;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private Status status;

    public User(String username, Status status) {
        this.username= username;

    }

    public String getUsername() {
        return username;
    }

    public Status getStatus() {
        return status;
    }
}
