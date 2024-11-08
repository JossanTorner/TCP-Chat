package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatWindow extends JFrame implements ActionListener {

    JTextArea chatArea;
    JScrollPane chatScroll;
    JButton connectButton;
    JTextField messageField;

    public ChatWindow() throws UnknownHostException {
        super("Chat App");
        this.setLayout(new BorderLayout());

        InetAddress myAddress = InetAddress.getLocalHost();

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        chatScroll = new JScrollPane(chatArea);

        connectButton = new JButton("Connect");
        messageField = new JTextField();
        //messageField.addActionListener(new Sender("25.16.67.47", 1234, messageField, chatArea));
        //connectButton.addActionListener(this);

        this.add(connectButton, BorderLayout.NORTH);
        this.add(chatScroll, BorderLayout.CENTER);
        this.add(messageField, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(ChatWindow.EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

   @Override
    public void actionPerformed(ActionEvent e) {
       /* Listener listener = null;
        try {
            listener = new Listener("25.16.11.103", 1234, chatArea);
            Thread listenerThread = new Thread(listener);
            listenerThread.start();
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }*/
    }
}