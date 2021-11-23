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

            Object sent=o;
            syn(sent,((Email)sent).getSender());

            Object bcc=o;
            ((Email)bcc).setStatus("Recip");
            if(((Email)bcc).getBCC().contains(";")){
                String listBcc[]=((Email)bcc).getBCC().split(";");
                for(int i=0;i<listBcc.length;i++){
                    syn(bcc,listBcc[i]);
                }
            }else if(!((Email)bcc).getBCC().equals("")){
                syn(bcc,((Email)bcc).getBCC());
            }

            Object recip=o;
            ((Email)recip).setStatus("Recip");
            ((Email)recip).setBCC("");
            syn(recip,((Email)recip).getRecipient());

            Object cc=o;
            ((Email)cc).setStatus("Recip");
            ((Email)cc).setBCC("");
            if(((Email)cc).getCC().contains(";")){
                String listCc[] = ((Email)cc).getCC().split(";");
                for(int i=0;i< listCc.length;i++){
                    syn(cc,listCc[i]);
                }
            }else if(!((Email)cc).getCC().equals("")){
                syn(cc,((Email)cc).getCC());
            }
            String check="ok";
            oos.writeObject(check);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
