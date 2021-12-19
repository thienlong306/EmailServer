package Test;

import Enity.Email;
import Enity.User;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.regex.Pattern;

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
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertByteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    public static void write() throws IOException {
        fileOu = new ObjectOutputStream(new FileOutputStream("src/Data/User.dat"));
        String pass = getMD5("admin");
        User u1 = new User("admin@sv.com",pass);
        fileOu.writeObject(u1);
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
        FileInputStream fis=new FileInputStream("src/Data/User.dat");
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
        FileInputStream fis=new FileInputStream("src/Data/t1@sv.com.dat");
        fileIn = new ObjectInputStream(fis);
        while (fis.available()!=0) {
            Object o = fileIn.readObject();
            if(o instanceof Email){
                Email temp = (Email) o;
                System.out.println(temp.getSender() + "-" + temp.getStatus());
            }
        }
    }
    public static void readUserData() throws IOException, ClassNotFoundException {
        FileInputStream fis=new FileInputStream("src/Data/User.dat");
        fileIn = new ObjectInputStream(fis);
        while (fis.available()!=0) {
            Object o = fileIn.readObject();
            if(o instanceof User){
                User temp = (User) o;
                System.out.println(temp.getUserName() + "-" + temp.getData());
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
                    if(listEmailSent.get(i).getStatus().equals("Recip")){
                        listEmailSent.get(i).setStatus("Delete");
                        fileOutSend.writeObject(listEmailSent.get(i));
                    }

                }
            }
    }
    public void schduled(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Calendar.DAY_OF_WEEK,
                Calendar.MONDAY
        );
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



        Timer time = new Timer(); // Instantiate Timer Object

        // Start running the task on Monday at 15:40:00, period is set to 8 hours
        // if you want to run the task immediately, set the 2nd parameter to 0
//        time.schedule(new CustomTask(), calendar.getTime(), TimeUnit.HOURS.toMillis(8));
    }
    public static boolean checkData(String username) throws IOException, ClassNotFoundException {
        readUser();
        File file = new File("src/Data/"+username+".dat");
        long megabytes=file.length()/(1024*1024);
        for (int i=0; i<list1.size();i++){
            if(list1.get(i).getUserName().equals(username))
                if(megabytes>list1.get(i).getData()) return false;
                else return true;
        }
        return false;
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        write();
//        writeAdd();
//        readEmail();
//        check();
//        setEmail();
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("long1");
//        System.out.println(temp.get(0));
        readUserData();
//     readUser();
//        System.out.println(list1.get(0).getListSpam());
//        System.out.println(list1.contains("t@sv.com"));
//     for (int i=0;i<list1.size();i++)
//         System.out.println(list1.get(i).getUserName()+"-"+list1.get(i).getPassword());
//        ArrayList<String> test=new ArrayList<>();
//        test.add("1");
//        test.add("2");
//        test.add("3");
//
//        System.out.println(test.remove("1"));
//        System.out.println(test);
//        String pattern ="^[\\w.+\\-]+@sv\\.com$";
//        String matcher ="long1asdas@sv.com";
//        System.out.println(Pattern.matches(pattern, matcher));

    }

}
