package Client.BLL;

import Enity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static Client.EmailClient.link;

public class AddUserClient {
    public static boolean AddUserClient(String user, String pass) {
        try {
            User u = new User(user, pass);
            ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("A");
            oos.writeObject(u);
            ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
            Object o = ois.readObject();
            System.out.println();
            oos.flush();
            if (o.equals("ok")) return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
