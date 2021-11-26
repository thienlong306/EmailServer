package Client.GUI;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Reading extends JFrame {
    private JPanel Read;
    private JTextField sent;
    private JTextField CC;
    private JTextField BCC;
    private JTextField subject;
    private JLabel detailFile;
    private JTextPane editor__;
    private JLabel statusUser;
    private JButton saveFileButton;
    private byte[] attachment;

    public void setReading(String sent, String CC, String BCC, String subject, StyledDocument editor__, String detailFile, byte[] attachment) {
        this.sent.setText(sent);
        this.CC.setText(CC);
        this.BCC.setText(BCC);
        this.subject.setText(subject);
        this.editor__.setDocument(editor__);
        this.detailFile.setText(detailFile);
        this.attachment = attachment;
        if(detailFile!=null) saveFileButton.setEnabled(true);
    }

    public void setStatusUser(String text) {
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
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //SELECT CHOOSE METHOD
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //GET RESULT OF CHOICE
                chooser.setSelectedFile(new File(detailFile.getText()));
                int result = chooser.showSaveDialog(Read);
                //IF CANCELLED CLOSE WINDOW
                if (result == JFileChooser.CANCEL_OPTION)
                    return;
                //CREATE NEW FILE OBJECT FROM SELECTION
                File temp = chooser.getSelectedFile();
                //CHECK THAT VALID FILE
                if (temp.getName().equals("")) {
                    //DISPLAY MESSAGE DIALOG
                    JOptionPane.showMessageDialog(Read, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        //CREATE NEW FILE OUPTUT STREAM
                        FileOutputStream fileOut = new FileOutputStream(temp);
                        //WRITE ATTACHMENT TO EMAIL
                        fileOut.write(attachment);
                        //CLOSE FILESTREAM
                        fileOut.close();
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(Read,"Không có file để save");
//                        ioe.printStackTrace();
                    }
                }
            }
        });
    }

    public void setEmail(int index) throws IOException, ClassNotFoundException {

    }

    public static void main(String[] args) {
        new Reading().setVisible(true);
    }
}
