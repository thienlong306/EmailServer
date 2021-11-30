package Server.BLL;

import Enity.Email;
import Enity.User;
import Server.EmailServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

import static Server.BLL.HandlerClient.objectIn;

public class ScheduleServer {
    public static Socket client;
    public static ArrayList<Email> listEmail = new ArrayList<>();

    public ScheduleServer(Socket socket) {
        client = socket;
    }

    public static void ScheduleServer() throws IOException, ClassNotFoundException {
        ArrayList<User> listUser = EmailServer.getAllUser();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        Object o = objectIn.readObject();
        Object email =o;
        o = objectIn.readObject();
        java.util.Timer time = new Timer(); // Instantiate Timer Object
        time.schedule(new TaskServer(email), ((Calendar)o).getTime());
        oos.writeObject("ok");
    }
}
