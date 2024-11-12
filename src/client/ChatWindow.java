package client;
import server.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatWindow extends JFrame {

    JTextArea chatArea;
    JScrollPane chatScroll;
    JButton connectButton;
    JTextField messageField;
    Socket socket;
    ChatClient chatClient;
    String username;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    static final int PORT = 8000;
    String serverIP = "25.16.11.103";


    public ChatWindow() throws IOException {
        super("Chat App");
        this.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        chatScroll = new JScrollPane(chatArea);

        connectButton = new JButton("Connect");
        messageField = new JTextField();
        messageField.setEnabled(false);

        setUpChat();

        windowEvents();
        this.add(connectButton, BorderLayout.NORTH);
        this.add(chatScroll, BorderLayout.CENTER);
        this.add(messageField, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(ChatWindow.EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public void setUpChat() throws IOException {
        connectButton.addActionListener(e -> {
            try{
                socket = new Socket(InetAddress.getLocalHost(), PORT);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                Request connectRequest = new Request(eRequest.CONNECT, username, "");
                outputStream.writeObject(connectRequest);
                Object received = inputStream.readObject();
                if(received instanceof Response response){
                    chatArea.append(response.getMessage());
                }

                chatClient = new ChatClient(messageField, chatArea, socket, username, outputStream, inputStream);
                messageField.setEnabled(true);
                messageField.addActionListener(chatClient);
                connectButton.setEnabled(false);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void windowEvents(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (socket != null){
                    chatClient.closeConnection();
                }
            }
            @Override
            public void windowOpened(WindowEvent e) {
                username = JOptionPane.showInputDialog("Name: ");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        ChatWindow chatWindow = new ChatWindow();
    }
}