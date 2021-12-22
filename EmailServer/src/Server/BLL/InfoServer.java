package Server.BLL;

import Enity.Email;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static Server.BLL.CipherServer.decryptData;
import static Server.BLL.HandlerClient.objectIn;
import static Server.EmailServer.listUser;

public class InfoServer {
    public static Socket client;
    public static ArrayList<Email> listEmail=new ArrayList<>();
    public static ObjectOutputStream oos;
    public InfoServer(Socket socket) {
        client = socket;
    }

    public static void InfoServer() throws IOException {
        try {
            Object o = objectIn.readObject();
            if (o.equals("getInfo")){
                o = objectIn.readObject();
                o=decryptData(o,client);
                oos = new ObjectOutputStream(client.getOutputStream());
                File file=new File("src/Data/"+(String)o+".dat");
                long data=file.length()/(1024*1024);
                String detailtUser="";
                for (int i=0;i<listUser.size();i++){
                    if(listUser.get(i).getUserName().equals((String)o))
                        detailtUser= listUser.get(i).getUserName() +"-"+data+"/"+listUser.get(i).getData()+" MB";
                }
                oos.writeObject(detailtUser);
            }else{
//                System.out.println("Change pass");
                o = objectIn.readObject();
                String user= (String) decryptData(o,client);
                o = objectIn.readObject();
                o = decryptData(o,client);
                String pass[]=((String) o).split("-");
                oos = new ObjectOutputStream(client.getOutputStream());
                System.out.println(pass[0]);
                System.out.println(pass[1]);
                System.out.println(pass[2]);
                if(pass[0].equals("d41d8cd98f00b204e9800998ecf8427e")||pass[1].equals("d41d8cd98f00b204e9800998ecf8427e")||pass[2].equals("d41d8cd98f00b204e9800998ecf8427e"))
                    oos.writeObject("Ô nhập không được để trống");
                else if (pass[1].equals(pass[2]))
                {
                    for (int i=0;i<listUser.size();i++){
                        if (listUser.get(i).getUserName().equals(user)){
                            if (pass[0].equals(listUser.get(i).getPassword())){
                                listUser.get(i).setPassword(pass[1]);
                                oos.writeObject("Thành công");
                            }else oos.writeObject("Sai mật khẩu cũ");
                        }
                    }
                }else {
                    oos.writeObject("2 pass mới không trùng nhau");
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
