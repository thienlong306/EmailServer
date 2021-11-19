package BLL;
import DAL.EmailServer;
import Enity.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static BLL.HandlerClient.objectIn;
import static DAL.EmailServer.listUser;

public class CheckUserServer {
    public static Socket client;

    public CheckUserServer(Socket socket) {
        client = socket;
    }

    public static void CheckUserServer() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            String checkLogin = "Error";
            if (o instanceof User) {
                for (User u : listUser) {
                    if (((User) o).getUserName().equals(u.getUserName()) && ((User) o).getPassword().equals(u.getPassword()))
                        if(u.getStatus().equals("unlock")) {
                            checkLogin = "ok";
                        }
                        else checkLogin = "lock";
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
