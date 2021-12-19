package Server.BLL;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Base64;

import static Server.BLL.HandlerClient.*;

public class CipherServer {
    public static SecretKey key;
    public static Socket client;

    public CipherServer(Socket socket) {
        client = socket;
    }

    public static void CipherServer() {
      try {
          objectOut =new ObjectOutputStream(client.getOutputStream());
          objectIn=new ObjectInputStream(client.getInputStream());
          createKeyRSA();
          getKeyAES();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
    public static void createKeyRSA() {
        try {
            //Tạo khóa RSA
            sv = new GenerateKeys(512);
            sv.createKeys();
            Object o = objectIn.readObject();
            objectOut.writeObject(sv.getPublicKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void getKeyAES(){
        try{
            // Giải mã khóa
            Object o=objectIn.readObject();
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, sv.getPrivateKey());
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode((String) o));
            key = new SecretKeySpec(decryptOut, 0, decryptOut.length, "AES");
//            System.out.println("Khóa sau khi giải mã: " + key);
            objectOut.writeObject("Get key AES success");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Object encryptData(Object o){
        SealedObject sealedObject = null;
        try {
            //Mã khóa Object
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            sealedObject = new SealedObject((Serializable) o, cipher);
//            encrypt=Base64.getEncoder().encodeToString(cipher.doFinal((byte[]) o));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sealedObject;
    }
    public static Object decryptData(Object o)  {
        SealedObject sealedObject=null;
        Object tmp=null;
        Cipher cipher = null;
        try {
            //Giải mã Object
//            String encrypt=(String) in.readObject();
//            System.out.println("encrypt: "+o);
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            sealedObject = (SealedObject) o;
            tmp=sealedObject.getObject(cipher);
//            tmp = new String(cipher.doFinal(Base64.getDecoder().decode((byte[]) o)));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
class GenerateKeys {
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public GenerateKeys(int keylength)
            throws NoSuchAlgorithmException, NoSuchProviderException {

        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);

    }

    public void createKeys() {

        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();

    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public static void main(String[] args)
            throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        GenerateKeys gk_Alice;
        GenerateKeys gk_Bob;

        gk_Alice = new GenerateKeys(512);
        gk_Alice.createKeys();

        System.out.println(gk_Alice.getPrivateKey());
        System.out.println(gk_Alice.getPublicKey());

        gk_Bob = new GenerateKeys(512);
        gk_Bob.createKeys();

        System.out.println(gk_Bob.getPrivateKey());
    }

}