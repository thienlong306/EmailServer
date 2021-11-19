package BLL;

import DAL.EmailClient;
import DAL.EmailServer;
import Enity.Email;
import Enity.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
            String addUser = "not ok";
            if(o instanceof Email){
                System.out.println(((Email) o).getSender()+((Email) o).getRecipient()+((Email) o).getCC()+((Email) o).getBCC()+((Email) o).getSubject()+((Email) o).getContent()+((Email) o).getAttachment());
                ObjectOutputStream fileOutSend = new ObjectOutputStream(new FileOutputStream ("src/Data/"+((Email) o).getSender()+".dat"));
                ObjectOutputStream fileOutRecip = new ObjectOutputStream(new FileOutputStream ("src/Data/"+((Email) o).getRecipient()+".dat"));
                fileOutSend.writeObject(o);
                fileOutRecip.writeObject(o);
                addUser="ok";
                fileOutSend.close();
                fileOutRecip.close();
            }
            oos.writeObject(addUser);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
