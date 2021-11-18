package BLL;

import DAL.EmailClient;
import Enity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import static DAL.EmailClient.link;

public class CheckUserClient {
    public static String CheckUserClient(String user, String pass) {
        Object o = null;
        try {
            User u = new User(user, pass);
            ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("C");
            oos.writeObject(u);
            ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
            o = ois.readObject();
            oos.flush();
            return (String) o;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (String) o;
    }

}

