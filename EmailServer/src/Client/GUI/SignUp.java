package Client.GUI;

import Client.BLL.AddUserClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SignUp extends JFrame {
    private JPanel panel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton đăngKýButton;
    private JPanel panelSignUp;
    private JButton Login;

    public SignUp(){
        add(panelSignUp);
        setTitle("Đăng ký");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 200;
        final int WIDTH = 300;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelSignUp.setFocusable(true);
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login().setVisible(true);
            }
        });
        đăngKýButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AddUserClient check = new AddUserClient();
                    if (check.AddUserClient(textField1.getText(), passwordField1.getText()))
                        JOptionPane.showMessageDialog(panelSignUp, "Thành Công");
                    else JOptionPane.showMessageDialog(panelSignUp, "Lỗi");
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(panelSignUp,"Mất kết nối máy chủ");
                }
            }
        });

        textField1.setText("exam@sv.com");
        textField1.setForeground(Color.GRAY);
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (textField1.getText().equals("exam@sv.com")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("exam@sv.com");
                }
            }
        });
    }

}
