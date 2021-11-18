package Enity;

import java.io.Serializable;

public class User implements Serializable {
    //DECLARE USERNAME AND PASSWORD VARAIBLES
    private String userName;
    private String password;
    private int data = 100;

    //CLASS CONSTRUCTOR
    public User() {

    }

    public User(String name, String pass) {
        userName = name;
        password = pass;
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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
