package Client.BLL;

//import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


public class ViewAttachmentClient extends JFrame{
    //DECLARE FILEIN STREAM FOR GETTING STRING
    private static BufferedReader fileIn;
    //DECLARE COMPONENETS TO BE PLACED ON GUI
    private static JTextArea attachmentWindow;
    private static byte[] textAttachment;
    private static Container pane;
    private static FileOutputStream fileOut;
    private static String name;
    public void ViewTextAttachment(byte[] textAttachment, String name)
    {
        this.textAttachment = textAttachment;
        this.name = name;
        //SET WINDOW TITLE
        setTitle("View Text Attachment");
        //INITIALISE COMPONENTS
        attachmentWindow = new JTextArea(30,50);
        //SET UP JTEXTAREAS PROPERTIES
        attachmentWindow.setWrapStyleWord(true);
        attachmentWindow.setLineWrap(true);
        attachmentWindow.setEditable(false);
        //ADD COMPONENTS TO CONTENT PANEL
        pane = getContentPane();
        pane.add(attachmentWindow, BorderLayout.CENTER);
        pane.add(new JScrollPane(attachmentWindow));
        //CATCH FILE READ ERROR
        try
        {
            getTextAttachmentContents();
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, "Error Reading From File");
        }
    }

    //CONVERTS TEXT ATTACHMENT INTO STRING
    public void getTextAttachmentContents() throws IOException
    {
        //SET UP NEW FILE OUTPUT STREAM
        fileOut = new FileOutputStream(name);
        //WRITE FILE FROM BYTE ARRAY
        fileOut.write(textAttachment);
        //CREATE NEW FILE OBJECT
        File temp = new File(name);
        //CREATE NEW FILE INPUT STREAM
        fileIn = new BufferedReader(new FileReader(temp));
        //DECLARE VARIABLES FOR FILE INPUT
        String input;
        String text = "";
        //GET FIRST STRING FROM FILE
        input = fileIn.readLine();
        text = (text + input + "\n");
        //GET REST OF STRINGS FROM FILE
        while (input != null)
        {
            text = (text + input + "\n");
            input = fileIn.readLine();
        }
        //CLOSE STREAM
        fileIn.close();
        //UPDATE GUI WITH STRING
        attachmentWindow.setText(text);
    }

    private static JLabel picLabel;
    private static ImageIcon pictureAttachment;
    //DECLARE ATTACHMENT VARIABLES
    private static byte[] graphicsAttachment;

    public void ViewGraphicsAttachment(byte[] graphicsAttachment)
    {
        //GET BYTE ARRAY FROM CLIENT
        this.graphicsAttachment = graphicsAttachment;
        try
        {
            getGraphicsAttachmentContents();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //SET WINDOW TITLE
        setTitle("View Graphics Attachment");
        //ADD COMPONENTS TO CONTENT PANEL
        pane = getContentPane();
        pane.add(picLabel, BorderLayout.CENTER);
    }

    //GET IMAGE FROM BYTE ARRAY ATTACHMENT
    public void getGraphicsAttachmentContents() throws IOException
    {
        pictureAttachment = new ImageIcon(graphicsAttachment);
        picLabel = new JLabel(pictureAttachment);
    }

    private byte[] attachment;
//    private Player player;
    private File file;
    public void ViewMediaAttachment(byte[] attachment, String name)
    {
        try
        {
            //GET PASSED IN ATTACHMENT
            this.attachment = attachment;
            //SET UP FILE STREAM
            fileOut = new FileOutputStream(name);
            //WRITE ATTACHMENT TO FILE
            fileOut.write(attachment);
            //CREATE NEW FILE OBJECT
            file = new File(name);
            //SET WINDOW TITLE
            setTitle("Java Media Player");
            //SET UP CONTENT PANE
            pane = getContentPane();
            //CREATE MEDIA PLAYER AND CONTOL PANEL
            Desktop.getDesktop().open(new File(file.toURI()));
            //CREATE MEDIA PLAYER AND CONTOL PANEL
//            player = Manager.createPlayer(file.toURL());
//            player.addControllerListener(this);
//            player.start();
        }
        catch(FileNotFoundException fnfe)
        {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }
        catch(Exception e2)
        {
            e2.printStackTrace();
        }
    }
    //SET UP CONTROLLER EVENT LISTENER FOR PLAYER
//    public void controllerUpdate(ControllerEvent e)
//    {
//        if (e instanceof RealizeCompleteEvent)
//        {
//            //CREATE PLAY WINDOW AND ADD TO PANE
//            Component visualComponent = player.getVisualComponent();
//            if(visualComponent != null)
//            {
//                pane.add(visualComponent, BorderLayout.CENTER);
//            }
//            //CREATE CONTROL PANEL AND ADD TO WINDOW
//            Component controlsComponent = player.getControlPanelComponent();
//            if(controlsComponent != null)
//            {
//                pane.add(controlsComponent, BorderLayout.SOUTH);
//            }
//            //SET UP LAYOUT
//            pane.doLayout();
//        }
//    }
}
