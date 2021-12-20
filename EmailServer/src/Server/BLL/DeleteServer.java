package Server.BLL;

import Enity.Email;
import Enity.User;
import Server.EmailServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.HandlerClient.objectIn;

public class DeleteServer {
    public static Socket client;
    public static ArrayList<Email> listEmail=new ArrayList<>();
    public DeleteServer(Socket socket) {
        client = socket;
    }

    public static void DeleteServer() throws IOException, ClassNotFoundException {
        ArrayList<User> listUser = EmailServer.getAllUser();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Object o = objectIn.readObject();
        String username=(String)o;
        System.out.println((String)o);
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
        if(o instanceof Integer) {
            ObjectOutputStream fileOutSend = null;
            if(listEmail.get((int)o).getStatus().contains("Delete"))
            {
                listEmail.remove((int)o);
            }else {
                listEmail.get((int)o).setStatus(listEmail.get((int)o).getStatus()+"-Delete");
            }
            fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/" + username + ".dat"));
            if(listEmail.size()!=0){
                for (int i = 0; i < listEmail.size(); i++) {
                    fileOutSend.writeObject(listEmail.get(i));
                }
            }
        }else{
            ObjectOutputStream fileOutSend = null;
            String w[]=((String)o).split("-");
            String s[]=listEmail.get(Integer.parseInt(w[0])).getStatus().split("-");
            listEmail.get(Integer.parseInt(w[0])).setStatus(s[0]);
            fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/" + username + ".dat"));
            if(listEmail.size()!=0){
                for (int i = 0; i < listEmail.size(); i++) {
                    fileOutSend.writeObject(listEmail.get(i));
                }
            }
        }
        oos.writeObject(check);
    }
}
