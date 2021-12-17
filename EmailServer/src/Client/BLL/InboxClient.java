package Client.BLL;

import Enity.Email;
import Client.GUI.Reading;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class InboxClient {
    private JTabbedPane tabedPane;
    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton spamButton;
    private JButton reloadButton;
    private static DefaultTableModel model;
    public static ArrayList<Email> listEmail = new ArrayList<>();
    public static ArrayList<Email> listEmailRecip = new ArrayList<>();

    public InboxClient(JTable inbox, JButton readButton, JButton deleteButton, JButton replyButton, JButton spamButton, JButton reloadButton, JTabbedPane tabedPane) {
        this.inbox = inbox;
        this.readButton = readButton;
        this.deleteButton = deleteButton;
        this.replyButton = replyButton;
        this.spamButton = spamButton;
        this.reloadButton = reloadButton;
        this.tabedPane = tabedPane;
    }

    public void setInboxClient() {
        String col[] = {"Người giửi", "Chủ đề", "Nội dung", "File","Thời gian"};
        model = new DefaultTableModel(col, 0);
        getListInbox();
        inbox.setModel(model);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readButton.setEnabled(true);
                spamButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        Reading reading = new Reading();
                        reading.setReading(listEmailRecip.get(inbox.getSelectedRow()).getSender(), listEmailRecip.get(inbox.getSelectedRow()).getCC(), listEmailRecip.get(inbox.getSelectedRow()).getBCC(), listEmailRecip.get(inbox.getSelectedRow()).getSubject(), listEmailRecip.get(inbox.getSelectedRow()).getContent(), listEmailRecip.get(inbox.getSelectedRow()).getNameAttchment(), listEmailRecip.get(inbox.getSelectedRow()).getAttachment());
                        reading.setVisible(true);
                        int index = listEmail.indexOf(listEmailRecip.get(inbox.getSelectedRow()));
                        ObjectOutputStream oos = new ObjectOutputStream(link.getOutputStream());
                        oos.writeObject("R");
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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inbox.getSelectedRow() != -1) {
                        int index = listEmail.indexOf(listEmailRecip.get(inbox.getSelectedRow()));
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
                    oos.writeObject(listEmailRecip.get(inbox.getSelectedRow()).getSender());
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
        for( int i = model.getRowCount() - 1; i >= 0; i-- )
        {
            model.removeRow(i);
        }
        listEmail.clear();
        listEmailRecip.clear();
        getListInbox();
    }
    public void getListInbox() {
        try {
            oos = new ObjectOutputStream(link.getOutputStream());
            oos.writeObject("L");
            oos.writeObject(username);
            ois = new ObjectInputStream(link.getInputStream());
            listEmail = (ArrayList<Email>) ois.readObject();
            int count=0;
            for (int i = listEmail.size()-1; i >=0; i--) {
                if (listEmail.get(i).getStatus().equals("Recip")) {
                    listEmailRecip.add(listEmail.get(i));
                    StyledDocument doc = (DefaultStyledDocument) listEmail.get(i).getContent();
                    Object[] data = {listEmail.get(i).getSender(), listEmail.get(i).getSubject(), doc.getText(0, doc.getLength()), listEmail.get(i).getNameAttchment(),listEmail.get(i).getDateTime()};
                    model.addRow(data);
                    count++;
                }
            }
        } catch (IOException | ClassNotFoundException | BadLocationException e) {
            JOptionPane.showMessageDialog(tabedPane,"Mất kết nối máy chủ");
        }
    }

}
