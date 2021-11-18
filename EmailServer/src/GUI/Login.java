package GUI;

import BLL.CheckUserClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField textFieldUser;
    private JPasswordField textFieldPass;
    private JButton SignUp;
    private JButton SignIn;
    private JPanel panelLogin;

    public Login() {
        add(panelLogin);
        setTitle("Đăng Nhập");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 200;
        final int WIDTH = 300;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckUserClient check = new CheckUserClient();
                if (check.CheckUserClient(textFieldUser.getText(), textFieldPass.getText()))
                    JOptionPane.showMessageDialog(panelLogin, "Thành Công");
                else JOptionPane.showMessageDialog(panelLogin, "Sai");
            }
        });
        SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp().setVisible(true);
            }
        });
    }

}