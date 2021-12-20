package Server.BLL;

import Enity.Email;
import Server.EmailServer;

import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static Server.EmailServer.listUser;


public class HandlerServer extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
        System.out.println("ServerHandler");
        System.out.println("1. lock user");
        System.out.println("2. unlock user");
        System.out.println("3. setdata user data");
        System.out.println("4. send user subject content");
        System.out.println("5. send all subject content");
        do {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine().toLowerCase();
            String[] text = input.split(" ");
            switch (text[0]) {
                case "test":
                    System.out.println("Test");
                    break;
                case "lock":
                    for (int i = 0; i < listUser.size(); i++) {
                        if (listUser.get(i).getUserName().equals(text[1])) {
                            listUser.get(i).setStatus("lock");
                            System.out.println("Đã khóa " + listUser.get(i).getUserName());
                            break;
                        }
                    }
                    break;
                case "unlock":
                    for (int i = 0; i < listUser.size(); i++) {
                        if (listUser.get(i).getUserName().equals(text[1])) {
                            listUser.get(i).setStatus("unlock");
                            System.out.println("Đã mở khóa " + listUser.get(i).getUserName());
                            break;
                        }
                    }
                    break;
                case "setdata":
                    String check="not ok";
                    for (int i = 0; i < listUser.size(); i++) {
                        if (listUser.get(i).getUserName().equals(text[1])) {
                            listUser.get(i).setData(Integer.parseInt(text[2]));
                            System.out.println("Đã set data " + listUser.get(i).getUserName());
                            check="ok";
                            break;
                        }
                    }
                    if(!check.equals("ok")) System.out.println("Không tìm thấy user");
                    break;
                case "send":
                    if(!text[1].equals("all")) {
                        Email temp = new Email();
                        temp.setSender("admin");
                        temp.setRecipient(text[1]);
                        temp.setSubject(text[2]);
                        temp.setStatus("Recip");
                        StyledDocument doc = new DefaultStyledDocument();
                        Style style = doc.addStyle("StyleName", null);
                        StyleConstants.setForeground(style, Color.BLACK);
                        try {
                            doc.insertString(doc.getLength(), text[3], style);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        temp.setContent(doc);

                        try {
                            syn(temp,text[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        for (int i = 0; i < listUser.size(); i++) {
                            Email temp = new Email();
                            temp.setSender("admin");
                            temp.setRecipient(listUser.get(i).getUserName());
                            temp.setSubject(text[2]);
                            temp.setStatus("Recip");
                            StyledDocument doc = new DefaultStyledDocument();
                            Style style = doc.addStyle("StyleName", null);
                            StyleConstants.setForeground(style, Color.BLACK);
                            try {
                                doc.insertString(doc.getLength(), text[3], style);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            temp.setContent(doc);

                            try {
                                syn(temp,listUser.get(i).getUserName());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case "bye":
                    EmailServer.shutDownServer();
                    break;
                default:
                    System.out.println("Error");
            }
        } while (true);
    }
    public static void syn(Email o,String username) throws IOException, ClassNotFoundException {
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
