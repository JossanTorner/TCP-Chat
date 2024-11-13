package client;
import server.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class ChatWindow extends JFrame {

    JTextArea chatArea;
    JScrollPane chatScroll;
    JButton connectButton;
    JTextField messageField;
    Socket socket;
    ChatClient chatClient;
    String username;

    JList<String> membersList;
    DefaultListModel<String> membersModel;

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

        membersModel = new DefaultListModel<>();
        membersList = new JList<>(membersModel); // Use DefaultListModel for members list
        JScrollPane membersScroll = new JScrollPane(membersList);
        membersScroll.setPreferredSize(new Dimension(100, 0));

        setUpChat();

        windowEvents();
        this.add(connectButton, BorderLayout.NORTH);
        this.add(chatScroll, BorderLayout.CENTER);
        this.add(messageField, BorderLayout.SOUTH);
        this.add(membersScroll, BorderLayout.EAST);

        this.setDefaultCloseOperation(ChatWindow.EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public void updateMemberDisplay(List<User> userList) {
        membersModel.clear();
        for (User user : userList) {
            membersModel.addElement(user.getUsername() + " - " + user.getStatus());
        }
    }

    public void setUpChat() throws IOException {
        connectButton.addActionListener(e -> {

            try{

                socket = new Socket(InetAddress.getLocalHost(), PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                //här sätter jag in username i requesten, men den är NULL.. eller kanske inte

                Request connectRequest = new Request(eRequest.CONNECT, username, "");
                out.writeObject(connectRequest);
                Object received = in.readObject();
                if(received instanceof Response response){
                    chatArea.append(response.getMessage());
                }


                chatClient = new ChatClient(messageField, chatArea, socket, username, out, in, this);

                messageField.setEnabled(true);
                messageField.addActionListener(chatClient);
                connectButton.setEnabled(false);

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException ex) {
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