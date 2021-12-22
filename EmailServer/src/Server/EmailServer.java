package Server;

import Enity.KeyUser;
import Enity.User;
import Server.BLL.HandlerClient;
import Server.BLL.HandlerServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EmailServer {
    private static ServerSocket servSocket;
    private static final int PORT = 2233;
    public static ArrayList<User> listUser = new ArrayList<>();
    public static ArrayList<KeyUser> listkey=new ArrayList<>();
    private static ObjectInputStream fileIn;
    private static ObjectOutputStream fileOut;

        public static void main(String[] args) throws IOException, ClassNotFoundException {
        startUpServer();
        HandlerServer handlerServer = new HandlerServer();
        handlerServer.start();

       new EmailServer();
        try {
            servSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            Socket client = servSocket.accept();
            HandlerClient handler = new HandlerClient(client);
            handler.start();
        } while (true);
    }

    private static void startUpServer() {
        try {
            readInUserNamesFromServerFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void shutDownServer() {
        try {
            writeOutUsersToServerFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void readInUserNamesFromServerFile() throws IOException, ClassNotFoundException {
        fileIn = new ObjectInputStream(new FileInputStream("src/Data/User.dat"));
        try {
            do {
                User temp = (User) fileIn.readObject();
                listUser.add(temp);
            } while (true);
        } catch (EOFException e) {
            System.out.println("Đọc file thành công");
        }
        finally {
            if (fileIn != null) fileIn.close();
        }
    }

    //SENDS ALL SERVER USERNAMES TO FILE
    private static void writeOutUsersToServerFile() throws IOException {
        //SET UP NEW FILE OUTPUT STREAM
        fileOut = new ObjectOutputStream(new FileOutputStream("src/Data/User.dat"));
        //IF VECTOR HAS MORE THAN ZERO USERS
        try {
            for (int i = 0; i < listUser.size(); i++) {
                fileOut.writeObject(listUser.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) fileOut.close();
        }

    }

    //ACCESSOR METHODS
    public static ArrayList<User> getAllUser() {
        return listUser;
    }
}
