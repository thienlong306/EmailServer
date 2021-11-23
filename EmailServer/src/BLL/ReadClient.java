package BLL;

import Enity.Email;
import GUI.Reading;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import static DAL.EmailClient.link;
import static DAL.EmailClient.user;
import static GUI.Login.username;
import static GUI.Main.ois;
import static GUI.Main.oos;

public class ReadClient {
    private JTabbedPane tabedPane;
    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton spamButton;
    private JButton reloadButton;
    private static DefaultTableModel model;
    public static ArrayList<Email> listEmail = new ArrayList<>();
    public static ArrayList<Email> listEmailRead = new ArrayList<>();

    public ReadClient(JTable inbox, JButton readButton, JButton deleteButton, JButton replyButton, JButton spamButton, JButton reloadButton, JTabbedPane tabedPane) {
        this.inbox = inbox;
        this.readButton = readButton;
        this.deleteButton = deleteButton;
        this.replyButton = replyButton;
        this.spamButton = spamButton;
        this.reloadButton = reloadButton;
        this.tabedPane = tabedPane;
    }

    public void setReadClient() {
        String col[] = {"Người giửi", "Chủ đề", "Nội dung", "File"};
        model = new DefaultTableModel(col, 0);
        getListRead();
        inbox.setModel(model);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readButton.setEnabled(true);
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmailRead.get(inbox.getSelectedRow()).getSender(), listEmailRead.get(inbox.getSelectedRow()).getCC(), listEmailRead.get(inbox.getSelectedRow()).getBCC(), listEmailRead.get(inbox.getSelectedRow()).getSubject(), listEmailRead.get(inbox.getSelectedRow()).getContent(), listEmailRead.get(inbox.getSelectedRow()).getNameAttchment());
                        reading.setVisible(true);
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
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {
            model.removeRow(i);
        }
        listEmail.clear();
        listEmailRead.clear();
        getListRead();
    }
    public void getListRead() {
        try {
            oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("L");
            oos.writeObject(username);
            ois = new ObjectInputStream(link.getInputStream());
            listEmail = (ArrayList<Email>) ois.readObject();
            int count=0;
            for (int i = listEmail.size()-1; i >=0; i--) {
                if (listEmail.get(i).getStatus().equals("Read")) {
                    listEmailRead.add(listEmail.get(i));
                    StyledDocument doc = (DefaultStyledDocument) listEmail.get(i).getContent();
                    Object[] data = {listEmail.get(i).getSender(), listEmail.get(i).getSubject(), doc.getText(0, doc.getLength()), listEmail.get(i).getNameAttchment()};
                    model.addRow(data);
                    count++;
                }
            }
        } catch (IOException | ClassNotFoundException | BadLocationException e) {
            e.printStackTrace();
        }
    }

}
