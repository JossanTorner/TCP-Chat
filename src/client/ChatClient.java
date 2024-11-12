package client;


import FileLog.FileLogHandler;
import server.ClientConnection;
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
    private List<User> userList;
    ChatWindow window;


    public ChatClient(JTextField textField, JTextArea textArea, Socket socket, String userName, ChatWindow window) {
        this.window = window;
        this.textField = textField;
        this.textArea = textArea;
        this.socket = socket;
        this.username = userName;
        status = Status.OFFLINE;

        userList = FileLogHandler.readObjectFile();


        try{
            startConnection();
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

    public void startListeningForMessages() {
        new Thread(() -> {
            try {
                Object serverMessage = in.readObject();
                if (serverMessage instanceof Response response) {
                    switch(response.getResponseType()){
                        case CONNECTION_ESTABLISHED -> {
                            status = Status.ONLINE;
                            String message = response.getMessage();
                            textArea.append(message + "\n");
                        }
                        case CONNECTION_TERMINATED -> {
                            status = Status.OFFLINE;
                            String message = response.getMessage();
                            textArea.append(message + "\n");
                        }
                        case BROADCAST -> {
                            String message = response.getMessage();
                            textArea.append(message + "\n");
                        }
                        case USER_STATE_CHANGED -> {
                            updateUserListAndLog(response.getOnlineList());
                            window.updateMemberDisplay(userList);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateUserList(List<ClientConnection> onlineList){
        for(int i= 0; i<onlineList.size(); i++){
            if(userList.get(i).getUsername().equals(onlineList.get(i).user.getUsername())){
                Status currentStatus = onlineList.get(i).user.getStatus();
                userList.get(i).setStatus(currentStatus);
            }
        }
    }

    public void updateUserListAndLog(List<ClientConnection> onlineList){
        for(int i= 0; i<onlineList.size(); i++){
            if(userList.get(i).getUsername().equals(onlineList.get(i).user.getUsername())){
                Status currentStatus = onlineList.get(i).user.getStatus();
                userList.get(i).setStatus(currentStatus);
            }
            else{
                User newUser = onlineList.get(i).user;
                userList.add(newUser);
                FileLogHandler.writeObjectToFile(newUser);
            }
        }
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
