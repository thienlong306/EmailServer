package Server.BLL;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandlerClient   extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    private Socket client;
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;
    public static String usermain;
    public static SecretKey key;
    public static GenerateKeys sv;
    protected static int indexkey=-1;
    //DECLARE CLASS CONSTRUCTOR
    public HandlerClient(Socket socket) {
        //GET SOCKET REFERENCE FROM SERVER
        client = socket;
    }
    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
//        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Connection");
        try {
            new CipherServer(client).CipherServer();
            do {
                objectIn = new ObjectInputStream(client.getInputStream());
                String option = (String) objectIn.readObject();
                switch (option) {
                    case "C":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Login");
                        new CheckUserServer(client).CheckUserServer();
                        break;
                    case "A":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Add User");
                        new AddUserServer(client).AddUserServer();
                        break;
                    case "S":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Send");
                        new SendServer(client).SendServer();
                        break;
                    case "SC":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Schedule");
                        new ScheduleServer(client).ScheduleServer();
                        break;
                    case "L":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " ListEmail");
                        new ListServer(client).ListServer();
                        break;
                    case "R":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Read");
                        new ReadingServer(client).ReadingServer();
                        break;
                    case "SP":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Spam");
                        new SpamServer(client).SpamServer();
                        break;
                    case "D":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Delete");
                        new DeleteServer(client).DeleteServer();
                        break;
                    case "Info":
//                        System.out.println(client.getInetAddress().getHostName()+":"+client.getPort() + " Info");
                        new InfoServer(client).InfoServer();
                        break;
                    default:
                        System.out.println("Không xác định");
                }
            } while (true);
        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(client.getInetAddress().getHostName() + " Disconnect");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
