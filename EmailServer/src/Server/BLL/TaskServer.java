package Server.BLL;

import Enity.Email;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import static Server.BLL.SendServer.checkData;
import static Server.EmailServer.listUser;

public class TaskServer extends TimerTask {
    private Object o;
    public TaskServer(Object o){
        this.o=o;
        //Constructor
    }

    public void run() {
        try {
            String listBcc[];
            String listCc[];
            Object bcc = o;
            ((Email) bcc).setStatus("Recip");
            if (((Email) bcc).getBCC().contains(";")) {
                listBcc = ((Email) o).getBCC().split(";");
                ((Email) bcc).setBCC("Bạn");
                for (int i = 0; i < listBcc.length; i++) {
                    if (checkData(listBcc[i]))
                        syn(bcc, listBcc[i]);
                }
            } else if (!((Email) bcc).getBCC().equals("")) {
                if (checkData(((Email) bcc).getBCC())) {
                    String tmp = ((Email) bcc).getBCC();
                    ((Email) bcc).setBCC("Bạn");
                    syn(bcc, tmp);
                }
            }

            Object recip = o;
            ((Email) recip).setStatus("Recip");
            ((Email) recip).setBCC("");
            if (checkData(((Email) recip).getRecipient()))
                syn(recip, ((Email) recip).getRecipient());

            Object cc = o;
            ((Email) cc).setStatus("Recip");
            ((Email) cc).setBCC("");
            if (((Email) cc).getCC().contains(";")) {
                listCc = ((Email) cc).getCC().split(";");
                for (int i = 0; i < listCc.length; i++) {
                    if (checkData(listCc[i]))
                        syn(cc, listCc[i]);
                }
            } else if (!((Email) cc).getCC().equals("")) {
                if (checkData(((Email) cc).getCC()))
                    syn(cc, ((Email) cc).getCC());
            }
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
    public static void syn(Object o,String username) throws IOException, ClassNotFoundException {
        ArrayList<Email> listEmail = new ArrayList<>();

        String checkSpam=((Email)o).getSender();
        ArrayList<String> tmp=new ArrayList<>();
        for (int i=0;i<listUser.size();i++){
            if(listUser.get(i).getUserName().equals(username)){
                tmp=listUser.get(i).getListSpam();
                break;
            }
        }
        if (tmp.contains(checkSpam)) ((Email)o).setStatus("Spam");

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
