package Client.BLL;

import Enity.Email;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Client.BLL.CipherClient.encryptData;
import static Client.EmailClient.link;
import static Client.GUI.Login.username;
import static Client.GUI.Main.ois;
import static Client.GUI.Main.oos;

public class ScheduleClient extends JFrame {
    private JPanel pickDate;
    private JPanel pickD;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton sendButton;
    private JPanel pickT;
    private JLabel label;
    private String date;
    private Email email;

    public void setEmail(Email email){
        this.email=email;
    }

    ScheduleClient() {
        setTitle("Schedule");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 500;
        final int WIDTH = 800;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        UtilDateModel model = new UtilDateModel();
//model.setDate(20,04,2014);
// Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
// Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        pickD =new JPanel();
        pickD.add(datePicker);
        pickDate=new JPanel();
        pickDate.add(pickD);
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        for (int i=0;i<24;i++){
            comboBox1.addItem(i);
        }
        for (int i=0;i<60;i++){
            comboBox2.addItem(i);
        }
        label=new JLabel();
        label.setText(":");
        pickDate.add(comboBox1);
        pickDate.add(label);
        pickDate.add(comboBox2);
        sendButton=new JButton();
        sendButton.setText("Giửi");
        pickDate.add(sendButton);
        add(pickDate);

        pack();
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(date+" "+ comboBox1.getItemAt(comboBox1.getSelectedIndex())+":" + comboBox2.getItemAt(comboBox2.getSelectedIndex()));

             try {
                 Calendar calendar = Calendar.getInstance();
                 String temp[] = date.split("-");
                 calendar.set(Calendar.DATE,Integer.parseInt(temp[0]));
                 calendar.set(Calendar.MONTH,Integer.parseInt(temp[1])-1);
                 calendar.set(Calendar.YEAR,Integer.parseInt(temp[2]));
                 int hour= Integer.parseInt(String.valueOf(comboBox1.getItemAt(comboBox1.getSelectedIndex())));
                 int minute= Integer.parseInt(String.valueOf(comboBox2.getItemAt(comboBox2.getSelectedIndex())));
                 calendar.set(Calendar.HOUR_OF_DAY, hour);
                 calendar.set(Calendar.MINUTE, minute);
                 calendar.set(Calendar.SECOND, 0);
                 calendar.set(Calendar.MILLISECOND, 0);
                 oos = new ObjectOutputStream(link.getOutputStream());
                 oos.writeObject("SC");
                 Object encryt=encryptData(email);
                 oos.writeObject(encryt);
//                 oos.writeObject(email);
                 oos.writeObject(calendar);
                 ois = new ObjectInputStream(link.getInputStream());
                 Object o = ois.readObject();
                 JOptionPane.showMessageDialog(pickDate,(String)o);
                 oos.flush();
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             } catch (ClassNotFoundException classNotFoundException) {
                 classNotFoundException.printStackTrace();
             } catch (NullPointerException ex){
                 JOptionPane.showMessageDialog(null,"Lỗi");
             }
            }
        });
    }

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                date=dateFormatter.format(cal.getTime());
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
