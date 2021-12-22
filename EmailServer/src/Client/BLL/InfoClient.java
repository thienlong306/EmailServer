package Client.BLL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class InfoClient {
    private JButton ChangePassButton;
    private JLabel userData;
    private JLabel data;
    private String detailtUser[];
    private JPanel Info;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JLabel hotenData;
    public InfoClient(JPanel Info,JLabel userData, JLabel data, JPasswordField passwordField1,JPasswordField passwordField2, JPasswordField passwordField3,JButton ChangePassButton,JLabel hotenData){
        this.Info=Info;
        this.userData=userData;
        this.data=data;
        this.passwordField1=passwordField1;
        this.passwordField2=passwordField2;
        this.passwordField3=passwordField3;
        this.ChangePassButton=ChangePassButton;
        this.hotenData=hotenData;
    }

    public void setInfoClient() {
        getInfo();
        userData.setText(detailtUser[0]);
        hotenData.setText(detailtUser[1]);
        data.setText(detailtUser[2]);
        ChangePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos = new ObjectOutputStream(link.getOutputStream());
                    oos.writeObject("Info");
                    oos.writeObject("changePass");
                    Object encryt=encryptData(username);
                    oos.writeObject(encryt);
                    String text1=getMD5(passwordField1.getText());
                    String text2=getMD5(passwordField2.getText());
                    String text3=getMD5(passwordField3.getText());
                    String objectPass=text1+"-"+text2+"-"+text3;
                    Object o=objectPass;
                    o=encryptData(o);
                    oos.writeObject(o);
//            oos.writeObject(username);
                    ois = new ObjectInputStream(link.getInputStream());
                    o = ois.readObject();
                    JOptionPane.showMessageDialog(Info,o);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public void reload(){
        getInfo();
        userData.setText(detailtUser[0]);
        hotenData.setText(detailtUser[1]);
        data.setText(detailtUser[2]);
    }
    public void getInfo(){
        try {
            oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("Info");
            oos.writeObject("getInfo");
            Object encryt=encryptData(username);
            oos.writeObject(encryt);
//            oos.writeObject(username);
            ois = new ObjectInputStream(link.getInputStream());
            Object o = ois.readObject();
            detailtUser=((String)o).split("-");
//            System.out.println(detailtUser[0]+detailtUser[1]+detailtUser[2]);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(Info,"Mất kết nối máy chủ");
        }
    }
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
}
