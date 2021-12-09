package Server.BLL;

import Enity.Email;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

public class TaskServer extends TimerTask {
    private Object o;
    public TaskServer(Object o){
        this.o=o;
        //Constructor
    }

    public void run() {
        try {
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

        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
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
