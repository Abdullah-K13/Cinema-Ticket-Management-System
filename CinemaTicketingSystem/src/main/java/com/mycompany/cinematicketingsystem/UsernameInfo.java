
package com.mycompany.cinematicketingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class UsernameInfo {
    private String username,userpass,ID;
     ConnectionToDB con = new ConnectionToDB();
    Connection con_obj=con.EstablishConnection();
    PreparedStatement pstmt=null;
    Statement stmt=null;
    ResultSet res=null;

    public UsernameInfo() {
    }
    

    public UsernameInfo(String username, String userpass) {
        this.username = username;
        this.userpass = userpass;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpass() {
        return userpass;
    }
    
     public boolean addusername() {
        boolean b = false;

        String sql = "insert into user_login(username,password)values('" + this.username + "','" + this.userpass + "')";

        try {

            stmt = con_obj.createStatement();
            int res = stmt.executeUpdate(sql);

            if (res > 0) {

                b = true;

            } else {

                JOptionPane.showMessageDialog(null, "Error");
                b = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return b;

    }
     public boolean deleteUsername(int ID) {
        String str = "delete from user_login where ID = '" + ID + "'";
        boolean b = false;
        try {
            stmt = con_obj.createStatement();
            int res = stmt.executeUpdate(str);
            if (res > 0) {

                b = true;
            } else {

                b = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return b;
    }
  public boolean searchUsername(int ID) {
        String str = "select * from user_login where ID = '" + ID + "'";
        boolean b = false;

        try {
            pstmt = con_obj.prepareStatement(str);
            res = pstmt.executeQuery();

            while (res.next()) {
                 this.username = res.getString("username");
                this.userpass = res.getString("password");
              
                
                b = true;
                return b;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            b = false;
        }
        return b;
    }   

    public String getID() {
        return ID;
    }
     
   public boolean searchId(String username) {
        String str = "select * from user_login where username = '" + username + "'";
        boolean b = false;

        try {
            pstmt = con_obj.prepareStatement(str);
            res = pstmt.executeQuery();

            while (res.next()) {
                 this.ID = res.getString("ID");
              
                
                b = true;
                return b;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            b = false;
        }
        return b;
    }   
   
     
  
public boolean loginUser() {
   boolean b = false;
        String loginString = "select * from user_login where username='" + this.username + "' and password='" + this.userpass + "'";

        try {
            pstmt = con_obj.prepareStatement(loginString);
            res = pstmt.executeQuery();
            if (res.next()) {
                b = true;
            } else {
                b = false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            b = false;
        }
        return b;

    }
public boolean updateUsername(int ID) {
        boolean b = false;

        String sql = "UPDATE user_login SET username='" + this.username + "' and password='" + this.userpass + "' WHERE ID = '" + ID + "'";

        try {

            stmt = con_obj.createStatement();
            int res = stmt.executeUpdate(sql);

            if (res > 0) {
                b = true;
            } else {

                JOptionPane.showMessageDialog(null, "Error");
                b = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        return b;

    }
}
