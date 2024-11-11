package client;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {

    eRequest requestType;
    String username;
    String message;

    public Request(eRequest requestType, String username, String message) {
        this.requestType = requestType;
        this.username = username;
        this.message = message;
    }

    public eRequest getRequestType() {
        return requestType;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }


}
