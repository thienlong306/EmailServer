package Enity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    //DECLARE USERNAME AND PASSWORD VARAIBLES
    private String userName;
    private String password;
    private String HotenUser;
    private String status="unlock";
    private int data = 100;
    private ArrayList<String> listSpam=new ArrayList<>();

    //CLASS CONSTRUCTOR
    public User() {

    }

    public User(String name, String pass,String Hoten) {
        userName = name;
        password = pass;
        HotenUser = Hoten;
    }

    //ACCESSOR METHODS
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    //MUTATOR METHODS
    public void setUserName(String name) {
        userName = name;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public ArrayList<String> getListSpam() {
        return listSpam;
    }
    public void ListSpam(ArrayList<String> listSpam) {
        this.listSpam = listSpam;
    }

    public String getHotenUser() {
        return HotenUser;
    }

    public void setHotenUser(String hotenUser) {
        HotenUser = hotenUser;
    }
}
