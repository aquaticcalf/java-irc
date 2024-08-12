import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URISyntaxException;

public class IRCClient {
    private static JTextArea messageArea;
    private static JTextField inputField;
    private static JTextField serverField;
    private static JTextField portField;
    private static JTextField nickField;
    private static JTextField userField;
    private static JTextField channelField;
    private static PrintWriter out;
    private static Socket socket;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("TextField.background", Color.LIGHT_GRAY);
            UIManager.put("TextField.foreground", Color.BLACK);
            UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("TextArea.background", Color.WHITE);
            UIManager.put("TextArea.foreground", Color.BLACK);
            UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("Button.background", Color.DARK_GRAY);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
            UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("IRC Client");
        frame.setLayout(new BorderLayout());

        try {
            URI uri = new URI("https://aqclf.xyz/sam.png");
            URL url = uri.toURL();
            Image image = ImageIO.read(url);
            frame.setIconImage(image);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        JPanel connectionPanel = new JPanel(new GridLayout(6, 2));
        connectionPanel.add(new JLabel("Server:"));
        serverField = new JTextField("irc.example.com");
        connectionPanel.add(serverField);

        connectionPanel.add(new JLabel("Port:"));
        portField = new JTextField("6667");
        connectionPanel.add(portField);

        connectionPanel.add(new JLabel("Nickname:"));
        nickField = new JTextField("your_nickname");
        connectionPanel.add(nickField);

        connectionPanel.add(new JLabel("Username:"));
        userField = new JTextField("your_username");
        connectionPanel.add(userField);

        connectionPanel.add(new JLabel("Channel:"));
        channelField = new JTextField("#your_channel");
        connectionPanel.add(channelField);

        JButton connectButton = new JButton("Connect");
        connectionPanel.add(connectButton);

        frame.add(connectionPanel, BorderLayout.NORTH);

        messageArea = new JTextArea(20, 50);
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private static void connectToServer() {
        String server = serverField.getText();
        int port = Integer.parseInt(portField.getText());
        String nick = nickField.getText();
        String user = userField.getText();
        String channel = channelField.getText();

        try {
            socket = new Socket(server, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("NICK " + nick);
            out.println("USER " + user + " 0 * :" + user);
            out.println("JOIN " + channel);

            new Thread(() -> {
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        messageArea.append(line + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage() {
        String message = inputField.getText();
        if (message != null && !message.isEmpty()) {
            out.println(message);
            inputField.setText("");
        }
    }
}