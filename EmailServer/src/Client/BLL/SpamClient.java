package Client.BLL;

import Client.GUI.Reading;
import Enity.Email;

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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class SpamClient {
    private JTabbedPane tabedPane;
    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton spamButton;
    private JButton reloadButton;
    private JTextField recipient;
    private JTextField CC;
    private static DefaultTableModel model;
    public static ArrayList<Email> listEmail = new ArrayList<>();
    public static ArrayList<Email> listEmailSpam = new ArrayList<>();

    public SpamClient(JTable inbox, JButton readButton, JButton deleteButton, JButton replyButton, JButton spamButton, JButton reloadButton, JTabbedPane tabedPane,JTextField recipient,JTextField CC) {
        this.inbox = inbox;
        this.readButton = readButton;
        this.deleteButton = deleteButton;
        this.replyButton = replyButton;
        this.spamButton = spamButton;
        this.reloadButton = reloadButton;
        this.tabedPane = tabedPane;
        this.recipient=recipient;
        this.CC=CC;
    }

    public void setSpamClient() {
        String col[] = {"Người giửi", "Chủ đề", "Nội dung", "File","Thời gian"};
        model = new DefaultTableModel(col, 0);
        getListSpam();
        inbox.setModel(model);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readButton.setEnabled(true);
                spamButton.setEnabled(true);
                deleteButton.setEnabled(true);
                replyButton.setEnabled(true);
            }
        });
        spamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos = new ObjectOutputStream(link.getOutputStream());
                    oos.writeObject("SP");
                    oos.writeObject(username);
                    oos.writeObject(listEmailSpam.get(inbox.getSelectedRow()).getSender());
                    ois = new ObjectInputStream(link.getInputStream());
                    String text = (String)ois.readObject();
                    JOptionPane.showMessageDialog(tabedPane,text);

                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipient.setForeground(Color.BLACK);
                recipient.setText(listEmailSpam.get(inbox.getSelectedRow()).getSender());
                tabedPane.setSelectedIndex(0);
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmailSpam.get(inbox.getSelectedRow()).getSender(), listEmailSpam.get(inbox.getSelectedRow()).getCC(), listEmailSpam.get(inbox.getSelectedRow()).getBCC(), listEmailSpam.get(inbox.getSelectedRow()).getSubject(), listEmailSpam.get(inbox.getSelectedRow()).getContent(), listEmailSpam.get(inbox.getSelectedRow()).getNameAttchment(),listEmailSpam.get(inbox.getSelectedRow()).getAttachment());
                        reading.setVisible(true);
                        reload();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        int index = listEmail.indexOf(listEmailSpam.get(inbox.getSelectedRow()));
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
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {
            model.removeRow(i);
        }
        listEmail.clear();
        listEmailSpam.clear();
        getListSpam();
    }
    public void getListSpam() {
        try {
            oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("L");
            oos.writeObject(username);
            ois = new ObjectInputStream(link.getInputStream());
            listEmail = (ArrayList<Email>) ois.readObject();
            int count=0;
            for (int i = listEmail.size()-1; i >=0; i--) {
                if (listEmail.get(i).getStatus().equals("Spam")) {
                    listEmailSpam.add(listEmail.get(i));
                    StyledDocument doc = (DefaultStyledDocument) listEmail.get(i).getContent();
                    Object[] data = {listEmail.get(i).getSender(), listEmail.get(i).getSubject(), doc.getText(0, doc.getLength()), listEmail.get(i).getNameAttchment(),listEmail.get(i).getDateTime()};
                    model.addRow(data);
                    count++;
                }
            }
        } catch (IOException | ClassNotFoundException | BadLocationException e) {
            e.printStackTrace();
        }
    }
}
