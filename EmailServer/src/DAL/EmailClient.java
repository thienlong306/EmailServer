package DAL;

import Enity.User;
import GUI.Login;

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

    public static void main(String[] args) {
        Connect();
        new Login().setVisible(true);
    }

}
