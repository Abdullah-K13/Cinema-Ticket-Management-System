/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinematicketingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class admindispaly {
      
    ConnectionToDB con = new ConnectionToDB();
    Connection con_obj=con.EstablishConnection();
    PreparedStatement pstmt=null;
    Statement stmt=null;
    ResultSet res=null;
    private String name,email,desig,number;
    
    public boolean searchEmp(int ID) {
        String str = "select * from tbl_login where ID = '" + ID + "'";
        boolean b = false;

        try {
            pstmt = con_obj.prepareStatement(str);
            res = pstmt.executeQuery();

            while (res.next()) {
                name = res.getString("Fullname");
                email = res.getString("email");
                number = res.getString("number");
                desig = res.getString("designation");
                
                b = true;
                return b;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            b = false;
        }
        return b;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDesig() {
        return desig;
    }

    public String getNumber() {
        return number;
    }
    
}
