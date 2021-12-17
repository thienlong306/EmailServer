package Client.GUI;

import Client.BLL.CheckUserClient;

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
    public static String username;

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
                try {
                    String result = String.valueOf(check.CheckUserClient(textFieldUser.getText(), textFieldPass.getText()));
                    if (result.equals("ok")) {
                        username = textFieldUser.getText();
                        JOptionPane.showMessageDialog(panelLogin, "Thành Công");
                        setVisible(false);
                        new Main().setVisible(true);
                    } else if (result.equals("lock"))
                        JOptionPane.showMessageDialog(panelLogin, "User bị khóa");
                    else JOptionPane.showMessageDialog(panelLogin, "Sai");
                } catch (Exception ex) {
                   JOptionPane.showMessageDialog(panelLogin,"Mất kết nối máy chủ");
                }
            }
        });
        SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SignUp().setVisible(true);
            }
        });
    }
}
