package States;

import client.Request;

import java.io.IOException;

public interface RequestHandlingStates {
    void handleRequest(Request request) throws IOException;
}
