
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EmailManager extends JFrame
        implements ActionListener {

    private JButton search = new JButton("Search");
    private JTextField inputSearch = new JTextField(10);
    private JButton read = new JButton("Read Message");
    private JTextField idTextField = new JTextField(2);
    private JButton label = new JButton("Add");
    private JTextField lblTxt = new JTextField(10);
    public JButton newMessage = new JButton("New Message");
    private JButton quit = new JButton("Exit");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JButton delete = new JButton("Delete");

    public static void main(String[] args) {
        new LogIn();
    }

    public EmailManager() {

        setLayout(new BorderLayout());
        setSize(600, 450);
        setLocation(500, 280);
        setResizable(false);
        setTitle("Email Manager");
        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //TOP PANEL        
        JPanel top = new JPanel();
        top.setBackground(Color.LIGHT_GRAY);
        top.add(read);

        read.addActionListener(this);
        top.add(idTextField);
        idTextField.setToolTipText("Enter Message ID");
        top.add(newMessage);

        newMessage.addActionListener(this);
        top.add(quit);

        quit.addActionListener(this);
        add("North", top);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.white);
        textArea.setPreferredSize(new Dimension(560, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setPreferredSize(new Dimension(560, 300));

        JPanel middle = new JPanel();
        middle.setBackground(Color.LIGHT_GRAY);
        middle.add(scrollPane);
        add("Center", middle);
        textArea.setText(MessageData.listAll());
        textArea.setEditable(false);
        setVisible(true);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.LIGHT_GRAY);
        bottom.add(inputSearch);
        inputSearch.setText("Enter a label");
        inputSearch.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        inputSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputSearch.setText("");
            }
        });
        bottom.add(search);
        search.addActionListener(this);
        bottom.add(new JLabel("Label:"));
        bottom.add(lblTxt);
        bottom.add(label);
        bottom.add(delete);
        delete.addActionListener(this);
        add("South", bottom);
        label.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            int question;
            JOptionPane.showConfirmDialog(null, "Are you sure you want "
                    + "to delete this label?");
            question = 0;

            deleteLabel();
            JOptionPane.showMessageDialog(null, "Label has been deleted.");
        }
        String string = (inputSearch.getText());
        if (e.getSource() == search) {

            textArea.setText(MessageData.listString(string));
        }

        try {
            if (e.getSource() == read && idTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select Message ID",
                        "Error", JOptionPane.WARNING_MESSAGE);
            } else if (e.getSource() == read) {
                new ReadMessage(idTextField.getText());
                this.setVisible(false);
            }
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "Error",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
        if (e.getSource() == newMessage) {
            new NewMessage();
            this.setVisible(false);
        }
        try {
            if (e.getSource() == quit) {
                int ans;
                JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                ans = 0;
                MessageData.close();
                System.exit(0);
            }
        } catch (Exception e3) {
            System.out.println("Somthin went wrong");
        }
        if (e.getSource() == label) {
            labelMessage();
        }
        try {
            if (e.getSource() == label && idTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No message ID is selected",
                        "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex1) {
            JOptionPane.showMessageDialog(null, "Oops, something went wrong",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void refreshData() { //after the 
        textArea.setText(MessageData.listAll());
    }

    public void labelMessage() {
        String lb2 = "", id2 = "";
        try {
            id2 = idTextField.getText(); //takes the value from the id field

        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "Oop, something went wrong",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        try {
            lb2 = lblTxt.getText(); //takes the value from the label field
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(null, "Please enter label",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        MessageData.setLabel(id2, lb2); // inserts in to the DB the label where the selected id is found.
        textArea.setText(MessageData.listAll()); //refreshes the textArea in Email manager

    }

    public void deleteLabel() {
        String lb2 = "", id2 = "";
        try {
            id2 = idTextField.getText(); //takes the value from the id field

        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "Oop, something went wrong",
                    "ERROR", JOptionPane.WARNING_MESSAGE);
        }

        MessageData.setLabel(id2, "  ");
        textArea.setText(MessageData.listAll());
    }

}
