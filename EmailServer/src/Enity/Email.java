package Enity;

import javax.swing.text.StyledDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Email implements Serializable {
    //DECLARE VARIABLES USED IN CLASS
    private String sender;
    private String recipient;
    private String CC;
    private String BCC;
    private String subject;
    private StyledDocument content;
    private byte[] attachment;
    private String nameAttchment;
    private String status;
    private LocalDateTime dateTime;

    //DEFAULT CONSTRUCTOR
    public Email() {
        sender = "";
        recipient = "";
        CC = "";
        BCC = "";
        subject = "";
        content = null;
        attachment = null;
        status = "Sent";
        dateTime= LocalDateTime.now();
    }

    //INITIALISED CONSTRUCTOR
    public Email(String sender, String recipient, String CC, String BCC, String subject,
                 StyledDocument content) {
        this.sender = sender;
        this.recipient = recipient;
        this.CC = CC;
        this.BCC = BCC;
        this.subject = subject;
        this.content = content;
        status = "Sent";
        dateTime= LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getBCC() {
        return BCC;
    }

    public void setBCC(String BCC) {
        this.BCC = BCC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public StyledDocument getContent() {
        return content;
    }

    public void setContent(StyledDocument content) {
        this.content = content;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(File temp) {
        //CONVERTS REFERENCE TO FILE OBJECT INTO BYTE ARRAY FOR SERAILIZATION
        try {
            FileInputStream fileIn = new FileInputStream(temp.getAbsolutePath());
            long fileLength = (temp.length());
            int intFileLength = (int) fileLength;
            attachment = new byte[intFileLength];
            fileIn.read(attachment);
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameAttchment() {
        return nameAttchment;
    }

    public void setNameAttchment(String nameAttchment) {
        this.nameAttchment = nameAttchment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDateTime() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateTime.format(myFormatObj);
        return formattedDate;
    }

    public void setDateTime() {
        this.dateTime= LocalDateTime.now();
    }
}
