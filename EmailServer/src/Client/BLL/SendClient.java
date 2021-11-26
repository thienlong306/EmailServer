package Client.BLL;

import Enity.Email;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class SendClient {
    private JTextField recipient;
    private JTextField CC;
    private JTextField BCC;
    private JTextField subject;

    private JTextPane editor__;
    private JButton Send;
    private JButton boldButton;
    private JButton color;
    private JButton italicButton;
    private JComboBox<String> fontSizeComboBox__;
    private JButton fileButton;
    private JLabel detailFile;
    private JButton Read;
    private JButton addImg;
    private JPanel panelSendEmail;
    private File file__=null;

    private static final String[] FONT_SIZES = {"Font Size", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};

    public SendClient(JComboBox fontSizeComboBox__, JButton Send, JButton Read, JButton boldButton, JButton italicButton, JButton color, JButton fileButton, JLabel detailFile, JTextPane editor__, JPanel panelSendEmail, JTextField recipient, JTextField CC, JTextField BCC, JTextField subject,JButton addImg) {
        this.fontSizeComboBox__ = fontSizeComboBox__;
        this.Send = Send;
        this.Read = Read;
        this.boldButton = boldButton;
        this.italicButton = italicButton;
        this.color = color;
        this.fileButton = fileButton;
        this.detailFile = detailFile;
        this.editor__ = editor__;
        this.panelSendEmail = panelSendEmail;
        this.recipient = recipient;
        this.CC = CC;
        this.BCC = BCC;
        this.subject = subject;
        this.addImg=addImg;
    }

    public void SetSendClient() {
//        add(panelSendEmail);
//        setSize(700, 500);
//        setTitle("Send Email");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fontSizeComboBox__.setModel(new DefaultComboBoxModel<String>(FONT_SIZES));

        EditButtonActionListener editButtonActionListener =
                new EditButtonActionListener();

        Send.addActionListener(new Send());

        boldButton.setAction(new StyledEditorKit.BoldAction());
        boldButton.setHideActionText(true);
        boldButton.setText("Bold");
        boldButton.addActionListener(editButtonActionListener);

        italicButton.setAction(new StyledEditorKit.ItalicAction());
        italicButton.setHideActionText(true);
        italicButton.setText("Italic");
        italicButton.addActionListener(editButtonActionListener);

        color.addActionListener(new ColorActionListener());

        fontSizeComboBox__.addItemListener(new FontSizeItemListener());

        addImg.addActionListener(new PictureInsertActionListener());

        fileButton.addActionListener(new sendAttachment());

        editor__.requestFocusInWindow();
    }

    private class EditButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editor__.requestFocusInWindow();
        }
    }

    private class ColorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color newColor =
                    JColorChooser.showDialog(panelSendEmail, "Choose a color", Color.BLACK);
            if (newColor == null) {

                editor__.requestFocusInWindow();
                return;
            }
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            editor__.setCharacterAttributes(attr, false);
            editor__.requestFocusInWindow();
        }
    }

    private class FontSizeItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontSizeComboBox__.getSelectedIndex() == 0)) {

                return;
            }
            String fontSizeStr = (String) e.getItem();
            int newFontSize = 0;
            try {
                newFontSize = Integer.parseInt(fontSizeStr);
            } catch (NumberFormatException ex) {

                return;
            }
            fontSizeComboBox__.setAction(new StyledEditorKit.FontSizeAction(fontSizeStr, newFontSize));
            fontSizeComboBox__.setSelectedIndex(0); // initialize to (default) select
            editor__.requestFocusInWindow();
        }
    }

    private class PictureInsertActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            File pictureFile = choosePictureFile();

            if (pictureFile == null) {

                editor__.requestFocusInWindow();
                return;
            }

            ImageIcon icon = new ImageIcon(pictureFile.toString());
            JButton picButton = new JButton(icon);
            editor__.insertComponent(picButton);
            editor__.requestFocusInWindow();
        }

        private File choosePictureFile() {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG, JPG & GIF Images", "png", "jpg", "gif");
            chooser.setFileFilter(filter);

            if (chooser.showOpenDialog(panelSendEmail) == JFileChooser.APPROVE_OPTION) {

                return chooser.getSelectedFile();
            }
            else {
                return null;
            }
        }
    } // PictureInsertActionListener

    private class sendAttachment extends Component implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //CREATE NEW FILE CHOOSER
            JFileChooser chooser = new JFileChooser();
            //SELECT TYPE OF FILE CHOOSER
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //GET RESULT OF FILE CHOOSER
            int result = chooser.showOpenDialog(this);
            //IF CANCEL RETURN TO SEND MAIL WINDOW
            if (result == JFileChooser.CANCEL_OPTION)
                return;
            //CREATE NEW FILE FROM CHOOSER SELECTION
            file__ = chooser.getSelectedFile();
            //IF NO FILE SELECTED
            if (file__ == null || file__.getName().equals("")) {
                //DISPLAY ERROR MESSAGE
                JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            } else {
                //ADD ATTACHMENT TO EMAIL
//                    email.setAttachment(temp);
//                    email.setAttachmentName(temp.getName());
                //UPDATE Client.GUI
                detailFile.setText(file__.getName() + "(" + file__.length() / 1024 + "K)");
            }
        }
    }

    private StyledDocument getEditorDocument() {
        StyledDocument doc = (DefaultStyledDocument) editor__.getDocument();
        return doc;
    }

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DefaultStyledDocument doc = (DefaultStyledDocument) getEditorDocument();
                Email temp = new Email(username, recipient.getText(), CC.getText(), BCC.getText(), subject.getText(), doc);
                if(file__!=null) {
                    temp.setNameAttchment(file__.getName());
                    temp.setAttachment(file__);
                }
                oos = new ObjectOutputStream(link.getOutputStream());
                oos.writeObject("S");
                oos.writeObject(temp);
                ois = new ObjectInputStream(link.getInputStream());
                Object o = ois.readObject();
                JOptionPane.showMessageDialog(panelSendEmail,(String)o);
                oos.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }

        private File chooseFile() {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(panelSendEmail) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }
    }

    private class OpenFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            file__ = chooseFile();
            if (file__ == null) {
                return;
            }
            readFile(file__);
        }

        private File chooseFile() {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(panelSendEmail) == JFileChooser.APPROVE_OPTION) {

                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }

        private void readFile(File file) {
            StyledDocument doc = null;
            try (InputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                doc = (DefaultStyledDocument) ois.readObject();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(panelSendEmail, "Input file was not found!");
                return;
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
            editor__.setDocument(doc);
        }
    }
}
