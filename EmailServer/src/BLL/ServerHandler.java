package BLL;

import DAL.EmailServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ServerHandler extends Thread {
    //DECLARE VARIABLES USED IN CLASS
    public static ObjectInputStream objectIn;
    public static ObjectOutputStream objectOut;

    //MULTITHREADED METHOD DEFINITION
    public synchronized void run() {
        System.out.println("ServerHandler");
        do {
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();
            switch (text.toLowerCase()) {
                case "test":
                    System.out.println("Test");
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
