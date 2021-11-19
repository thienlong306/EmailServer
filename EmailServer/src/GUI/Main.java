package GUI;

import BLL.SendClient;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel panelMain;
    private JTabbedPane tabbeMain;
    private JPanel Inbox;
    private JPanel Read;
    private JPanel Sent;
    private JPanel Spam;
    private JPanel Bin;
    private JPanel Send;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton boldButton;
    private JButton italicButton;
    private JComboBox fontSizeComboBox__;
    private JButton fileButton;
    private JButton color;
    private JLabel detailFile;
    private JTextPane editor__;

    public Main() {
        add(panelMain);
        setTitle("Main");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 800;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SendClient sc = new SendClient();
        sc.setBoldButton(boldButton,editor__);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }


}
