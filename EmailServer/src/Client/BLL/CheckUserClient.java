package Client.BLL;

import Enity.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;

public class CheckUserClient {
    public static String CheckUserClient(String user, String pass) {
        Object o = null;
        try {
            pass=getMD5(pass);
            User u = new User(user, pass);
            Object encry=encryptData(u);
//            System.out.println(encry);
            ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("C");
            oos.writeObject(encry);
            ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
            o = ois.readObject();
            oos.flush();
            return (String) o;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Mất kết nối máy chủ");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Mất kết nối máy chủ");
        }
        return (String) o;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}

