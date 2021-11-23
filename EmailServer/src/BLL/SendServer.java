package BLL;

import DAL.EmailServer;
import Enity.Email;
import Enity.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static BLL.HandlerClient.objectIn;

public class SendServer {
    public static Socket client;
    public SendServer(Socket socket) {
        client = socket;
    }
    public static void SendServer() {
        try {
            ArrayList<User> listUser = EmailServer.getAllUser();
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            String check = "not ok";

            ArrayList<Email> listEmailSent = new ArrayList<>();
            File fileSent = new File("src/Data/"+((Email) o).getSender()+".dat");
            if(fileSent.length()!=0){
                FileInputStream fis = new FileInputStream(fileSent);
                ObjectInputStream fileInSent = new ObjectInputStream(fis);
                Email temp;
                while (fis.available() != 0){
                    Object i = fileInSent.readObject();
                    if(i instanceof Email){
                        temp = (Email) i;
                        listEmailSent.add(temp);
                    }
                }
                fileInSent.close();
            }
            if(o instanceof Email) {
                ObjectOutputStream fileOutSend = null;
                fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/" + ((Email) o).getSender() + ".dat"));
                if(listEmailSent.size()!=0){
                    for (int i = 0; i < listEmailSent.size(); i++) {
                        fileOutSend.writeObject(listEmailSent.get(i));
                    }
                }
                fileOutSend.writeObject(o);
                fileOutSend.close();
                check="ok";
            }

            ArrayList<Email> listEmailRecip = new ArrayList<>();
            File fileRecip = new File("src/Data/"+((Email) o).getRecipient()+".dat");
            if(fileRecip.length()!=0){
                FileInputStream fis = new FileInputStream(fileRecip);
                ObjectInputStream fileInRecip = new ObjectInputStream(fis);
                Email temp;
                while (fis.available() != 0){
                    Object i = fileInRecip.readObject();
                    if(i instanceof Email){
                        temp = (Email) i;
                        listEmailRecip.add(temp);
                    }

                }
                fileInRecip.close();
            }
            if(o instanceof Email) {
                ObjectOutputStream fileOutRecip = null;
                fileOutRecip = new ObjectOutputStream(new FileOutputStream("src/Data/" + ((Email) o).getRecipient() + ".dat"));
                if(listEmailRecip.size()!=0){
                    for (int i = 0; i < listEmailRecip.size(); i++) {
                        fileOutRecip.writeObject(listEmailRecip.get(i));
                    }
                }
                ((Email) o).setStatus("Recip");
                fileOutRecip.writeObject(o);
                fileOutRecip.close();
                check="ok";
            }

//            if(o instanceof Email){
//                ObjectOutputStream fileOutRecip = new ObjectOutputStream(new FileOutputStream ("src/Data/"+((Email) o).getRecipient()+".dat"));
//                ((Email) o).setStatus("Recip");
//                fileOutRecip.writeObject(o);
//                fileOutRecip.close();
//
//                check="ok";
//            }
            oos.writeObject(check);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
