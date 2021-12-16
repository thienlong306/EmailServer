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

import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class ReadClient {
    private JTabbedPane tabedPane;
    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton replyButtonAll;
    private JButton spamButton;
    private JButton reloadButton;
    private JTextField recipient;
    private JTextField CC;
    private static DefaultTableModel model;
    public static ArrayList<Email> listEmail = new ArrayList<>();
    public static ArrayList<Email> listEmailRead = new ArrayList<>();

    public ReadClient(JTable inbox, JButton readButton, JButton deleteButton, JButton replyButton,JButton replyButtonAll, JButton spamButton, JButton reloadButton, JTabbedPane tabedPane,JTextField recipient,JTextField CC) {
        this.inbox = inbox;
        this.readButton = readButton;
        this.deleteButton = deleteButton;
        this.replyButton = replyButton;
        this.replyButtonAll=replyButtonAll;
        this.spamButton = spamButton;
        this.reloadButton = reloadButton;
        this.tabedPane = tabedPane;
        this.recipient=recipient;
        this.CC=CC;
    }

    public void setReadClient() {
        String col[] = {"Người giửi", "Chủ đề", "Nội dung", "File","Thời gian"};
        model = new DefaultTableModel(col, 0);
        getListRead();
        inbox.setModel(model);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readButton.setEnabled(true);
                spamButton.setEnabled(true);
                deleteButton.setEnabled(true);
                replyButton.setEnabled(true);
                replyButtonAll.setEnabled(true);
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmailRead.get(inbox.getSelectedRow()).getSender(), listEmailRead.get(inbox.getSelectedRow()).getCC(), listEmailRead.get(inbox.getSelectedRow()).getBCC(), listEmailRead.get(inbox.getSelectedRow()).getSubject(), listEmailRead.get(inbox.getSelectedRow()).getContent(), listEmailRead.get(inbox.getSelectedRow()).getNameAttchment(),listEmailRead.get(inbox.getSelectedRow()).getAttachment());
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
                recipient.setText(listEmailRead.get(inbox.getSelectedRow()).getSender());
                recipient.setForeground(Color.BLACK);
                tabedPane.setSelectedIndex(0);
            }
        });
        replyButtonAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipient.setForeground(Color.BLACK);
                CC.setForeground(Color.BLACK);
                recipient.setText(listEmailRead.get(inbox.getSelectedRow()).getSender());
                CC.setText(listEmailRead.get(inbox.getSelectedRow()).getCC());
                tabedPane.setSelectedIndex(0);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        int index = listEmail.indexOf(listEmailRead.get(inbox.getSelectedRow()));
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
        spamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos = new ObjectOutputStream(link.getOutputStream());
                    oos.writeObject("SP");
                    oos.writeObject(username);
                    oos.writeObject(listEmailRead.get(inbox.getSelectedRow()).getSender());
                    ois = new ObjectInputStream(link.getInputStream());
                    String text = (String)ois.readObject();
                    JOptionPane.showMessageDialog(tabedPane,text);

                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
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
        spamButton.setEnabled(false);
        deleteButton.setEnabled(false);
        replyButton.setEnabled(false);
        replyButtonAll.setEnabled(false);
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
