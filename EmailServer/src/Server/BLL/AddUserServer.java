package Server.BLL;

import Enity.User;
import Server.EmailServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static Server.BLL.CipherServer.decryptData;
import static Server.BLL.HandlerClient.objectIn;

public class AddUserServer {
    public static Socket client;

    public AddUserServer(Socket socket) {
        client = socket;
    }

    public static void AddUserServer() {
        try {
            ArrayList<User> listUser = EmailServer.getAllUser();
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            o=decryptData(o,client);
            String addUser = "ok";
            String pattern ="^[\\w.+\\-]+@sv\\.com$";
            String matcher = ((User)o).getUserName();
            if(!Pattern.matches(pattern, matcher))
            {
                addUser = "Error";
            }else if (o instanceof User) {
                for (User u : listUser) {
                    if (((User) o).getUserName().equals(u.getUserName()))
                        addUser = "Error";
                }
            }
            if(!addUser.equals("Error")) {
                listUser.add((User) o);
                addUser="ok";
            }
            oos.writeObject(addUser);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
