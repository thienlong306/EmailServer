package GUI;

import BLL.SendClient;
import DAL.EmailClient;

import javax.swing.*;
import java.awt.*;

import static GUI.Login.username;

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

    public Main() {
        add(panelMain);
        setTitle("Main");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 800;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SendClient sc = new SendClient(fontSizeComboBox__,SendEmail,ReadEmail,boldButton,italicButton,color,fileButton,detailFile,editor__,Send,recipient,CC,BCC,subject);
        sc.SetSendClient();
    }

//    public static void main(String[] args) {
//        EmailClient ec = new EmailClient();
//        ec.Connect();
//        new Main().setVisible(true);
//    }


}
