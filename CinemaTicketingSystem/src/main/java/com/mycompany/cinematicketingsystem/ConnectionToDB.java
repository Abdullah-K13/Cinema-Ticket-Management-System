
package com.mycompany.cinematicketingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectionToDB {
     Connection con = null;

    public Connection EstablishConnection() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
           con= DriverManager.getConnection("jdbc:ucanaccess://D:\\test.accdb");
        //   JOptionPane.showMessageDialog(null, "connected");
        }
        catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex);
                }
        return con;
    }
    
}
