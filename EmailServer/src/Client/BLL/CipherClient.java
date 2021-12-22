package Client.BLL;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import static Client.EmailClient.*;

public class CipherClient {
    public static GenerateSymmetricKey myKey;
    public static PublicKey publicKey;
    public static void CipherClient(){
       try {
           objectIn = new ObjectInputStream(link.getInputStream());
           objectOut =  new ObjectOutputStream(link.getOutputStream());
           getKeyPublic();
           createKeyAES();
           encryptKeyAES();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (NullPointerException ex){

       }
    }
    public static void getKeyPublic() {
        try {
            //Nhận khóa public
            //Object o = in.readObject();
            objectOut.writeObject("Give me key");
            Object o = objectIn.readObject();
            publicKey = (PublicKey) o;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createKeyAES() {
        try {
            //Tạo khóa AES
            myKey = new GenerateSymmetricKey(16, "AES");
            System.out.println("Mã AES: " + myKey.getKey());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void encryptKeyAES() {
        try {
            // Mã hoá khóa AES
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte encryptOut[] = c.doFinal(myKey.getKey().getEncoded());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
//            System.out.println("Chuỗi sau khi mã hoá: " + strEncrypt);
            objectOut.writeObject(strEncrypt);
            objectIn.readObject();
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
            cipher.init(Cipher.ENCRYPT_MODE, myKey.getKey());
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
            cipher.init(Cipher.DECRYPT_MODE, myKey.getKey());
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
class GenerateSymmetricKey {

    private SecretKeySpec secretKey;
    private static byte[] secretKeySend;

    public GenerateSymmetricKey(int length, String algorithm)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        SecureRandom rnd = new SecureRandom();
        byte[] key = new byte[length];
        rnd.nextBytes(key);
        this.secretKey = new SecretKeySpec(key, algorithm);
    }

    public SecretKeySpec getKey() {
        return this.secretKey;
    }
}

