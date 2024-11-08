package server;

import client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.DoubleToIntFunction;

public class ChatServer extends Thread {

    Socket socket;
    private ServerListener server;
    PrintWriter out;
    BufferedReader in;

    public ChatServer(Socket socket) {
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
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }

    }

    /*public void run(){
        //Här ska vi hantera inström och utström
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = "";

            while((message = in.readLine()) != null) {
                out.println(message);
                server.brodcast(message, this);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

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
