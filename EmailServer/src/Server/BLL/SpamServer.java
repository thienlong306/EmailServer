package Server.BLL;

import Enity.Email;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.HandlerClient.objectIn;

public class SpamServer {
    public static Socket client;
    public static ArrayList<Email> listEmail=new ArrayList<>();
    public SpamServer(Socket socket) {
        client = socket;
    }

    public static void SpamServer() throws IOException, ClassNotFoundException {

//        ArrayList<User> listUser = EmailServer.getAllUser();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Object o = objectIn.readObject();
        String username=(String)o;
        String check = "ok";

        ArrayList<Email> listEmail = new ArrayList<>();
        File fileSent = new File("src/Data/"+(String) o+".dat");
        if(fileSent.length()!=0){
            FileInputStream fis = new FileInputStream(fileSent);
            ObjectInputStream fileInSent = new ObjectInputStream(fis);
            Email temp;
            while (fis.available() != 0){
                Object i= fileInSent.readObject();
                if(i instanceof Email){
                    temp = (Email) i;
                    listEmail.add(temp);
                }
            }
        }

        o=objectIn.readObject();
        for (int i=0;i<listEmail.size();i++){
            if(listEmail.get(i).getSender().equals((String) o)){
                if(listEmail.get(i).getStatus().equals("Spam")){
                    listEmail.get(i).setStatus("Read");
                }else
                {
                    listEmail.get(i).setStatus("Spam");
                }
            }
        }

            ObjectOutputStream fileOut = null;
            fileOut = new ObjectOutputStream(new FileOutputStream("src/Data/" + username + ".dat"));
            if(listEmail.size()!=0){
                for (int i = 0; i < listEmail.size(); i++) {
                    fileOut.writeObject(listEmail.get(i));
                }
            }
        oos.writeObject(check);
    }
}
