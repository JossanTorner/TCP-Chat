package client;


import server.Response;
import server.eResponseType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatClient implements ActionListener{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    Status status;
    JTextField textField;
    JTextArea textArea;
    ChatWindow window;
    List<User> userList;


    public ChatClient(JTextField textField, JTextArea textArea, Socket socket, String userName, ObjectOutputStream out, ObjectInputStream in, ChatWindow window) throws IOException {
        this.out = out;
        this.in = in;
        userList = new ArrayList<>();
        this.textField = textField;
        this.textArea = textArea;
        this.socket = socket;
        this.username = userName;
        this.window = window;
        status = Status.OFFLINE;
        try{
            startListeningForMessages();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void startConnection() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
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
    
    public void sendMessage(Object request) throws IOException {
        out.writeObject(request);
    }

    public void startListeningForMessages() throws IOException {
        new Thread(() -> {
            try {
                while(true){
                    Object serverMessage = in.readObject();
                    if (serverMessage instanceof Response response) {
                        switch(response.getResponseType()){
                            case CONNECTION_ESTABLISHED -> {
                                System.out.println("Got connection response");
                                status = Status.ONLINE;
                                String message = response.getMessage();
                                textArea.append(message + "\n");
                            }
                            case CONNECTION_TERMINATED -> {
                                System.out.println("Got offline response");
                                status = Status.OFFLINE;
                                String message = response.getMessage();
                                textArea.append(message + "\n");
                            }
                            case BROADCAST -> {
                                System.out.println("Got broadcasted message");
                                String message = response.getMessage();
                                textArea.append(message + "\n");
                            }
                            case USER_STATUS_CHANGED ->{
                                System.out.println(username + " received updated user list with " + response.getUsers().size() + " users:");
                                for (User user : response.getUsers()) {
                                    System.out.println("  - " + user.getUsername());
                                }
                                userList = new ArrayList<>(response.getUsers());  // Create a fresh copy
                                window.updateMemberList(userList);
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String message = textField.getText();
            Request request = new Request(eRequest.MESSAGE, username, message);
            sendMessage(request);
            textField.setText("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
