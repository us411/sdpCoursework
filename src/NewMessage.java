
import java.awt.*;
import static java.awt.SystemColor.window;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class NewMessage extends JFrame // we can use the features of the JFrame class
        implements ActionListener {

    private String id;
    public JTextField receiver = new JTextField(10);
    public JTextField subject = new JTextField(10);
    private JTextArea textArea = new JTextArea("Write your message here");
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JButton sendTheMessage = new JButton("Send");
    private JButton clear = new JButton("Clear");
    private JButton home = new JButton("Home");
    private JTextField labelTxt = new JTextField(10);
    private JComboBox priority = new JComboBox();

    public NewMessage() {

        setLayout(new BorderLayout());
        setSize(750, 250);
        setLocation(500, 280);
        setResizable(false);
        setTitle("New Message");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel top = new JPanel();
        top.setBackground(Color.LIGHT_GRAY);
        top.add(new JLabel("Recipient")).setBackground(Color.pink);
        top.add(receiver);
        top.add(new JLabel("Subject:")).setBackground(Color.pink);
        top.add(subject);
        top.add(new JLabel("Label"));
        top.add(labelTxt).setEnabled(true);
        labelTxt.addActionListener(this); // ASSIGN ACTION LISTENER 
        top.add(new JLabel("Priority:"));

        top.add(priority);
        priority.addActionListener(this);
        priority.addItem("1");
        priority.addItem("2");
        priority.addItem("3");
        priority.addItem("4");
        priority.addItem("5");

        top.add(sendTheMessage);
        sendTheMessage.addActionListener(this);

        add("North", top);

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setBackground(Color.white);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(250, 100));
        textArea.setText("Write your message here....");
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setText("");
            }
        });
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(450, 150));

        JPanel middle = new JPanel();
        middle.setBackground(Color.LIGHT_GRAY);
        middle.add(scrollPane);
        add("Center", middle);
        setVisible(true);

        JPanel bottom = new JPanel();
        add("South", bottom);
        bottom.setBackground(Color.LIGHT_GRAY);
        bottom.add(clear);
        bottom.add(home);
        home.addActionListener(this);
        clear.addActionListener(this);
    }
    /* in the action performed method are stated all of the events
     we want to happen 
     when a button is pressed for example*/

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sendTheMessage && receiver.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You cannot send"
                    + "\n a message without recipient",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } // clear the text fields

        if (e.getSource() == home) {
            new EmailManager().refreshData();
            this.setVisible(false);
        }
        if (e.getSource() == clear) {
            textArea.setText("");
            receiver.setText("");
            subject.setText("");
            labelTxt.setText("");
        } //open a EmailManager window
        if (e.getSource() == home) {
            new EmailManager().refreshData();
            this.setVisible(false);
        }
        if (e.getSource() == sendTheMessage) {
            msgSend();
        }
    }

    public void labelMessage() {
        String lb2 = "", id2 = "";
        try {
            id2 = MessageData.setMsgId();
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "Oop, something went wrong",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        try {
            lb2 = labelTxt.getText();
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(null, "Please enter label",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        MessageData.setLabel(id2, lb2);
        textArea.setText(MessageData.listAll());
    }

    public void msgSend() {
        /* setMsgID is the method in MessageData where the program sets the 
         Id automatically*/
        String messageId = MessageData.setMsgId();
        int pri = Integer.parseInt(priority.getSelectedItem().toString());
        String s = MessageData.DEFAULT_EMAIL; // set the send to the default email adrress
        String re = receiver.getText(); //get the text from the reciever field
        String sub = subject.getText();
        String m = textArea.getText();
        String l = labelTxt.getText();
        /* sendMesg is the method in MessageData where 
         we insert values into the database*/
        MessageData.sendMsg(messageId, pri, s, re, sub, m, l);
        JOptionPane.showMessageDialog(null, "The message has been sent!");
        textArea.setText("");
        receiver.setText("");
        subject.setText("");
        labelTxt.setText("");
        dispose();
        EmailManager window = new EmailManager();
    }

}
