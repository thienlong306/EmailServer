package BLL;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerClient   extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    private Socket client;
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    //DECLARE CLASS CONSTRUCTOR
    public HandlerClient(Socket socket) {
        //GET SOCKET REFERENCE FROM SERVER
        client = socket;
    }

    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Connection");
        try {
            do {
                System.out.println("HandlerClient");
                objectIn = new ObjectInputStream(client.getInputStream());
                String option = (String) objectIn.readObject();
                switch (option) {
                    case "C":
                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Login");
                        new CheckUserServer(client).CheckUserServer();
                        break;
                    case "A":
                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Add User");
                        new AddUserServer(client).AddUserServer();
                        break;
                    case "S":
                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Send");
                        new SendServer(client).SendServer();
                        break;
                    case "L":
                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " ListEmail");
                        new ListServer(client).ListServer();
                        break;
                    case "R":
                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Read");
                        new ReadingServer(client).ReadingServer();
                        break;
                    default:
                        System.out.println("Không xác định");
                }
            } while (true);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(client.getInetAddress().getHostName() + " Disconnect");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
