package Server.BLL;

import Enity.Email;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.CipherServer.decryptData;
import static Server.BLL.HandlerClient.objectIn;

public class ListServer {
    public static Socket client;
    public static ArrayList<Email> listEmail=new ArrayList<>();
    public static ObjectOutputStream oos;
    public ListServer(Socket socket) {
        client = socket;
    }

    public static void ListServer() throws IOException {
        try {
            Object o = objectIn.readObject();
            o=decryptData(o,client);
            oos = new ObjectOutputStream(client.getOutputStream());
            FileInputStream fis = new FileInputStream("src/Data/"+(String)o+".dat");
            ObjectInputStream fileIn = new ObjectInputStream(fis);
            while (fis.available() !=0) {
                o = fileIn.readObject();
                if(o instanceof Email){
                    Email temp = (Email) o;
                    listEmail.add(temp);
                }
            }
            oos.writeObject(listEmail);
            listEmail.clear();

        } catch (IOException e) {
            oos.writeObject(listEmail);
            listEmail.clear();
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
