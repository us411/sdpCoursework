
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class LogIn extends JFrame
        implements ActionListener {

    JButton logicon = new JButton("Log In");
    JPanel panel = new JPanel();
    JTextField username = new JTextField(15);
    JLabel user = new JLabel("Username");
    JLabel passw = new JLabel("Password");
    JPasswordField pass = new JPasswordField(15); // text field for passwords; the text will be shown with ***

    LogIn() {

        super("LogIn Autentification");
        JPanel middle = new JPanel();
        middle.setBackground(Color.white);
        setSize(300, 200);
        setLocation(500, 280);
        setResizable(false);
        middle.add(new JLabel("Username"));
        middle.add(username);
        username.setToolTipText("zd405");
        pass.setToolTipText("123");
        middle.add(new JLabel("Password"));
        middle.add(pass);
        middle.add(logicon);
        logicon.setBounds(110, 100, 80, 20);
        logicon.addActionListener((ActionListener) this);
        panel.setLayout(null);
        username.setBounds(70, 30, 150, 20);
        pass.setBounds(70, 65, 150, 20);

        getContentPane().add(panel);
        getContentPane().add(middle);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String user = username.getText();
        char[] password = pass.getPassword();
        boolean check = new String(username.getText()).equals("zd405");
        if (!check || !isPasswordCorrect(password)) { //if the username or password are not those
            JOptionPane.showMessageDialog(null, "Wrong Password / Username"); //the system will show a msg 
            username.setText(""); //resets the username text to nothing
            pass.setText(""); // resets the pass text to nothing
            username.requestFocus();

        } else if (e.getSource() == logicon) {

            EmailManager CloseCurrentWindow = new EmailManager(); //if the username and pass are correct 
            CloseCurrentWindow.setVisible(true); // Email manager will open
            dispose();
        }
    }

    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = {'1', '2', '3'};

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }
        //Zero out the password.
        Arrays.fill(correctPassword, '0');
        return isCorrect;
    }
}
