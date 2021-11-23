package DAL;

import Enity.User;
import GUI.Login;
import GUI.Main;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmailClient {
    public static InetAddress host;
    public static final int PORT = 1234;
    public static Socket link;
    public static User user;
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 16;

    public static void Connect() {
        try {
            host = InetAddress.getLocalHost();
            link = new Socket(host, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        Connect();
        UIManager.put("TextPane.font",
                new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

}
