package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatClient implements ActionListener {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;
    JTextField textField;
    JTextArea textArea;


    public ChatClient(JTextField textField, JTextArea textArea, Socket socket, String userName) {
        this.textField = textField;
        this.textArea = textArea;
        this.socket = socket;
        this.userName = userName;
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
        out.println(userName + ": " + message);
    }

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
