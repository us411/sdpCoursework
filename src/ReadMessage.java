import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class ReadMessage extends JFrame
        implements ActionListener {

    private String id;
    private JComboBox priority = new JComboBox();
    private JButton update = new JButton("Update");
    private JTextArea textArea = new JTextArea();
    private JButton home = new JButton("Home");
    private JButton reply = new JButton("Reply");
    private JScrollPane scrollPane = new JScrollPane(textArea);

    public ReadMessage(String id) {

        this.id = id;
        setLayout(new BorderLayout());
        setSize(500, 250);
        setLocation(500, 280);
        setResizable(false);
        setTitle("Message Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel top = new JPanel();
        top.setBackground(Color.LIGHT_GRAY);

        top.add(new JLabel("Enter Priority:"));
        top.add(priority);
        priority.addActionListener(this);
        priority.addItem("1");
        priority.addItem("2");
        priority.addItem("3");
        priority.addItem("4");
        priority.addItem("5");
        top.add(reply);
        reply.setToolTipText("Reply to this message");
        reply.addActionListener(this);

        add("North", top);

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setBackground(Color.white);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(450, 150));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        JPanel middle = new JPanel();
        middle.setBackground(Color.LIGHT_GRAY);
        middle.add(scrollPane);
        add("Center", middle);
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.LIGHT_GRAY);
        add("South", bottom);
        bottom.add(update);
        update.setToolTipText("Click to change the priority of the message");
        update.addActionListener(this);
        bottom.add(home);
        home.addActionListener(this);

        displayMessage();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == update) {
                updatePri();
                displayMessage();
            }
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "Please enter a valid priority value",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        if (e.getSource() == home) {
            new EmailManager().refreshData();
            this.setVisible(false);
        }
        if(e.getSource() == reply){
            NewMessage replyWindow = new NewMessage();
            replyWindow.subject.setText("Re:" +MessageData.getSubject(id));
            replyWindow.receiver.setText(MessageData.getRecipient(id));
            dispose();
        }

    }

    private void displayMessage() {
        String subject = MessageData.getSubject(id);
        try {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "No Message found",
                        "ERROR", JOptionPane.WARNING_MESSAGE);
            } else {
                textArea.setText("Subject: " + subject);
                textArea.setText("From: " + MessageData.DEFAULT_EMAIL);
                textArea.append("\nTo: " + MessageData.getRecipient(id));
                textArea.append("\nPriority: " + MessageData.stars(MessageData.getPriority(id)));
                textArea.append("\n\n" + MessageData.getMessage(id));
                setVisible(true);
            }
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(null, "Oops, something went wrong",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updatePri() {
        int priorityValue = Integer.parseInt(priority.getSelectedItem().toString());
        MessageData.setPriority(id, priorityValue);
    }

}
