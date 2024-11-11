package server;

import java.io.Serializable;

public class Response implements Serializable {

    private String message;
    private eResponseType responseType;

    public Response(String message, eResponseType responseType) {
        this.message = message;
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public eResponseType getResponseType() {
        return responseType;
    }

}
