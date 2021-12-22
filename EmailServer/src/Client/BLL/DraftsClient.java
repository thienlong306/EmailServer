package Client.BLL;

import Client.GUI.Main;
import Client.GUI.Reading;
import Enity.Email;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class DraftsClient {
    private JPanel Draft;
    private JTable draftTable;
    private JButton readDraft;
    private JButton deleteDraft;
    private JButton setEmail;
    private static DefaultTableModel model;
    private ArrayList<Email> listEmail = new ArrayList<>();

    private JTextField recipient;
    private JTextField CC;
    private JTextField BCC;
    private JTextField subject;
    private JTextPane editor__;
    private JTabbedPane tabbedPane;
    private JLabel detailFile;
    private byte[] attachment;
    private File file;
    public DraftsClient(JTabbedPane tabbedPane,JPanel Draft, JTable draftTable, JButton readDraft, JButton deleteDraft, JButton setEmail,JTextField recipient,JTextField CC, JTextField BCC, JTextField subject,JTextPane editor__,JLabel detailFile,byte[] attachment){
        this.Draft=Draft;
        this.draftTable=draftTable;
        this.readDraft=readDraft;
        this.deleteDraft=deleteDraft;
        this.setEmail=setEmail;
        this.recipient=recipient;
        this.CC=CC;
        this.BCC=BCC;
        this.subject=subject;
        this.editor__=editor__;
        this.tabbedPane=tabbedPane;
        this.detailFile=detailFile;
        this.attachment=attachment;
    }

    public DraftsClient() {
    }
    public void setDraftClient(){
        String col[] = {"Người nhận", "Chủ đề", "Nội dung", "File","Thời gian lưu"};
        model = new DefaultTableModel(col, 0);
        getListDraft();
        draftTable.setModel(model);
        draftTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readDraft.setEnabled(true);
                deleteDraft.setEnabled(true);
                setEmail.setEnabled(true);
            }
        });
        readDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (draftTable.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getSender(), listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getCC(), listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getBCC(), listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getSubject(), listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getContent(), listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getNameAttchment(),listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1).getAttachment());
                        reading.setVisible(true);
                        reload();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        setEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Email temp=listEmail.get(listEmail.size()-draftTable.getSelectedRow()-1);
                recipient.setForeground(Color.BLACK);
                CC.setForeground(Color.BLACK);
                BCC.setForeground(Color.BLACK);
                recipient.setText(temp.getRecipient());
                CC.setText(temp.getCC());
                BCC.setText(temp.getBCC());
                subject.setText(temp.getSubject());
                editor__.setDocument(temp.getContent());
                detailFile.setText(temp.getNameAttchment());
               try {
                   FileOutputStream fileOut = new FileOutputStream(temp.getNameAttchment());
                   fileOut.write(temp.getAttachment());
                   file = new File(temp.getNameAttchment());
                   new SendClient().setFile(file);
               } catch (FileNotFoundException fileNotFoundException) {
                   fileNotFoundException.printStackTrace();
               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
                reload();
                tabbedPane.setSelectedIndex(0);
            }
        });
        deleteDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Email> listEmail = new ArrayList<>();
                    File fileSent = new File("src/Data/BK.dat");
                    if(fileSent.length()!=0){
                        FileInputStream fis = new FileInputStream(fileSent);
                        ObjectInputStream fileInSent = new ObjectInputStream(fis);
                        Email temp;
                        while (fis.available() != 0){
                            Object i= fileInSent.readObject();
                            if(i instanceof Email){
                                temp = (Email) i;
                                listEmail.add(temp);
                            }
                        }
                    }
                    ObjectOutputStream fileOutSend = null;
                    listEmail.remove(listEmail.size()-draftTable.getSelectedRow()-1);
                    fileOutSend = new ObjectOutputStream(new FileOutputStream("src/Data/BK.dat"));
                    if(listEmail.size()!=0){
                        for (int i = 0; i < listEmail.size(); i++) {
                            fileOutSend.writeObject(listEmail.get(i));
                        }
                    }
                    reload();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
    }
    public void reload(){
        readDraft.setEnabled(false);
        deleteDraft.setEnabled(false);
        setEmail.setEnabled(false);
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {
            model.removeRow(i);
        }
        listEmail.clear();
        getListDraft();
    }
    public void getListDraft(){
       try {
           File file = new File("src/Data/BK.dat");
           if(file.length()!=0){
               FileInputStream fis = new FileInputStream(file);
               ObjectInputStream objectIn = new ObjectInputStream(fis);
               Email temp;
               while (fis.available() != 0){
                   Object i = objectIn.readObject();
                   if(i instanceof Email){
                       temp = (Email) i;
                       listEmail.add(temp);
                   }
               }
               objectIn.close();
           }
           int count=0;
           for (int i = listEmail.size()-1; i >=0; i--) {
                   StyledDocument doc = (DefaultStyledDocument) listEmail.get(i).getContent();
                   Object[] data = {listEmail.get(i).getRecipient() , listEmail.get(i).getSubject(), doc.getText(0, doc.getLength()), listEmail.get(i).getNameAttchment(),listEmail.get(i).getDateTime()};
                   model.addRow(data);
                   count++;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    public void addEmail(Object o){
        try {
            syn(o,"BK");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void syn(Object o,String username) throws IOException, ClassNotFoundException {
        ArrayList<Email> listEmail = new ArrayList<>();
        File file = new File("src/Data/BK.dat");
        if(file.length()!=0){
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fis);
            Email temp;
            while (fis.available() != 0){
                Object i = objectIn.readObject();
                if(i instanceof Email){
                    temp = (Email) i;
                    listEmail.add(temp);
                }
            }
            objectIn.close();
        }
        if(o instanceof Email) {
            ObjectOutputStream objectOut= null;
            objectOut = new ObjectOutputStream(new FileOutputStream("src/Data/BK.dat"));
            if(listEmail.size()!=0){
                for (int i = 0; i < listEmail.size(); i++) {
                    objectOut.writeObject(listEmail.get(i));
                }
            }
            objectOut.writeObject(o);
            objectOut.close();
        }
    }
}
