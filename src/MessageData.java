// Skeleton version of MessageData.java that links to Java Derby database.
// NOTE: You should not have to make any changes to the other
// Java GUI classes for this to work, if you complete it correctly.
// Indeed these classes shouldn't even need to be recompiled

import java.sql.*;
import java.util.*;
import java.io.*;
import static java.lang.System.*;
import javax.swing.JTextField;
import org.apache.derby.drda.NetworkServerControl;

public class MessageData {

    private static Connection connection;
    private static Statement stmt;
    public static final String DEFAULT_EMAIL = "zd405@gre.ac.uk";

    static {
        // standard code to open a connection and statement to Java Derby database
        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            // Load JDBC driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //Establish a connection
            String sourceURL = "jdbc:derby://localhost:1527/"
                    + new File("EmailsDB").getAbsolutePath() + ";";
            connection = DriverManager.getConnection(sourceURL, "student", "student");
            stmt = connection.createStatement();
        } // The following exceptions must be caught
        catch (ClassNotFoundException cnfe) {
            out.println(cnfe);
        } catch (SQLException sqle) {
            out.println(sqle);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String pad(String string, int width) {
        if (string == null) {
            string = "";
        }
        width -= string.length();
        for (int i = 0; i < width; ++i) {
            string += " ";
        }
        return string;
    }

    private static String formatListEntry(ResultSet res) throws SQLException {
        return res.getString("ID") + " " + pad(stars(res.getInt("Priority")), 8) + " " + pad(res.getString("Sender"), 30) + " " + pad(res.getString("Label"), 6) + " " + res.getString("Subject") + "\n";
    }

    private static String listHeader() {
        String output = "Id Priority From                           Label  Subject\n";
        output += "== ======== ====                           =====  =======\n";
        return output;
    }

    public static String listAll() {
        String output = listHeader();
        try {
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGE");
            while (res.next()) { // there is a result
                output += formatListEntry(res);

            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return output;
    }

    static String listLabelled(String label) {
        String output = listHeader();
        try {
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGE WHERE Label = '" + label + "'");
            while (res.next()) { // there is a result
                output += formatListEntry(res);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return output;
    }

    static String getLabel(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGE WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                return res.getString("Label");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static String setLabel(String id, String label) {
        // SQL UPDATE statement required. For instance if label is "todo" and id is "04" then updateStr is
        // UPDATE Libary SET Label = 'todo' WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGE SET Label = '" + label + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
        return listLabelled(label);
    }

    static String getMessage(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT Message from STUDENT.MESSAGE WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                return res.getString("Message");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setMessage(String id, String message) {
        // SQL UPDATE statement required. For instance if message is "Hello, how are you?" and id is "04" then updateStr is
        // UPDATE Libary SET Message = 'Hello, how are you?' WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGE SET Message = '" + message + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getPriority(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * from STUDENT.MESSAGE WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                return res.getInt("Priority");
            } else {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -1; // -1 means no such item
        }
    }

    public static void setPriority(String id, int priority) {
        // SQL UPDATE statement required. For instance if priority is 5 and id is "04" then updateStr is
        // UPDATE Libary SET Priority = 5 WHERE id = '04'
        String updateStr = "UPDATE STUDENT.MESSAGE SET Priority = " + priority + " WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static String getRecipient(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGE WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                //To is a reserved word in Derby SQL, Too has been used instead
                return res.getString("Too");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setRecipient(String id, String to) {

        String updateStr = "UPDATE STUDENT.MESSAGE SET too = '" + to + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getSender(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGE WHERE ID = '" + DEFAULT_EMAIL + id + "'");
            if (res.next()) { // there is a result
                return res.getString("Sender");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setSender(String id, String from) {
        /* update the sender with the default email*/
        String updateStr = "UPDATE STUDENT.MESSAGE SET sender = '" + DEFAULT_EMAIL + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getSubject(String id) {
        try {
            // Need single quote marks ' around the id field in SQL. This is easy to get wrong!
            // For instance if id was "04" the SELECT statement would be:
            // SELECT * FROM MessageTable WHERE id = '04'
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGE WHERE ID = '" + id + "'");
            if (res.next()) { // there is a result
                return res.getString("Subject");
            } else {
                return null; // null means no such item
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static void setSubject(String id, String subject) {
        String updateStr = "UPDATE STUDENT.MESSAGE SET Subject = '" + subject + "' WHERE ID = '" + id + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String stars(int priority) {
        String stars = "";
        for (int i = 0; i < priority; ++i) {
            stars += "*";
        }
        return stars;
    }

    // close the database
    public static void close() {
        try {
            connection.close();
        } catch (Exception e) {
            // this shouldn't happen
            System.out.println(e);
        }
    }
//automatically adds the next ID no need the user to put it

    static String setMsgId() {
        int id = 0;
        String rowS = "0";
        try {
            ResultSet res = stmt.executeQuery("SELECT ID from STUDENT.MESSAGE");
            while (res.next()) {
                id++;
            }
            //reproduces the database format 01 
            id++;
            if (id < 10) {
                rowS += String.valueOf(id);
                return rowS;
            } else {
                // converts the int value into string
                rowS = String.valueOf(id);
                return rowS;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    /*this method is connected to NewMessage when 
     the send Button is pressed 
     it writes into the database the values that the user puts*/

    public static boolean
            sendMsg(String messageId, int pri, String s, String re, String sub, String m, String l) {

        String updateStr = "INSERT INTO STUDENT.MESSAGE "
                + "(ID, PRIORITY, SENDER, TOO, SUBJECT, MESSAGE, LABEL) \n"
                + "	VALUES "
                + "('" + messageId + "', " + pri + ", '" + s + "', '"
                + re + "', '" + sub + "', '" + m + "', '" + l + "')";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
//searchs the facility for the inputed label 

    static String listString(String string) {
        String output = listHeader();
        try {
            ResultSet res = stmt.executeQuery("SELECT * FROM STUDENT.MESSAGE WHERE LOCATE "
                    + "('" + string + "',label)>0");
            while (res.next()) {
                output += formatListEntry(res);
            }
        } catch (Exception e5) {
            System.out.println(e5);
            return null;
        }
        return output;
    }

}
