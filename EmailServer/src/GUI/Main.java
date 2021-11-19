package GUI;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel panelMain;
    private JTabbedPane tabbeMain;
    private JButton readButton;
    private JButton sentButton;
    private JButton spamButton;
    private JButton binButton;
    private JButton sendButton;
    private JButton exitButton;
    private JPanel panelMenu;
    private JButton inboxButton;

    public Main() {
        add(panelMain);
        setTitle("Main");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 1000;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
