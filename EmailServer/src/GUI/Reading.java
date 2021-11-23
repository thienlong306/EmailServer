package GUI;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static DAL.EmailClient.link;
import static GUI.Login.username;
import static GUI.Main.oos;

public class Reading extends JFrame {
    private JPanel Read;
    private JTextField sent;
    private JTextField CC;
    private JTextField BCC;
    private JTextField subject;
    private JLabel detailFile;
    private JTextPane editor__;
    private JLabel statusUser;


    public void setReading(String sent, String CC, String BCC, String subject, StyledDocument editor__, String detailFile){
        this.sent.setText(sent);
        this.CC.setText(CC);
        this.BCC.setText(BCC);
        this.subject.setText(subject);
        this.editor__.setDocument(editor__);
        this.detailFile.setText(detailFile);
    }
    public void setStatusUser(String text){
        statusUser.setText(text);
    }
    public Reading() {
        add(Read);
        setTitle("Reading");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 800;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
    }
    public void setEmail(int index) throws IOException, ClassNotFoundException {

    }
    public static void main(String[] args) {
        new Reading().setVisible(true);
    }
}
