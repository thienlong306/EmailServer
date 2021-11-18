package Enity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Email {
    //DECLARE VARIABLES USED IN CLASS
    private String sender;
    private String recipient;
    private String subject;
    private String content;
    private String attachmentName;
    private byte[] attachment;
    private boolean read;
    //DEFAULT CONSTRUCTOR
    public Email()
    {
        sender = "";
        recipient = "";
        subject = "";
        content = "";
        attachmentName = "";
        attachment = null;
        read = false;
    }
    //INITIALISED CONSTRUCTOR
    public Email(String sender, String recipient, String subject,
                 String content, String attachmentName, byte[] attachment)
    {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.attachmentName = attachmentName;
        this.attachment = attachment;
        read = false;
    }
    //ACCESSOR METHODS
    public String getSender()
    {
        return sender;
    }
    public String getRecipient()
    {
        return recipient;
    }
    public String getSubject()
    {
        return subject;
    }
    public String getContent()
    {
        return content;
    }
    public String getAttachmentName()
    {
        return attachmentName;
    }
    public byte[] getAttachment()
    {
        return attachment;
    }
    public boolean getRead()
    {
        return read;
    }
    //MUTATOR METHODS
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public void setAttachmentName(String attachmentName)
    {
        this.attachmentName = attachmentName;
    }
    public void setAttachment(File temp)
    {
        //CONVERTS REFERENCE TO FILE OBJECT INTO BYTE ARRAY FOR SERAILIZATION
        try
        {
            FileInputStream fileIn = new FileInputStream(temp.getAbsolutePath());
            long fileLength = (temp.length());
            int intFileLength = (int)fileLength;
            attachment = new byte[intFileLength];
            fileIn.read(attachment);
            fileIn.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void setRead(boolean read)
    {
        this.read = read;
    }
}
