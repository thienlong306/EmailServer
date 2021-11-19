package BLL;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.*;

public class SendClient extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private JTextPane editor__;
    private JButton Send;
    private JButton boldButton;
    private JButton color;
    private JButton italicButton;
    private JComboBox<String> fontSizeComboBox__;
    private JButton fileButton;
    private JLabel detailFile;

    private JPanel panelSendEmail;
    private JButton Read;

    private File file__;

    private static final String[] FONT_SIZES = {"Font Size", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};

    public void setBoldButton(JButton boldButton, JTextPane editor__) {
        boldButton.setAction(new StyledEditorKit.BoldAction());
        boldButton.setHideActionText(true);
        boldButton.setText("Bold");
        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor__.requestFocusInWindow();
            }
        });
    }
//    public SendClient() {
//        add(panelSendEmail);
//        setSize(700, 500);
//        setTitle("Send Email");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        fontSizeComboBox__.setModel(new DefaultComboBoxModel<String>(FONT_SIZES));
//
//        EditButtonActionListener editButtonActionListener =
//                new EditButtonActionListener();
//
//        Send.addActionListener(new SaveFileListener());
//        Read.addActionListener(new OpenFileListener());
//
//        italicButton.setAction(new StyledEditorKit.ItalicAction());
//        italicButton.setHideActionText(true);
//        italicButton.setText("Italic");
//        italicButton.addActionListener(editButtonActionListener);
//
//        color.addActionListener(new ColorActionListener());
//
//        fontSizeComboBox__.addItemListener(new FontSizeItemListener());
//
//        fileButton.addActionListener(new sendAttachment());
//
//        editor__.requestFocusInWindow();
//    }



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
            File temp = chooser.getSelectedFile();
            //IF NO FILE SELECTED
            if (temp == null || temp.getName().equals("")) {
                //DISPLAY ERROR MESSAGE
                JOptionPane.showMessageDialog(this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            } else {
                //ADD ATTACHMENT TO EMAIL
//                    email.setAttachment(temp);
//                    email.setAttachmentName(temp.getName());
                //UPDATE GUI
                detailFile.setText(temp.getName() + "(" + temp.length() / 1024 + "K)");
            }
        }
    }

    private StyledDocument getEditorDocument() {
        StyledDocument doc = (DefaultStyledDocument) editor__.getDocument();
        return doc;
    }

    private class SaveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (file__ == null) {
                file__ = chooseFile();
                if (file__ == null) {
                    return;
                }
            }
            DefaultStyledDocument doc = (DefaultStyledDocument) getEditorDocument();

            try (
                    OutputStream fos = new FileOutputStream(file__);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {

                oos.writeObject(doc);
            } catch (IOException ex) {

                throw new RuntimeException(ex);
            }

//            setFrameTitleWithExtn(file__.getName());
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
