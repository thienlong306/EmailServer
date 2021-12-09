package Server.BLL;

import Enity.Email;
import Enity.User;
import Server.EmailServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

import static Server.BLL.HandlerClient.objectIn;

public class ScheduleServer {
    public static Socket client;
    public static ArrayList<Email> listEmail = new ArrayList<>();

    public ScheduleServer(Socket socket) {
        client = socket;
    }

    public static void ScheduleServer() throws IOException, ClassNotFoundException {
        ArrayList<User> listUser = EmailServer.getAllUser();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Object o = objectIn.readObject();
        Object email =o;
        Object sent=o;
        syn(sent,((Email)sent).getSender());
        o = objectIn.readObject();
        java.util.Timer time = new Timer(); // Instantiate Timer Object
        time.schedule(new TaskServer(email), ((Calendar)o).getTime());
        oos.writeObject("ok");
    }
    public static void syn(Object o,String username) throws IOException, ClassNotFoundException {
        ArrayList<Email> listEmail = new ArrayList<>();
        File file = new File("src/Data/"+username+".dat");
        if(file.length()!=0){
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fis);
            Email temp;
            while (fis.available() != 0){
                Object i = objectIn.readObject();
                if(i instanceof Email){
                    temp = (Email) i;
                    listEmail.add(temp);
                }
            }
            objectIn.close();
        }
        if(o instanceof Email) {
            ObjectOutputStream objectOut= null;
            objectOut = new ObjectOutputStream(new FileOutputStream("src/Data/" + username + ".dat"));
            if(listEmail.size()!=0){
                for (int i = 0; i < listEmail.size(); i++) {
                    objectOut.writeObject(listEmail.get(i));
                }
            }
            objectOut.writeObject(o);
            objectOut.close();
        }
    }
}
