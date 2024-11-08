package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatServer extends Thread {

    Socket socket;
    private ServerListener server;
    PrintWriter out;
    BufferedReader in;

    public ChatServer(Socket socket, ServerListener server) {
        this.socket = socket;
        this.server = server;
    }

    public void run(){

        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String clientMessage;
            while((clientMessage = in.readLine()) != null){
                out.println(clientMessage);
                server.brodcast(clientMessage, this);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
            server.removeClient(this);
        }

    }

    public void sendMessage(String message) {
       out.println(message);
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
