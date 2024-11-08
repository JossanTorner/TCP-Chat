package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements ActionListener{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    JTextField textField;
    JTextArea textArea;


    public ChatClient(JTextField textField, JTextArea textArea, Socket socket) {
        this.textField = textField;
        this.textArea = textArea;
        this.socket = socket;
        try{
            startConnection();
            startListeningForMessages();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startConnection() throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void closeConnection() {
        try{
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendMessage(String message) throws IOException {
        out.println(message);
    }

//    public void recieveMessage() throws IOException {
//        textArea.append(in.readLine() + "\n");
//    }

    public void startListeningForMessages() {
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    textArea.append(serverMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String message = textField.getText();
            sendMessage(message);
            textField.setText("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
