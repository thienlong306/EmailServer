package BLL;
import DAL.EmailServer;
import Enity.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static BLL.ClientHandler.objectIn;

public class CheckUserServer {
    public static Socket client;

    public CheckUserServer(Socket socket) {
        client = socket;
    }

    public static void CheckUserServer() {
        try {
            ArrayList<User> listUser = EmailServer.getAllUser();
            System.out.println("CheckUserServer");
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            String checkLogin = "Error";
            if (o instanceof User) {
                for (User u : listUser) {
                    if (((User) o).getUserName().equals(u.getUserName()) && ((User) o).getPassword().equals(u.getPassword()))
                        checkLogin = "ok";
                }
            }
            oos.writeObject(checkLogin);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
