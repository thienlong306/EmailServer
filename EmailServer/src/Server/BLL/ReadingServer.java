package Server.BLL;

import Enity.Email;
import Enity.User;
import Server.EmailServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.HandlerClient.objectIn;

public class ReadingServer {
    public static Socket client;
    public static ArrayList<Email> listEmail=new ArrayList<>();
    public ReadingServer(Socket socket) {
        client = socket;
    }

    public static void ReadingServer() throws IOException, ClassNotFoundException {

        ArrayList<User> listUser = EmailServer.getAllUser();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Object o = objectIn.readObject();
        String username=(String)o;
        String check = "ok";
        ArrayList<Email> listEmailSent = new ArrayList<>();
        File fileSent = new File("src/Data/"+(String) o+".dat");
        if(fileSent.length()!=0){
            FileInputStream fis = new FileInputStream(fileSent);
            ObjectInputStream fileInSent = new ObjectInputStream(fis);
            Email temp;
            while (fis.available() != 0){
                Object i= fileInSent.readObject();
                if(i instanceof Email){
                    temp = (Email) i;
                    listEmailSent.add(temp);
                }
            }
        }
        o=objectIn.readObject();
        if(o instanceof Integer) {
            ObjectOutputStream fileOutSend = null;
            listEmailSent.get((int)o).setStatus("Read");
            fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/" + username + ".dat"));
            if(listEmailSent.size()!=0){
                for (int i = 0; i < listEmailSent.size(); i++) {
                    fileOutSend.writeObject(listEmailSent.get(i));
                }
            }
        }
        oos.writeObject(check);
    }
}
