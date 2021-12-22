package Server.BLL;

import Enity.Email;
import Enity.User;
import Server.EmailServer;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.CipherServer.decryptData;
import static Server.BLL.HandlerClient.objectIn;
import static Server.EmailServer.listUser;

public class SendServer {
    public static Socket client;
    public SendServer(Socket socket) {
        client = socket;
    }
    public static void SendServer() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            Object o = objectIn.readObject();
            o=decryptData(o,client);
            String check="not ok";
            String checkContent="ok";
            String username="Tài khoản người nhận không đủ dung lượng: ";
            String checkuser="Tài khoản không hợp lệ: ";
            Object sent=o;

//            System.out.println(checkUser(((Email)sent).getRecipient()));
            if(!checkUser(((Email)sent).getRecipient())) checkuser+=((Email)sent).getRecipient()+",";
            String listCheck[];
            if(((Email) sent).getBCC().contains(";")){
            listCheck=((Email) sent).getBCC().split(";");
            for (int i=0;i<listCheck.length;i++){
                if (!checkUser(listCheck[i])) checkuser+=listCheck[i]+",";
            }}else if(!((Email) sent).getBCC().equals("")) {
                if(!checkUser(((Email)sent).getBCC())) checkuser+=((Email)sent).getBCC()+",";
            }
            if(((Email) sent).getCC().contains(";")){
            listCheck = ((Email) sent).getCC().split(";");
            for (int i=0;i<listCheck.length;i++){
                if (!checkUser(listCheck[i])) checkuser+=listCheck[i]+",";
            }}else if(!((Email) sent).getCC().equals("")) {
                if(!checkUser(((Email)sent).getCC())) checkuser+=((Email)sent).getCC()+",";
            }
            try {
                if (((Email)o).getSubject().equals("")||((Email)o).getContent().getText(0,((Email)o).getContent().getLength()).equals("")){
                    checkContent="Lỗi";
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            String listBcc[];
            String listCc[];
            if (!checkData(((Email) o).getRecipient()))
                username += ((Email) o).getRecipient() + " ";
            if (((Email) o).getBCC().contains(";")) {
                listBcc = ((Email) o).getBCC().split(";");
                for (int i = 0; i < listBcc.length; i++) {
                    if (!checkData(listBcc[i]))
                        username += listBcc[i] + " ";
                }
            } else if (!((Email) o).getBCC().equals("")) {
                if (!checkData(((Email) o).getBCC()))
                    username += ((Email) o).getBCC() + " ";
            }
            if (((Email) o).getCC().contains(";")) {
                listCc = ((Email) o).getCC().split(";");
                for (int i = 0; i < listCc.length; i++) {
                    if (!checkData(listCc[i]))
                       username += listCc[i] + " ";
                }
            } else if (!((Email) o).getCC().equals("")) {
                if (!checkData(((Email) o).getCC()))
                    username += ((Email) o).getCC() + " ";
            }

            if(checkuser.equals("Tài khoản không hợp lệ: ")&&checkContent.equals("ok")&&username.equals("Tài khoản người nhận không đủ dung lượng: ")){
                if (checkData(((Email) sent).getSender())) {
                    check = "ok";
                    syn(sent, ((Email) sent).getSender());

                    Object bcc = o;
                    ((Email) bcc).setStatus("Recip");
                    if (((Email) bcc).getBCC().contains(";")) {
                        listBcc = ((Email) o).getCC().split(";");
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
                } else check = "Bạn không đủ dung lượng";
            }
            if(checkContent.equals("Lỗi")) oos.writeObject(checkContent);
            else if(!checkuser.equals("Tài khoản không hợp lệ: ")) oos.writeObject(checkuser);
            else if (username.equals("Tài khoản người nhận không đủ dung lượng: "))
            oos.writeObject(check);
            else oos.writeObject(username);

            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    public static boolean checkData(String username) {
        try {
            File file = new File("src/Data/"+username+".dat");
            long megabytes=file.length()/(1024*1024);
            for (int i=0; i<listUser.size();i++){
                if(listUser.get(i).getUserName().equals(username))
                    if(megabytes>listUser.get(i).getData()) return false;
                    else return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean checkUser(String username) {
        for (int i=0;i<listUser.size();i++){
            if (listUser.get(i).getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
