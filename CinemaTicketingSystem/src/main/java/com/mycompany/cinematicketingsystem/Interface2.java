/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cinematicketingsystem;
import static com.mycompany.cinematicketingsystem.SignUp.clientid;
import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class Interface2 extends javax.swing.JFrame {
    

    public static int clid;
    /**
     * 
     * Creates new form Interface
     */
    public Interface2() {
        initComponents();
         setLocationRelativeTo(null);
    }
    
    
    public static void GetClientIDByUsername(String username) {
      
        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            String sql = "{call GetClientidByUsername(?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.setString(1, username);
                callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
                callableStatement.execute();

                clid  = callableStatement.getInt(2);

                if (clid > 0) {
                    System.out.println("LoginId for username " + username + ": " + clid);
                    clientid = clid;
                } else {
                    System.out.println("No LoginId found for username " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 public static void fetchid(String username){
        

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            String sql = "{call GetClientid(?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.setString(1, username);
                callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
                callableStatement.execute();

                int loginId = callableStatement.getInt(2);

                if (loginId > 0) {
                    System.out.println("LoginId for username " + username + ": " + loginId);
                    clientid = loginId;
                } else {
                    System.out.println("No LoginId found for username " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkAdminLogin(String username, String password) {
        boolean isValid = false;

        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            String storedProcedureCall = "{call CheckAdminLogin(?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
                callableStatement.setString(1, username);
                callableStatement.setString(2, password);
                callableStatement.registerOutParameter(3, Types.BIT);

                callableStatement.executeUpdate();

                // Get the result from the stored procedure
                isValid = callableStatement.getBoolean(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static boolean checkUserLogin(String username, String password) {
        boolean isValid = false;

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            String storedProcedureCall = "{call CheckUserLogin(?, ?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
                callableStatement.setString(1, username);
                callableStatement.setString(2, password);
                callableStatement.registerOutParameter(3, Types.BIT);

                callableStatement.executeUpdate();

                // Get the result from the stored procedure
                isValid = callableStatement.getBoolean(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jLabelClose4 = new javax.swing.JLabel();
        jLabelMini4 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        Quit = new com.k33ptoo.components.KButton();
        signup = new com.k33ptoo.components.KButton();
        Login1 = new com.k33ptoo.components.KButton();
        passwordfield = new javax.swing.JCheckBox();
        txt2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setForeground(new java.awt.Color(0, 124, 190));

        jLabelClose4.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabelClose4.setForeground(new java.awt.Color(0, 124, 190));
        jLabelClose4.setText("x");
        jLabelClose4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelClose4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelClose4MouseClicked(evt);
            }
        });

        jLabelMini4.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabelMini4.setForeground(new java.awt.Color(0, 124, 190));
        jLabelMini4.setText("-");
        jLabelMini4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelMini4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMini4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelMini4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelClose4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelClose4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMini4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        kGradientPanel1.setkEndColor(new java.awt.Color(68, 160, 141));
        kGradientPanel1.setkGradientFocus(160);
        kGradientPanel1.setkStartColor(new java.awt.Color(9, 54, 55));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setForeground(new java.awt.Color(68, 160, 141));
        jLabel7.setText("Enter Username");

        jLabel8.setForeground(new java.awt.Color(68, 160, 141));
        jLabel8.setText("Enter Password");

        txt1.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        txt1.setForeground(new java.awt.Color(68, 160, 141));
        txt1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(68, 160, 141)));
        txt1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(68, 160, 141));
        jLabel9.setText("Log in ");

        jComboBox1.setForeground(new java.awt.Color(68, 160, 141));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose how you want to log in", "User", "Admin" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));

        Quit.setText("Quit");
        Quit.setkBorderRadius(50);
        Quit.setkEndColor(new java.awt.Color(68, 160, 141));
        Quit.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        Quit.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        Quit.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        Quit.setkIndicatorThickness(10);
        Quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitActionPerformed(evt);
            }
        });

        signup.setText("Sign Up");
        signup.setkBorderRadius(50);
        signup.setkEndColor(new java.awt.Color(68, 160, 141));
        signup.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        signup.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        signup.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        signup.setkIndicatorThickness(10);
        signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupActionPerformed(evt);
            }
        });

        Login1.setText("Login");
        Login1.setkBorderRadius(50);
        Login1.setkEndColor(new java.awt.Color(68, 160, 141));
        Login1.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        Login1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        Login1.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        Login1.setkIndicatorThickness(10);
        Login1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login1ActionPerformed(evt);
            }
        });

        passwordfield.setForeground(new java.awt.Color(68, 160, 141));
        passwordfield.setText("Show password");
        passwordfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordfieldActionPerformed(evt);
            }
        });

        txt2.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        txt2.setForeground(new java.awt.Color(68, 160, 141));
        txt2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(68, 160, 141)));
        txt2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Login1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(Quit, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt1)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, 0, 260, Short.MAX_VALUE)
                            .addComponent(passwordfield)
                            .addComponent(txt2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addGap(31, 31, 31)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordfield)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Quit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Login1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Where Cinematic");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Wonders");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Unveiled");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Oasis Cinema's");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelClose4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClose4MouseClicked

        System.exit(0);
    }//GEN-LAST:event_jLabelClose4MouseClicked

    private void jLabelMini4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMini4MouseClicked

        this.setState(JFrame.ICONIFIED); //to minimize JFrame
    }//GEN-LAST:event_jLabelMini4MouseClicked

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void QuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitActionPerformed
        // TODO add your handling code here:
        System.exit(0);

    }//GEN-LAST:event_QuitActionPerformed

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupActionPerformed
        // TODO add your handling code here:
       if(jComboBox1.getSelectedItem().toString() == "Choose how you want to log in"){
                       JOptionPane.showMessageDialog(null, "Please Select User");

       }
     
       else
      {
    nameandpass np = new nameandpass();
    np.setVisible(true);
        dispose();
      }
    }//GEN-LAST:event_signupActionPerformed

    private void Login1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login1ActionPerformed


        // TODO add your handling code here:
 int pw ;
        if(txt1.getText().length()<=0||txt2.getText().length()<=0){
                        JOptionPane.showMessageDialog(null, "Please Enter username and password");

        }
 else if(jComboBox1.getSelectedItem().toString() == "Choose how you want to log in"){
            JOptionPane.showMessageDialog(null, "Please select how you want to log in");
        }
       
        else {
     
         
     pw = Integer.parseInt(txt2.getText());
          
            if (jComboBox1.getSelectedItem().toString() == "User")
            {

boolean b = checkUserLogin(txt1.getText(), txt2.getText());
                if (b == true)

                {
             
                    JOptionPane.showMessageDialog(null, "Login successfull");
                   PurchaseTicket pt = new PurchaseTicket();
                   pt.setVisible(true);
                    GetClientIDByUsername(txt1.getText());
                   dispose();
                }
                else
                JOptionPane.showMessageDialog(null, "Login unseccessfull");
            }
            else if (jComboBox1.getSelectedItem().toString() == "Admin")
            {
boolean b;
               b = checkAdminLogin(txt1.getText(), txt2.getText());

                if (b == true)
                {
                    admin_login ad = new admin_login();
                                        JOptionPane.showMessageDialog(null, "Login successfull");

                    ad.setVisible(true);
                    dispose();

                }
                else
                JOptionPane.showMessageDialog(null, "Login unseccessfull");
            
     
//     catch(NumberFormatException e)
//     {
//         JOptionPane.showMessageDialog(null, e+"must be all numbers");
//     }
            }
 }
    }//GEN-LAST:event_Login1ActionPerformed

    private void passwordfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordfieldActionPerformed
        // TODO add your handling code here:
//        if (evt.getSource() == passwordfield) {
//            if (passwordfield.isSelected()) {
//                txt2.setEchoChar((char) 0);
//            } else {
//                txt2.setEchoChar('*');
//            }
//  }
    }//GEN-LAST:event_passwordfieldActionPerformed

    private void txt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton Login1;
    private com.k33ptoo.components.KButton Quit;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose4;
    private javax.swing.JLabel jLabelMini4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JCheckBox passwordfield;
    private com.k33ptoo.components.KButton signup;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    // End of variables declaration//GEN-END:variables
}
