package Client.BLL;

import Enity.Email;
import Client.GUI.Reading;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class SentClient {
    private JTabbedPane tabedPane;
    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton replyAllButton;
    private JButton spamButton;
    private JButton reloadButton;
    private JTextField recipient;
    private JTextField CC;
    private JTextField BCC;
    private static DefaultTableModel model;
    public static ArrayList<Email> listEmail = new ArrayList<>();
    public static ArrayList<Email> listEmailSent = new ArrayList<>();

    public SentClient(JTable inbox, JButton readButton, JButton deleteButton, JButton replyButton,JButton replyAllButton, JButton spamButton, JButton reloadButton, JTabbedPane tabedPane,JTextField recipient,JTextField CC,JTextField BCC) {
        this.inbox = inbox;
        this.readButton = readButton;
        this.deleteButton = deleteButton;
        this.replyButton = replyButton;
        this.replyAllButton=replyAllButton;
        this.spamButton = spamButton;
        this.reloadButton = reloadButton;
        this.tabedPane = tabedPane;
        this.recipient=recipient;
        this.CC=CC;
        this.BCC=BCC;
    }

    public void setSentClient() {
        String col[] = {"Người nhận", "Chủ đề", "Nội dung", "File","Thời gian"};
        model = new DefaultTableModel(col, 0);
        getListSent();
        inbox.setModel(model);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readButton.setEnabled(true);
                replyButton.setEnabled(true);
                replyAllButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmailSent.get(inbox.getSelectedRow()).getRecipient(), listEmailSent.get(inbox.getSelectedRow()).getCC(), listEmailSent.get(inbox.getSelectedRow()).getBCC(), listEmailSent.get(inbox.getSelectedRow()).getSubject(), listEmailSent.get(inbox.getSelectedRow()).getContent(), listEmailSent.get(inbox.getSelectedRow()).getNameAttchment(),listEmailSent.get(inbox.getSelectedRow()).getAttachment());
                        reading.setStatusUser("Nhận");
                        reading.setVisible(true);
                        reload();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipient.setForeground(Color.BLACK);
                recipient.setText(listEmailSent.get(inbox.getSelectedRow()).getRecipient());
                tabedPane.setSelectedIndex(0);
            }
        });
        replyAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipient.setForeground(Color.BLACK);
                CC.setForeground(Color.BLACK);
                BCC.setForeground(Color.BLACK);
                recipient.setText(listEmailSent.get(inbox.getSelectedRow()).getRecipient());
                CC.setText(listEmailSent.get(inbox.getSelectedRow()).getCC());
                BCC.setText(listEmailSent.get(inbox.getSelectedRow()).getBCC());
                tabedPane.setSelectedIndex(0);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        int index = listEmail.indexOf(listEmailSent.get(inbox.getSelectedRow()));
                        ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
                        oos.writeObject("D");
                        oos.writeObject(username);
                        oos.writeObject(index);
                        ObjectInputStream ois = new ObjectInputStream(link.getInputStream());
                        String check = (String) ois.readObject();
                        reload();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
reload();
            }
        });
    }
    public void reload(){
        readButton.setEnabled(false);
        replyButton.setEnabled(false);
        replyAllButton.setEnabled(false);
        deleteButton.setEnabled(false);
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {
            model.removeRow(i);
        }
        listEmail.clear();
        listEmailSent.clear();
        getListSent();
    }
    public void getListSent() {
        try {
            oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("L");
            Object encryt=encryptData(username);
            oos.writeObject(encryt);
//            oos.writeObject(username);
            ois = new ObjectInputStream(link.getInputStream());
            listEmail = (ArrayList<Email>) ois.readObject();
            int count=0;
            for (int i = listEmail.size()-1; i >=0; i--) {
                if (listEmail.get(i).getStatus().equals("Sent")) {
                    listEmailSent.add(listEmail.get(i));
                    StyledDocument doc = (DefaultStyledDocument) listEmail.get(i).getContent();
                    Object[] data = {listEmail.get(i).getRecipient(), listEmail.get(i).getSubject(), doc.getText(0, doc.getLength()), listEmail.get(i).getNameAttchment(),listEmail.get(i).getDateTime()};
                    model.addRow(data);
                    count++;
                }
            }
        } catch (IOException | ClassNotFoundException | BadLocationException e) {
            JOptionPane.showMessageDialog(tabedPane,"Mất kết nối máy chủ");
        }
    }

}
