package Test;

import Enity.User;

import java.io.*;
import java.util.ArrayList;

public class Test {
    public static  ArrayList<User> listUser = new ArrayList<>();
    public static void main(String[] args) {

        try {
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("src/Data/User.dat"));
//            ObjectOutputStream fileOu = new ObjectOutputStream(new FileOutputStream("src/Data/User.dat"));
            while (true){
                User temp = (User) fileIn.readObject();
                listUser.add(temp);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){
            for (User u:listUser){
                System.out.println(u.getUserName()+u.getPassword()+u.getData());
            }
            System.out.println("Doc file thanh cong");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
