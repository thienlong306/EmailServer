package BLL;

import DAL.EmailServer;
import Enity.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Scanner;

import static DAL.EmailServer.listUser;


public class HandlerServer extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
        System.out.println("ServerHandler");
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
                case "bye":
                    EmailServer.shutDownServer();
                    break;
                default:
                    System.out.println("Error");
            }
        } while (true);
    }
}
