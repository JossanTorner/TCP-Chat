package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    static final int PORT = 8000;
    String serverIP = "25.16.11.103";


    public ChatWindow() throws UnknownHostException {
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

    public void setUpChat(){
        connectButton.addActionListener(e -> {
            try{
                socket = new Socket(serverIP, PORT);
                chatClient = new ChatClient(messageField, chatArea, socket, username);

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

    public static void main(String[] args) throws UnknownHostException {
        ChatWindow chatWindow = new ChatWindow();
    }
}