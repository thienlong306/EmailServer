package Client.GUI;

import Client.*;
import Client.BLL.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;
import static Client.GUI.Login.username;

public class Main extends JFrame {
    private JPanel panelMain;
    private JTabbedPane tabbeMain;
    private JPanel Inbox;
    private JPanel Read;
    private JPanel Sent;
    private JPanel Spam;
    private JPanel Bin;
    private JPanel Send;
    private JTextField recipient;
    private JTextField CC;
    private JTextField BCC;
    private JTextField subject;
    private JButton boldButton;
    private JButton italicButton;
    private JComboBox fontSizeComboBox__;
    private JButton fileButton;
    private JButton color;
    private JLabel detailFile;
    private JTextPane editor__;
    private JButton SendEmail;
    private JButton ReadEmail;

    private JTable inbox;
    private JButton readButton;
    private JButton deleteButton;
    private JButton replyButton;
    private JButton spamButton;
    private JButton reloadButton;
    private JButton readButton1;
    private JButton reloadButton1;
    private JButton spamButton1;
    private JButton replyButton1;
    private JButton deleteButton1;
    private JTable tableRead;
    private JTable sent;
    private JButton readButton2;
    private JButton replyButton2;
    private JButton spamButton2;
    private JButton deleteButton2;
    private JButton reloadButton2;
    private JButton addImg;
    private JTable tableSpam;
    private JButton readButton3;
    private JButton reloadButton3;
    private JButton replyButton3;
    private JButton spamButton3;
    private JButton deleteButton3;
    private JTable tableDelete;
    private JButton readButton4;
    private JButton reloadButton4;
    private JButton replyButton4;
    private JButton spamButton4;
    private JButton deleteButton4;
    private JButton replyAllButton1;
    private JButton scheduleButton;
    private JButton replyAllButton2;
    private JPanel Info;
    private JButton làmMớiButton;
    private JButton unDeleteButton;
    private JButton underline;
    private JTextField textField1;
    private JTextField textField2;
    private JButton ChangePassButton;
    private JTextField textField3;
    private JLabel userData;
    private JLabel Data;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;

    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;
    public Main() {
        addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        try {
                            oos = new ObjectOutputStream(link.getOutputStream());
                            oos.writeObject("C");
                            String lg="LG";
                            Object encry=encryptData(lg);
                            oos.writeObject(encry);
                            ois = new ObjectInputStream(link.getInputStream());
                            Object o = ois.readObject();
                            JOptionPane.showMessageDialog(panelMain,"Logout");
                        } catch (IOException ioException) {
                            
                        } catch (ClassNotFoundException classNotFoundException) {

                        }
                        new Login().setVisible(true);
                    }
                }
        );

        add(panelMain);
        setTitle(username);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 900;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);

        SendClient sc = new SendClient(fontSizeComboBox__,SendEmail,scheduleButton,ReadEmail,boldButton,italicButton,color,fileButton,detailFile,editor__,Send,recipient,CC,BCC,subject,addImg,làmMớiButton,underline);
        sc.SetSendClient();

        InboxClient ic = new InboxClient(inbox, readButton,deleteButton,replyButton,spamButton,reloadButton,tabbeMain);
        ic.setInboxClient();

        ReadClient rc = new ReadClient(tableRead, readButton1,deleteButton1,replyButton1,replyAllButton1,spamButton1,reloadButton1,tabbeMain,recipient,CC);
        rc.setReadClient();

        SentClient sct = new SentClient(sent, readButton2,deleteButton2,replyButton2,replyAllButton2,spamButton2,reloadButton2,tabbeMain,recipient,CC,BCC);
        sct.setSentClient();

        SpamClient sp = new SpamClient(tableSpam, readButton3,deleteButton3,replyButton3,spamButton3,reloadButton3,tabbeMain,recipient,CC);
        sp.setSpamClient();

        DeleteClient dc = new DeleteClient(tableDelete, readButton4,deleteButton4,replyButton4,reloadButton4,tabbeMain,recipient,unDeleteButton);
        dc.setDeleteClien();

        InfoClient infoC=new InfoClient(Info,userData,Data,passwordField1,passwordField2,passwordField3,ChangePassButton);
        infoC.setInfoClient();

        tabbeMain.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbeMain.getSelectedIndex()==1){
                    ic.reload();
                }
                if(tabbeMain.getSelectedIndex()==2){
                    rc.reload();
                }
                if(tabbeMain.getSelectedIndex()==3){
                    sct.reload();
                }
                if(tabbeMain.getSelectedIndex()==4){
                    sp.reload();
                }
                if(tabbeMain.getSelectedIndex()==5){
                    dc.reload();
                }
                if(tabbeMain.getSelectedIndex()==6){
                    infoC.reload();
                }
            }
        });

    }
}
