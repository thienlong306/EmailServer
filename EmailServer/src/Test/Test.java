package Test;

import Enity.User;

import java.io.*;
import java.util.ArrayList;

public class Test {
    public static  ArrayList<User> listUser = new ArrayList<>();
    public static void main(String[] args) {

        try {
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("src/Data/User.dat"));
            while (true){
                User temp = (User) fileIn.readObject();
                listUser.add(temp);
            }
//
//            ObjectOutputStream fileOu = new ObjectOutputStream(new FileOutputStream("src/Data/User.dat"));
//            User u1 = new User("long1","123");
//            User u2 = new User("long2","123");
//            fileOu.writeObject(u1);
//            fileOu.writeObject(u2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (EOFException e){
            for (User u:listUser){
                System.out.println(u.getUserName()+" "+u.getPassword()+" "+u.getData()+" "+u.getStatus());
            }
            System.out.println("Doc file thanh cong");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
