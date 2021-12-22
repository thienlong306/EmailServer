package Enity;

import javax.crypto.SecretKey;
import java.net.Socket;

public class KeyUser {
    private Socket client;
    private SecretKey key;

    public KeyUser(Socket client,SecretKey key){
        this.client=client;
        this.key=key;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }
}
