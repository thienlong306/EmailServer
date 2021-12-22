package Client.BLL;

import Enity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;

public class AddUserClient {
    public static boolean AddUserClient(String user, String pass,String name) {
        try {
            pass = getMD5(pass);
            User u = new User(user,pass,name);
            Object encry=encryptData(u);
            ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("A");
            oos.writeObject(encry);
            ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
            Object o = ois.readObject();
            System.out.println();
            oos.flush();
            if (o.equals("ok")) return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        return false;
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
