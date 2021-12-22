package Server.BLL;
import Enity.User;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static Server.BLL.CipherServer.decryptData;
import static Server.BLL.HandlerClient.objectIn;
import static Server.BLL.HandlerClient.usermain;
import static Server.EmailServer.listUser;

public class CheckUserServer {
    public static Socket client;

    public CheckUserServer(Socket socket) {
        client = socket;
    }

    public static void CheckUserServer() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            o=decryptData(o,client);
            String checkLogin = "Error";
            if (o.equals("LG")){
                System.out.println(usermain+" Logout");
            }else {
                if (o instanceof User) {
                    for (User u : listUser) {
                        if (((User) o).getUserName().equals(u.getUserName()) && ((User) o).getPassword().equals(u.getPassword()))
                            if (u.getStatus().equals("unlock")) {
                                checkLogin = "ok";
                                usermain = ((User) o).getUserName();
                            } else checkLogin = "lock";
                    }
                }
            }
            if(checkLogin.equals("ok")) System.out.println(((User) o).getUserName()+" Login");
            oos.writeObject(checkLogin);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
