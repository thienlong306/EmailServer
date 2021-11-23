package Test;

import Enity.Email;
import Enity.User;

import java.io.*;
import java.util.ArrayList;

import static BLL.HandlerClient.objectIn;

public class Test {
    private static ArrayList<User> list1=new ArrayList<>();
    private static ArrayList<User> list2=new ArrayList<>();
    static class MyObjectOutputStream extends ObjectOutputStream {
        // Constructor of ths class
        // 1. Parameterized constructor
        MyObjectOutputStream(OutputStream o) throws IOException
        {
            super(o);
        }

        // Method of this class
        public void writeStreamHeader() throws IOException
        {
            return;
        }
    }
    static ObjectInputStream fileIn;
    static ObjectOutputStream fileOu;
    static MyObjectOutputStream oos;
    public static void write() throws IOException {
        fileOu = new ObjectOutputStream(new FileOutputStream("src/Data/Test.dat"));
        User u1 = new User("long1","123");
        User u2 = new User("long2","123");
        fileOu.writeObject(u1);
        fileOu.writeObject(u2);
        fileOu.close();

    }

    public static void writeAdd() throws IOException {
        ObjectOutput oos = new MyObjectOutputStream(new FileOutputStream("src/Data/Test.dat",true));
        User u1 = new User("long3","123");
        User u2 = new User("long4","123");
        oos.writeObject(u1);
        oos.writeObject(u2);
        oos.close();

    }
    public static void readUser() throws IOException, ClassNotFoundException {
        FileInputStream fis=new FileInputStream("src/Data/Test.dat");
                    fileIn = new ObjectInputStream(fis);
            while (fis.available()!=0) {
                User temp = (User) fileIn.readObject();
//                System.out.println(temp.getUserName());
                list1.add(temp);
//                Email temp=(Email) fileIn.readObject();
//                System.out.println(temp.getSender()+temp.getRecipient()+temp.getStatus());
            }
    }
    public static void check() throws IOException, ClassNotFoundException {
        FileInputStream fis=new FileInputStream("src/Data/Test.dat");
        fileIn = new ObjectInputStream(fis);
        while (fis.available()!=0) {
            User temp = (User) fileIn.readObject();
//                System.out.println(temp.getUserName());
            list2.add(temp);
        }
        System.out.println(list1.get(1).getUserName());
        System.out.println(list2.get(1).getUserName());
        System.out.println(list1.equals(list2));
    }
    public static void readEmail() throws IOException, ClassNotFoundException {
        FileInputStream fis=new FileInputStream("src/Data/long2.dat");
        fileIn = new ObjectInputStream(fis);
        while (fis.available()!=0) {
            Object o = fileIn.readObject();
            if(o instanceof Email){
                Email temp = (Email) o;
                System.out.println(temp.getSender() + "-" + temp.getStatus());
            }
        }
    }
    public static void setEmail() throws IOException, ClassNotFoundException {
        ArrayList<Email> listEmailSent = new ArrayList<>();
        File fileSent = new File("src/Data/long2.dat");
        if(fileSent.length()!=0){
            FileInputStream fis = new FileInputStream(fileSent);
            ObjectInputStream fileInSent = new ObjectInputStream(fis);
            Email temp;
            while (fis.available() != 0){
                Object i= fileInSent.readObject();
                if(i instanceof Email){
                    temp = (Email) i;
                    listEmailSent.add(temp);
                }
            }
        }
            ObjectOutputStream fileOutSend = null;
            fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/long2.dat"));
            if(listEmailSent.size()!=0){
                for (int i = 0; i < listEmailSent.size(); i++) {
                    if(listEmailSent.get(i).getStatus().equals("Read")){
                        listEmailSent.get(i).setStatus("Recip");
                        fileOutSend.writeObject(listEmailSent.get(i));
                    }

                }
            }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        write();
//        writeAdd();
//        readEmail();
//        check();
        setEmail();
        }
}
