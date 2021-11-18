package BLL;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    private Socket client;
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    //DECLARE CLASS CONSTRUCTOR
    public ClientHandler(Socket socket) {
        //GET SOCKET REFERENCE FROM SERVER
        client = socket;
    }

    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Connection");
        try {
            do {
                objectIn = new ObjectInputStream(client.getInputStream());
                String option = (String) objectIn.readObject();
                switch (option) {
                    case "C":
                        new CheckUserServer(client).CheckUserServer();
                        break;
                    case "A":
                        new AddUserServer(client).AddUserServer();
                        break;
                    default:
                        System.out.println("Không xác định");
                }
            } while (true);
        } catch (IOException e) {
            System.out.println(client.getInetAddress().getHostName() + " Disconnect");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
