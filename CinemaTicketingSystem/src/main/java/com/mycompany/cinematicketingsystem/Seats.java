/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cinematicketingsystem;

import static com.mycompany.cinematicketingsystem.Screening.hallid;
import static com.mycompany.cinematicketingsystem.Screening.slotid;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class Seats extends javax.swing.JFrame {
public static int hallid;
public static int slotid;
public static String[] selectedseats = new String[50];
public static int seatcount = 0;
    public static int test=PurchaseTicket.quan;
    public Seats() {
        initComponents();
       counter.setText(String.valueOf(seatcount));

        
        setLocationRelativeTo(null);
    DefaultTableModel tbmodel = (DefaultTableModel) slots.getModel();
try {
    Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345");

    // Call the stored procedure
    String storedProcedureCall = "{call GetAllSlots}";
    try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
        ResultSet res = callableStatement.executeQuery();
        ResultSetMetaData metaData = res.getMetaData();

        // Remove existing columns from the table model
        tbmodel.setColumnCount(0);

        // Add columns to the table model
        int columns = metaData.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            tbmodel.addColumn(metaData.getColumnName(i));
        }

        // Add rows to the table model
        while (res.next()) {
            Object[] row = new Object[columns];
            for (int i = 1; i <= columns; i++) {
                row[i - 1] = res.getObject(i);
            }
            tbmodel.addRow(row);
        }
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}
    }
 
    public void initializeSeatButtons() {
JButton[] seatButtons = {
    A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14,
    B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14,
    C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14,
    D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, D14,
    E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12, E13, E14,
    F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14,
    G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12, G13, G14,
    H1, H2, H3, H4, H5, H6, H7, H8, H9, H10, H11, H12, H13, H14,
    I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14,
    J1, J2, J3, J4, J5, J6, J7, J8, J9, J10, J11, J12, J13, J14};
//JButton[] seatButtons = new JButton[14]; // Assuming 10 rows and 14 columns
//
//int buttonIndex = 0;
//
//for (char rowChar = 'A'; rowChar <= 'A'; rowChar++) {
//    for (int columnNum = 1; columnNum <= 14; columnNum++) {
//        String buttonName = String.format("%c%d", rowChar, columnNum);
//        seatButtons[buttonIndex] = new JButton(buttonName);
//        buttonIndex++;
//    }
//}
    
    
    for (int i = 0; i < seatButtons.length; i++) {
        JButton currentButton = seatButtons[i];
        setSeatButtonProperties(currentButton);
       // currentButton.addActionListener(new SeatSelectionListener(currentButton.getName()));
       // System.out.println("Button Name: " + currentButton.getName());
    }
    
    }
    private void setSeatButtonProperties(JButton button) {
               // System.out.println("Button Name: " + button.getText());

        // Retrieve seat status from the database
        String seatStatus = getStatusFromDatabase(button.getText()); // Using button name as SeatLabel
       if ("Booked".equals(seatStatus)) {
    button.setBackground(Color.red);
} else if ("available".equals(seatStatus))  {
    button.setBackground(Color.green);
}
        else {
    // If status is null, set it to green
    button.setBackground(Color.green);
}
//       
      //  button.setBackground(seatStatus.equals(seatStatus) ? Color.RED : Color.GREEN);
    }
public void updateSeatStatus(String row, int seatNumber, String newStatus) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        String storedProcedureCall = "{call UpdateSeatStatus(?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            callableStatement.setString(1, row);
            callableStatement.setInt(2, seatNumber);
            callableStatement.setString(3, newStatus);
            callableStatement.executeUpdate();
            System.out.println("Seat status updated successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//
//public static void fetchstudentinfo(int stdid){
//        
//
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
//            String sql = "{call GetStudnfo(?, ?,?,?,?,?,?, ?,?,?,?,?)}";
//            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
//                callableStatement.setInt(1, stdid);
//                callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
//                callableStatement.execute();
//
//                          stdName = callableStatement.getString(2);
//
//
//                if (loginId > 0) {
//                    System.out.println("LoginId for username " + hallname + ": " + loginId);
//                    hallid= loginId;
//                } else {
//                    System.out.println("No LoginId found for username " + hallname);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void fetchhallid(String hallname){
        

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            String sql = "{call GetHallID(?, ?)}";
            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.setString(1, hallname);
                callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
                callableStatement.execute();

                int loginId = callableStatement.getInt(2);

                if (loginId > 0) {
                    System.out.println("LoginId for username " + hallname + ": " + loginId);
                    hallid= loginId;
                } else {
                    System.out.println("No LoginId found for username " + hallname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
  private String getStatusFromDatabase(String seatLabel) {
   String status = "available"; // Default status

    String[] parts = seatLabel.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
    String alphabetPart = parts[0]; // Contains the alphabet part
    int numericPart = Integer.parseInt(parts[1]);  // Contains the numeric part

    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        String storedProcedureCall = "{call GetSeatStatus(?, ?, ?, ?, ?,?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Assuming the parameters' order in the stored procedure
            callableStatement.setInt(1, PurchaseTicket.movieId); // Adjust as needed
            callableStatement.setInt(2, hallid); // Adjust as needed
            callableStatement.setInt(3, slotid); // Adjust as needed
            callableStatement.setInt(4, numericPart); // Adjust as needed
          callableStatement.setString(5, alphabetPart);
            callableStatement.registerOutParameter(6, Types.VARCHAR);

            callableStatement.execute(); // Use executeQuery for stored procedures

            status = callableStatement.getString(6); // Retrieve the output parameter value
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return status;
}

 static class SeatSelectionListener implements ActionListener {
    private String seatLabel;

    public SeatSelectionListener(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle seat selection, e.g., change seat status in the database
        System.out.println("Selected Seat: " + seatLabel);
    }

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
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        A1 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        C1 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        E1 = new javax.swing.JButton();
        F1 = new javax.swing.JButton();
        G1 = new javax.swing.JButton();
        H1 = new javax.swing.JButton();
        I1 = new javax.swing.JButton();
        J1 = new javax.swing.JButton();
        J2 = new javax.swing.JButton();
        I2 = new javax.swing.JButton();
        H2 = new javax.swing.JButton();
        G2 = new javax.swing.JButton();
        F2 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        C2 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        A2 = new javax.swing.JButton();
        H4 = new javax.swing.JButton();
        F4 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        I4 = new javax.swing.JButton();
        D4 = new javax.swing.JButton();
        C4 = new javax.swing.JButton();
        A4 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        J4 = new javax.swing.JButton();
        G4 = new javax.swing.JButton();
        A3 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        C3 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        F3 = new javax.swing.JButton();
        G3 = new javax.swing.JButton();
        H3 = new javax.swing.JButton();
        I3 = new javax.swing.JButton();
        J3 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        C6 = new javax.swing.JButton();
        D5 = new javax.swing.JButton();
        F5 = new javax.swing.JButton();
        D6 = new javax.swing.JButton();
        A6 = new javax.swing.JButton();
        F6 = new javax.swing.JButton();
        I6 = new javax.swing.JButton();
        C5 = new javax.swing.JButton();
        I5 = new javax.swing.JButton();
        E6 = new javax.swing.JButton();
        E5 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        J6 = new javax.swing.JButton();
        G5 = new javax.swing.JButton();
        J5 = new javax.swing.JButton();
        H5 = new javax.swing.JButton();
        G6 = new javax.swing.JButton();
        A5 = new javax.swing.JButton();
        H6 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        C8 = new javax.swing.JButton();
        D7 = new javax.swing.JButton();
        F7 = new javax.swing.JButton();
        D8 = new javax.swing.JButton();
        A8 = new javax.swing.JButton();
        F8 = new javax.swing.JButton();
        I8 = new javax.swing.JButton();
        C7 = new javax.swing.JButton();
        I7 = new javax.swing.JButton();
        E8 = new javax.swing.JButton();
        E7 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        J8 = new javax.swing.JButton();
        G7 = new javax.swing.JButton();
        J7 = new javax.swing.JButton();
        H7 = new javax.swing.JButton();
        G8 = new javax.swing.JButton();
        A7 = new javax.swing.JButton();
        H8 = new javax.swing.JButton();
        B9 = new javax.swing.JButton();
        C10 = new javax.swing.JButton();
        D9 = new javax.swing.JButton();
        F9 = new javax.swing.JButton();
        D10 = new javax.swing.JButton();
        A10 = new javax.swing.JButton();
        F10 = new javax.swing.JButton();
        I10 = new javax.swing.JButton();
        C9 = new javax.swing.JButton();
        I9 = new javax.swing.JButton();
        E10 = new javax.swing.JButton();
        E9 = new javax.swing.JButton();
        B10 = new javax.swing.JButton();
        J10 = new javax.swing.JButton();
        G9 = new javax.swing.JButton();
        J9 = new javax.swing.JButton();
        H9 = new javax.swing.JButton();
        G10 = new javax.swing.JButton();
        A9 = new javax.swing.JButton();
        H10 = new javax.swing.JButton();
        B11 = new javax.swing.JButton();
        C12 = new javax.swing.JButton();
        D11 = new javax.swing.JButton();
        F11 = new javax.swing.JButton();
        D12 = new javax.swing.JButton();
        A12 = new javax.swing.JButton();
        F12 = new javax.swing.JButton();
        I12 = new javax.swing.JButton();
        C11 = new javax.swing.JButton();
        I11 = new javax.swing.JButton();
        E12 = new javax.swing.JButton();
        E11 = new javax.swing.JButton();
        B12 = new javax.swing.JButton();
        J12 = new javax.swing.JButton();
        G11 = new javax.swing.JButton();
        J11 = new javax.swing.JButton();
        H11 = new javax.swing.JButton();
        G12 = new javax.swing.JButton();
        A11 = new javax.swing.JButton();
        H12 = new javax.swing.JButton();
        B13 = new javax.swing.JButton();
        C14 = new javax.swing.JButton();
        D13 = new javax.swing.JButton();
        F13 = new javax.swing.JButton();
        D14 = new javax.swing.JButton();
        A14 = new javax.swing.JButton();
        F14 = new javax.swing.JButton();
        I14 = new javax.swing.JButton();
        C13 = new javax.swing.JButton();
        I13 = new javax.swing.JButton();
        E14 = new javax.swing.JButton();
        E13 = new javax.swing.JButton();
        B14 = new javax.swing.JButton();
        J14 = new javax.swing.JButton();
        G13 = new javax.swing.JButton();
        J13 = new javax.swing.JButton();
        H13 = new javax.swing.JButton();
        G14 = new javax.swing.JButton();
        A13 = new javax.swing.JButton();
        H14 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        slots = new javax.swing.JTable();
        hall = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        Pay = new com.k33ptoo.components.KButton();
        counter = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();

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
                .addContainerGap(960, Short.MAX_VALUE)
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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select Seats");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Screen");

        A1.setText("A1");
        A1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A1ActionPerformed(evt);
            }
        });

        B1.setText("B1");
        B1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });

        C1.setText("C1");
        C1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ActionPerformed(evt);
            }
        });

        D1.setText("D1");
        D1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });

        E1.setText("E1");
        E1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E1ActionPerformed(evt);
            }
        });

        F1.setText("F1");
        F1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F1ActionPerformed(evt);
            }
        });

        G1.setText("G1");
        G1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G1ActionPerformed(evt);
            }
        });

        H1.setText("H1");
        H1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H1ActionPerformed(evt);
            }
        });

        I1.setText("I1");
        I1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I1ActionPerformed(evt);
            }
        });

        J1.setText("J1");
        J1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J1ActionPerformed(evt);
            }
        });

        J2.setText("J2");
        J2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J2ActionPerformed(evt);
            }
        });

        I2.setText("I2");
        I2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I2ActionPerformed(evt);
            }
        });

        H2.setText("H2");
        H2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H2ActionPerformed(evt);
            }
        });

        G2.setText("G2");
        G2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G2ActionPerformed(evt);
            }
        });

        F2.setText("F2");
        F2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F2ActionPerformed(evt);
            }
        });

        E2.setText("E2");
        E2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E2ActionPerformed(evt);
            }
        });

        D2.setText("D2");
        D2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D2ActionPerformed(evt);
            }
        });

        C2.setText("C2");
        C2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C2ActionPerformed(evt);
            }
        });

        B2.setText("B2");
        B2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });

        A2.setText("A2");
        A2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A2ActionPerformed(evt);
            }
        });

        H4.setText("H4");
        H4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H4ActionPerformed(evt);
            }
        });

        F4.setText("F4");
        F4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F4ActionPerformed(evt);
            }
        });

        E4.setText("E4");
        E4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E4ActionPerformed(evt);
            }
        });

        I4.setText("I4");
        I4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I4ActionPerformed(evt);
            }
        });

        D4.setText("D4");
        D4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D4ActionPerformed(evt);
            }
        });

        C4.setText("C4");
        C4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C4ActionPerformed(evt);
            }
        });

        A4.setText("A4");
        A4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A4ActionPerformed(evt);
            }
        });

        B4.setText("B4");
        B4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });

        J4.setText("J4");
        J4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J4ActionPerformed(evt);
            }
        });

        G4.setText("G4");
        G4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G4ActionPerformed(evt);
            }
        });

        A3.setText("A3");
        A3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A3ActionPerformed(evt);
            }
        });

        B3.setText("B3");
        B3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });

        C3.setText("C3");
        C3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C3ActionPerformed(evt);
            }
        });

        D3.setText("D3");
        D3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });

        E3.setText("E3");
        E3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E3ActionPerformed(evt);
            }
        });

        F3.setText("F3");
        F3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F3ActionPerformed(evt);
            }
        });

        G3.setText("G3");
        G3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G3ActionPerformed(evt);
            }
        });

        H3.setText("H3");
        H3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H3ActionPerformed(evt);
            }
        });

        I3.setText("I3");
        I3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I3ActionPerformed(evt);
            }
        });

        J3.setText("J3");
        J3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J3ActionPerformed(evt);
            }
        });

        B5.setText("B5");
        B5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B5ActionPerformed(evt);
            }
        });

        C6.setText("C6");
        C6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C6ActionPerformed(evt);
            }
        });

        D5.setText("D5");
        D5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D5ActionPerformed(evt);
            }
        });

        F5.setText("F5");
        F5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F5ActionPerformed(evt);
            }
        });

        D6.setText("D6");
        D6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D6ActionPerformed(evt);
            }
        });

        A6.setText("A6");
        A6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A6ActionPerformed(evt);
            }
        });

        F6.setText("F6");
        F6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F6ActionPerformed(evt);
            }
        });

        I6.setText("I6");
        I6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I6ActionPerformed(evt);
            }
        });

        C5.setText("C5");
        C5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C5ActionPerformed(evt);
            }
        });

        I5.setText("I5");
        I5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I5ActionPerformed(evt);
            }
        });

        E6.setText("E6");
        E6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E6ActionPerformed(evt);
            }
        });

        E5.setText("E5");
        E5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E5ActionPerformed(evt);
            }
        });

        B6.setText("B6");
        B6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B6ActionPerformed(evt);
            }
        });

        J6.setText("J6");
        J6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J6ActionPerformed(evt);
            }
        });

        G5.setText("G5");
        G5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G5ActionPerformed(evt);
            }
        });

        J5.setText("J5");
        J5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J5ActionPerformed(evt);
            }
        });

        H5.setText("H5");
        H5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H5ActionPerformed(evt);
            }
        });

        G6.setText("G6");
        G6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G6ActionPerformed(evt);
            }
        });

        A5.setText("A5");
        A5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A5ActionPerformed(evt);
            }
        });

        H6.setText("H6");
        H6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H6ActionPerformed(evt);
            }
        });

        B7.setText("B7");
        B7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B7ActionPerformed(evt);
            }
        });

        C8.setText("C8");
        C8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C8ActionPerformed(evt);
            }
        });

        D7.setText("D7");
        D7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D7ActionPerformed(evt);
            }
        });

        F7.setText("F7");
        F7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F7ActionPerformed(evt);
            }
        });

        D8.setText("D8");
        D8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D8ActionPerformed(evt);
            }
        });

        A8.setText("A8");
        A8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A8ActionPerformed(evt);
            }
        });

        F8.setText("F8");
        F8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F8ActionPerformed(evt);
            }
        });

        I8.setText("I8");
        I8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I8ActionPerformed(evt);
            }
        });

        C7.setText("C7");
        C7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C7ActionPerformed(evt);
            }
        });

        I7.setText("I7");
        I7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I7ActionPerformed(evt);
            }
        });

        E8.setText("E8");
        E8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E8ActionPerformed(evt);
            }
        });

        E7.setText("E7");
        E7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E7ActionPerformed(evt);
            }
        });

        B8.setText("B8");
        B8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B8ActionPerformed(evt);
            }
        });

        J8.setText("J8");
        J8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J8ActionPerformed(evt);
            }
        });

        G7.setText("G7");
        G7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G7ActionPerformed(evt);
            }
        });

        J7.setText("J7");
        J7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J7ActionPerformed(evt);
            }
        });

        H7.setText("H7");
        H7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H7ActionPerformed(evt);
            }
        });

        G8.setText("G8");
        G8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G8ActionPerformed(evt);
            }
        });

        A7.setText("A7");
        A7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A7ActionPerformed(evt);
            }
        });

        H8.setText("H8");
        H8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H8ActionPerformed(evt);
            }
        });

        B9.setText("B9");
        B9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B9ActionPerformed(evt);
            }
        });

        C10.setText("C10");
        C10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C10ActionPerformed(evt);
            }
        });

        D9.setText("D9");
        D9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D9ActionPerformed(evt);
            }
        });

        F9.setText("F9");
        F9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F9ActionPerformed(evt);
            }
        });

        D10.setText("D10");
        D10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D10ActionPerformed(evt);
            }
        });

        A10.setText("A10");
        A10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A10ActionPerformed(evt);
            }
        });

        F10.setText("F10");
        F10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F10ActionPerformed(evt);
            }
        });

        I10.setText("I10");
        I10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I10ActionPerformed(evt);
            }
        });

        C9.setText("C9");
        C9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C9ActionPerformed(evt);
            }
        });

        I9.setText("I9");
        I9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I9ActionPerformed(evt);
            }
        });

        E10.setText("E10");
        E10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E10ActionPerformed(evt);
            }
        });

        E9.setText("E9");
        E9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E9ActionPerformed(evt);
            }
        });

        B10.setText("B10");
        B10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B10ActionPerformed(evt);
            }
        });

        J10.setText("J10");
        J10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J10ActionPerformed(evt);
            }
        });

        G9.setText("G9");
        G9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G9ActionPerformed(evt);
            }
        });

        J9.setText("J9");
        J9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J9ActionPerformed(evt);
            }
        });

        H9.setText("H9");
        H9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H9ActionPerformed(evt);
            }
        });

        G10.setText("G10");
        G10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G10ActionPerformed(evt);
            }
        });

        A9.setText("A9");
        A9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A9ActionPerformed(evt);
            }
        });

        H10.setText("H10");
        H10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H10ActionPerformed(evt);
            }
        });

        B11.setText("B11");
        B11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B11ActionPerformed(evt);
            }
        });

        C12.setText("C12");
        C12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C12ActionPerformed(evt);
            }
        });

        D11.setText("D11");
        D11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D11ActionPerformed(evt);
            }
        });

        F11.setText("F11");
        F11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F11ActionPerformed(evt);
            }
        });

        D12.setText("D12");
        D12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D12ActionPerformed(evt);
            }
        });

        A12.setText("A12");
        A12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A12ActionPerformed(evt);
            }
        });

        F12.setText("F12");
        F12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F12ActionPerformed(evt);
            }
        });

        I12.setText("I12");
        I12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I12ActionPerformed(evt);
            }
        });

        C11.setText("C11");
        C11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C11ActionPerformed(evt);
            }
        });

        I11.setText("I11");
        I11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I11ActionPerformed(evt);
            }
        });

        E12.setText("E12");
        E12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E12ActionPerformed(evt);
            }
        });

        E11.setText("E11");
        E11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E11ActionPerformed(evt);
            }
        });

        B12.setText("B12");
        B12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B12ActionPerformed(evt);
            }
        });

        J12.setText("J12");
        J12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J12ActionPerformed(evt);
            }
        });

        G11.setText("G11");
        G11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G11ActionPerformed(evt);
            }
        });

        J11.setText("J11");
        J11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J11ActionPerformed(evt);
            }
        });

        H11.setText("H11");
        H11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H11ActionPerformed(evt);
            }
        });

        G12.setText("G12");
        G12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G12ActionPerformed(evt);
            }
        });

        A11.setText("A11");
        A11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A11ActionPerformed(evt);
            }
        });

        H12.setText("H12");
        H12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H12ActionPerformed(evt);
            }
        });

        B13.setText("B13");
        B13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B13ActionPerformed(evt);
            }
        });

        C14.setText("C14");
        C14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C14ActionPerformed(evt);
            }
        });

        D13.setText("D13");
        D13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D13ActionPerformed(evt);
            }
        });

        F13.setText("F13");
        F13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F13ActionPerformed(evt);
            }
        });

        D14.setText("D14");
        D14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        D14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D14ActionPerformed(evt);
            }
        });

        A14.setText("A14");
        A14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A14ActionPerformed(evt);
            }
        });

        F14.setText("F14");
        F14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        F14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F14ActionPerformed(evt);
            }
        });

        I14.setText("I14");
        I14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I14ActionPerformed(evt);
            }
        });

        C13.setText("C13");
        C13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        C13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C13ActionPerformed(evt);
            }
        });

        I13.setText("I13");
        I13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        I13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I13ActionPerformed(evt);
            }
        });

        E14.setText("E14");
        E14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E14ActionPerformed(evt);
            }
        });

        E13.setText("E13");
        E13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        E13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E13ActionPerformed(evt);
            }
        });

        B14.setText("B14");
        B14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        B14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B14ActionPerformed(evt);
            }
        });

        J14.setText("J14");
        J14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J14ActionPerformed(evt);
            }
        });

        G13.setText("G13");
        G13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G13ActionPerformed(evt);
            }
        });

        J13.setText("J13");
        J13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        J13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J13ActionPerformed(evt);
            }
        });

        H13.setText("H13");
        H13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H13ActionPerformed(evt);
            }
        });

        G14.setText("G14");
        G14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        G14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G14ActionPerformed(evt);
            }
        });

        A13.setText("A13");
        A13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        A13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A13ActionPerformed(evt);
            }
        });

        H14.setText("H14");
        H14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 160, 141)));
        H14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H14ActionPerformed(evt);
            }
        });

        jLabel1.setText("A");

        jLabel3.setText("B");

        jLabel5.setText("C");

        jLabel6.setText("D");

        jLabel7.setText("E");

        jLabel8.setText("F");

        jLabel9.setText("G");

        jLabel10.setText("H");

        jLabel11.setText("I");

        jLabel12.setText("J");

        jLabel13.setText("1");

        jLabel14.setText("2");

        jLabel16.setText("3");

        jLabel17.setText("4");

        jLabel18.setText("6");

        jLabel19.setText("5");

        jLabel20.setText("10");

        jLabel21.setText("9");

        jLabel22.setText("8");

        jLabel23.setText("7");

        jLabel24.setText("14");

        jLabel25.setText("13");

        jLabel26.setText("12");

        jLabel27.setText("11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(J2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(G2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(H2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(I2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(A13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(J13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(J14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(G14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(F14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(H14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(I14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(E14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(A14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(C14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(D14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel18)
                        .addComponent(jLabel23)
                        .addComponent(jLabel22)
                        .addComponent(jLabel21)
                        .addComponent(jLabel20)
                        .addComponent(jLabel27)
                        .addComponent(jLabel26)
                        .addComponent(jLabel25)
                        .addComponent(jLabel24)
                        .addComponent(jLabel14)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(A1)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(B1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(C1)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(D1)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(E1)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(F1)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(G1)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(H1)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(I1)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(A12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J12)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(A13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J13))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(A14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(C14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(E14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(F14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(G14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(H14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(I14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(J14)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        slots.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        slots.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                slotsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(slots);

        hall.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choosing...", "Hall 1", "Hall 2", "Hall 3", "Hall 4", "Hall 5 ", "Hall F" }));
        hall.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hallItemStateChanged(evt);
            }
        });
        hall.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                hallPropertyChange(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel28.setText("Hall:");

        Pay.setText("Next");
        Pay.setkBorderRadius(50);
        Pay.setkEndColor(new java.awt.Color(2, 149, 250));
        Pay.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        Pay.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        Pay.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        Pay.setkIndicatorThickness(10);
        Pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel30.setText("Counter");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(26, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(Pay, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(counter, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addComponent(hall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(counter, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30))
                .addGap(23, 23, 23)
                .addComponent(Pay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(527, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelClose4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClose4MouseClicked

        System.exit(0);
    }//GEN-LAST:event_jLabelClose4MouseClicked

    private void jLabelMini4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMini4MouseClicked

        this.setState(JFrame.ICONIFIED); //to minimize JFrame
    }//GEN-LAST:event_jLabelMini4MouseClicked

    private void A1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A1ActionPerformed
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A1.setBackground(Color.red);
    selectedseats[seatcount]=A1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
    }//GEN-LAST:event_A1ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B1.setBackground(Color.red);
    selectedseats[seatcount]=B1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B1ActionPerformed

    private void C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C1.setBackground(Color.red);
    selectedseats[seatcount]=C1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C1ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D1.setBackground(Color.red);
    selectedseats[seatcount]=D1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D1ActionPerformed

    private void E1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E1.setBackground(Color.red);
    selectedseats[seatcount]=E1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E1ActionPerformed

    private void F1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F1.setBackground(Color.red);
    selectedseats[seatcount]=F1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F1ActionPerformed

    private void G1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G1.setBackground(Color.red);
    selectedseats[seatcount]=G1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G1ActionPerformed

    private void H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H1.setBackground(Color.red);
    selectedseats[seatcount]=H1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H1ActionPerformed

    private void I1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I1.setBackground(Color.red);
    selectedseats[seatcount]=I1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I1ActionPerformed

    private void J1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J1ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J1.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J1.setBackground(Color.red);
    selectedseats[seatcount]=J1.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J1ActionPerformed

    private void J2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J2.setBackground(Color.red);
    selectedseats[seatcount]=J2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J2ActionPerformed

    private void I2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I2.setBackground(Color.red);
    selectedseats[seatcount]=I2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I2ActionPerformed

    private void H2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H2.setBackground(Color.red);
    selectedseats[seatcount]=H2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H2ActionPerformed

    private void G2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G2.setBackground(Color.red);
    selectedseats[seatcount]=G2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G2ActionPerformed

    private void F2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F2.setBackground(Color.red);
    selectedseats[seatcount]=F2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F2ActionPerformed

    private void E2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E2.setBackground(Color.red);
    selectedseats[seatcount]=E2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E2ActionPerformed

    private void D2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D2.setBackground(Color.red);
    selectedseats[seatcount]=D2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D2ActionPerformed

    private void C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C2.setBackground(Color.red);
    selectedseats[seatcount]=C2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C2ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B2.setBackground(Color.red);
    selectedseats[seatcount]=B2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B2ActionPerformed

    private void A2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A2ActionPerformed
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A2.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
     A2.setBackground(Color.red);
    selectedseats[seatcount]=A2.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
    }//GEN-LAST:event_A2ActionPerformed

    private void H4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H4.setBackground(Color.red);
    selectedseats[seatcount]=H4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H4ActionPerformed

    private void F4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F4.setBackground(Color.red);
    selectedseats[seatcount]=F4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F4ActionPerformed

    private void E4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E4.setBackground(Color.red);
    selectedseats[seatcount]=E4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E4ActionPerformed

    private void I4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I4.setBackground(Color.red);
    selectedseats[seatcount]=I4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I4ActionPerformed

    private void D4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D4.setBackground(Color.red);
    selectedseats[seatcount]=D4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D4ActionPerformed

    private void C4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C4.setBackground(Color.red);
    selectedseats[seatcount]=C4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C4ActionPerformed

    private void A4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A4ActionPerformed
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    selectedseats[seatcount]=A4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
    }//GEN-LAST:event_A4ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B4.setBackground(Color.red);
    selectedseats[seatcount]=B4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B4ActionPerformed

    private void J4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J4.setBackground(Color.red);
    selectedseats[seatcount]=J4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J4ActionPerformed

    private void G4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G4ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G4.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G4.setBackground(Color.red);
    selectedseats[seatcount]=G4.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G4ActionPerformed

    private void A3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A3ActionPerformed
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
     A3.setBackground(Color.red);
    selectedseats[seatcount]=A3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
    }//GEN-LAST:event_A3ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B3.setBackground(Color.red);
    selectedseats[seatcount]=B3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B3ActionPerformed

    private void C3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C3.setBackground(Color.red);
    selectedseats[seatcount]=C3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C3ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D3.setBackground(Color.red);
    selectedseats[seatcount]=D3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D3ActionPerformed

    private void E3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E3.setBackground(Color.red);
    selectedseats[seatcount]=E3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E3ActionPerformed

    private void F3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F3.setBackground(Color.red);
    selectedseats[seatcount]=F3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F3ActionPerformed

    private void G3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G3.setBackground(Color.red);
    selectedseats[seatcount]=G3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G3ActionPerformed

    private void H3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H3.setBackground(Color.red);
    selectedseats[seatcount]=H3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H3ActionPerformed

    private void I3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I3.setBackground(Color.red);
    selectedseats[seatcount]=I3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I3ActionPerformed

    private void J3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J3ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J3.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J3.setBackground(Color.red);
    selectedseats[seatcount]=J3.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J3ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B5ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B5.setBackground(Color.red);
    selectedseats[seatcount]=B5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B5ActionPerformed

    private void C6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C6ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C6.setBackground(Color.red);
    selectedseats[seatcount]=C6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C6ActionPerformed

    private void D5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D5ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D5.setBackground(Color.red);
    selectedseats[seatcount]=D5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D5ActionPerformed

    private void F5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F5ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F5.setBackground(Color.red);
    selectedseats[seatcount]=F5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F5ActionPerformed

    private void D6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D6ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D6.setBackground(Color.red);
    selectedseats[seatcount]=D6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D6ActionPerformed

    private void A6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A6ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A6.setBackground(Color.red);
    selectedseats[seatcount]=A6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
          
        
    }//GEN-LAST:event_A6ActionPerformed

    private void F6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F6.setBackground(Color.red);
    selectedseats[seatcount]=F6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F6ActionPerformed

    private void I6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I6.setBackground(Color.red);
    selectedseats[seatcount]=I6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I6ActionPerformed

    private void C5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C5.setBackground(Color.red);
    selectedseats[seatcount]=C5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C5ActionPerformed

    private void I5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I5.setBackground(Color.red);
    selectedseats[seatcount]=I5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I5ActionPerformed

    private void E6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E6.setBackground(Color.red);
    selectedseats[seatcount]=E6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E6ActionPerformed

    private void E5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E5.setBackground(Color.red);
    selectedseats[seatcount]=E5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E5ActionPerformed

    private void B6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B6.setBackground(Color.red);
    selectedseats[seatcount]=B6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B6ActionPerformed

    private void J6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J6.setBackground(Color.red);
    selectedseats[seatcount]=J6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J6ActionPerformed

    private void G5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G5.setBackground(Color.red);
    selectedseats[seatcount]=G5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G5ActionPerformed

    private void J5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J5.setBackground(Color.red);
    selectedseats[seatcount]=J5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J5ActionPerformed

    private void H5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H5ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H5.setBackground(Color.red);
    selectedseats[seatcount]=H5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H5ActionPerformed

    private void G6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G6.setBackground(Color.red);
    selectedseats[seatcount]=G6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G6ActionPerformed

    private void A5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A5ActionPerformed
        // TODO add your handling code here:
        if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A5.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    selectedseats[seatcount]=A5.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}

    }//GEN-LAST:event_A5ActionPerformed

    private void H6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H6ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H6.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H6.setBackground(Color.red);
    selectedseats[seatcount]=H6.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H6ActionPerformed

    private void B7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B7.setBackground(Color.red);
    selectedseats[seatcount]=B7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B7ActionPerformed

    private void C8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C8.setBackground(Color.red);
    selectedseats[seatcount]=C8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C8ActionPerformed

    private void D7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D7.setBackground(Color.red);
    selectedseats[seatcount]=D7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D7ActionPerformed

    private void F7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F7.setBackground(Color.red);
    selectedseats[seatcount]=F7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F7ActionPerformed

    private void D8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D8.setBackground(Color.red);
    selectedseats[seatcount]=D8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D8ActionPerformed

    private void A8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A8.setBackground(Color.red);
    selectedseats[seatcount]=A8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A8ActionPerformed

    private void F8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F8.setBackground(Color.red);
    selectedseats[seatcount]=F8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F8ActionPerformed

    private void I8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I8.setBackground(Color.red);
    selectedseats[seatcount]=I8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I8ActionPerformed

    private void C7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C7.setBackground(Color.red);
    selectedseats[seatcount]=C7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C7ActionPerformed

    private void I7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I7.setBackground(Color.red);
    selectedseats[seatcount]=I7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I7ActionPerformed

    private void E8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E8.setBackground(Color.red);
    selectedseats[seatcount]=E8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E8ActionPerformed

    private void E7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E7.setBackground(Color.red);
    selectedseats[seatcount]=E7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E7ActionPerformed

    private void B8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B8.setBackground(Color.red);
    selectedseats[seatcount]=B8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B8ActionPerformed

    private void J8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J8.setBackground(Color.red);
    selectedseats[seatcount]=J8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J8ActionPerformed

    private void G7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G7.setBackground(Color.red);
    selectedseats[seatcount]=G7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G7ActionPerformed

    private void J7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J7.setBackground(Color.red);
    selectedseats[seatcount]=J7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J7ActionPerformed

    private void H7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H7.setBackground(Color.red);
    selectedseats[seatcount]=H7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H7ActionPerformed

    private void G8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G8.setBackground(Color.red);
    selectedseats[seatcount]=G8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G8ActionPerformed

    private void A7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A7ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A7.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A7.setBackground(Color.red);
    selectedseats[seatcount]=A7.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
        // TODO add your handling code here:
       
    }//GEN-LAST:event_A7ActionPerformed

    private void H8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H8ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H8.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H8.setBackground(Color.red);
    selectedseats[seatcount]=H8.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H8ActionPerformed

    private void B9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B9.setBackground(Color.red);
    selectedseats[seatcount]=B9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B9ActionPerformed

    private void C10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C10.setBackground(Color.red);
    selectedseats[seatcount]=C10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C10ActionPerformed

    private void D9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D9.setBackground(Color.red);
    selectedseats[seatcount]=D9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D9ActionPerformed

    private void F9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F9.setBackground(Color.red);
    selectedseats[seatcount]=F9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F9ActionPerformed

    private void D10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D10.setBackground(Color.red);
    selectedseats[seatcount]=D10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D10ActionPerformed

    private void A10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A10.setBackground(Color.red);
    selectedseats[seatcount]=A10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A10ActionPerformed

    private void F10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F10.setBackground(Color.red);
    selectedseats[seatcount]=F10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F10ActionPerformed

    private void I10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I10.setBackground(Color.red);
    selectedseats[seatcount]=I10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I10ActionPerformed

    private void C9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C9.setBackground(Color.red);
    selectedseats[seatcount]=C9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C9ActionPerformed

    private void I9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I9.setBackground(Color.red);
    selectedseats[seatcount]=I9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I9ActionPerformed

    private void E10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E10.setBackground(Color.red);
    selectedseats[seatcount]=E10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E10ActionPerformed

    private void E9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E9.setBackground(Color.red);
    selectedseats[seatcount]=E9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E9ActionPerformed

    private void B10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B10.setBackground(Color.red);
    selectedseats[seatcount]=B10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B10ActionPerformed

    private void J10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J10.setBackground(Color.red);
    selectedseats[seatcount]=J10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J10ActionPerformed

    private void G9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G9.setBackground(Color.red);
    selectedseats[seatcount]=G9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G9ActionPerformed

    private void J9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J9.setBackground(Color.red);
    selectedseats[seatcount]=J9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J9ActionPerformed

    private void H9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H9.setBackground(Color.red);
    selectedseats[seatcount]=H9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H9ActionPerformed

    private void G10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G10.setBackground(Color.red);
    selectedseats[seatcount]=G10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G10ActionPerformed

    private void A9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A9ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A9.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A9.setBackground(Color.red);
    selectedseats[seatcount]=A9.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A9ActionPerformed

    private void H10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H10ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H10.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H10.setBackground(Color.red);
    selectedseats[seatcount]=H10.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H10ActionPerformed

    private void B11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B11.setBackground(Color.red);
    selectedseats[seatcount]=B11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B11ActionPerformed

    private void C12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C12.setBackground(Color.red);
    selectedseats[seatcount]=C12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C12ActionPerformed

    private void D11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D11.setBackground(Color.red);
    selectedseats[seatcount]=D11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D11ActionPerformed

    private void F11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F11.setBackground(Color.red);
    selectedseats[seatcount]=F11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F11ActionPerformed

    private void D12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D12.setBackground(Color.red);
    selectedseats[seatcount]=D12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D12ActionPerformed

    private void A12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A12.setBackground(Color.red);
    selectedseats[seatcount]=A12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A12ActionPerformed

    private void F12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F12.setBackground(Color.red);
    selectedseats[seatcount]=F12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F12ActionPerformed

    private void I12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I12.setBackground(Color.red);
    selectedseats[seatcount]=I12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I12ActionPerformed

    private void C11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C11.setBackground(Color.red);
    selectedseats[seatcount]=C11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C11ActionPerformed

    private void I11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I11.setBackground(Color.red);
    selectedseats[seatcount]=I11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I11ActionPerformed

    private void E12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E12.setBackground(Color.red);
    selectedseats[seatcount]=E12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E12ActionPerformed

    private void E11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E11.setBackground(Color.red);
    selectedseats[seatcount]=E11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E11ActionPerformed

    private void B12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B12.setBackground(Color.red);
    selectedseats[seatcount]=B12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B12ActionPerformed

    private void J12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J12.setBackground(Color.red);
    selectedseats[seatcount]=J12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J12ActionPerformed

    private void G11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G11.setBackground(Color.red);
    selectedseats[seatcount]=G11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G11ActionPerformed

    private void J11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J11.setBackground(Color.red);
    selectedseats[seatcount]=J11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J11ActionPerformed

    private void H11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H11.setBackground(Color.red);
    selectedseats[seatcount]=H11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H11ActionPerformed

    private void G12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G12.setBackground(Color.red);
    selectedseats[seatcount]=G12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G12ActionPerformed

    private void A11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A11ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A11.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A11.setBackground(Color.red);
    selectedseats[seatcount]=A11.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A11ActionPerformed

    private void H12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H12ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H12.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H12.setBackground(Color.red);
    selectedseats[seatcount]=H12.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H12ActionPerformed

    private void B13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B13.setBackground(Color.red);
    selectedseats[seatcount]=B13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B13ActionPerformed

    private void C14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C14.setBackground(Color.red);
    selectedseats[seatcount]=C14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C14ActionPerformed

    private void D13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D13.setBackground(Color.red);
    selectedseats[seatcount]=D13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D13ActionPerformed

    private void F13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F13.setBackground(Color.red);
    selectedseats[seatcount]=F13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F13ActionPerformed

    private void D14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(D14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    D14.setBackground(Color.red);
    selectedseats[seatcount]=D14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_D14ActionPerformed

    private void A14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A14.setBackground(Color.red);
    selectedseats[seatcount]=A14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A14ActionPerformed

    private void F14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(F14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    F14.setBackground(Color.red);
    selectedseats[seatcount]=F14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_F14ActionPerformed

    private void I14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I14.setBackground(Color.red);
    selectedseats[seatcount]=I14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I14ActionPerformed

    private void C13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(C13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    C13.setBackground(Color.red);
    selectedseats[seatcount]=C13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_C13ActionPerformed

    private void I13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(I13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    I13.setBackground(Color.red);
    selectedseats[seatcount]=I13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_I13ActionPerformed

    private void E14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E14.setBackground(Color.red);
    selectedseats[seatcount]=E14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E14ActionPerformed

    private void E13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(E13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    E13.setBackground(Color.red);
    selectedseats[seatcount]=E13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_E13ActionPerformed

    private void B14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(B14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    B14.setBackground(Color.red);
    selectedseats[seatcount]=B14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_B14ActionPerformed

    private void J14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J14.setBackground(Color.red);
    selectedseats[seatcount]=J14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J14ActionPerformed

    private void G13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G13.setBackground(Color.red);
    selectedseats[seatcount]=G13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G13ActionPerformed

    private void J13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(J13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    J13.setBackground(Color.red);
    selectedseats[seatcount]=J13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_J13ActionPerformed

    private void H13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H13.setBackground(Color.red);
    selectedseats[seatcount]=H13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H13ActionPerformed

    private void G14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(G14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    G14.setBackground(Color.red);
    selectedseats[seatcount]=G14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_G14ActionPerformed

    private void A13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A13ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(A13.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    A13.setBackground(Color.red);
    selectedseats[seatcount]=A13.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_A13ActionPerformed

    private void H14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H14ActionPerformed
        // TODO add your handling code here:
if(seatcount == test){
         JOptionPane.showMessageDialog(null, "You have already booked all seats");
        }
else{
if(H14.getBackground() == Color.red)
{
         JOptionPane.showMessageDialog(null, "this is seat is booked");
}
else
{
    H14.setBackground(Color.red);
    selectedseats[seatcount]=H14.getText();
}
        System.out.println(selectedseats[seatcount]);
        seatcount++;
        
}
    }//GEN-LAST:event_H14ActionPerformed

    private void hallItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hallItemStateChanged


        // TODO add your handling code here:
    }//GEN-LAST:event_hallItemStateChanged

    private void hallPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_hallPropertyChange


        // TODO add your handling code here:
    }//GEN-LAST:event_hallPropertyChange

    private void slotsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slotsMouseClicked
 TableModel model = slots.getModel();
        fetchhallid(hall.getSelectedItem().toString());
         int selectedRowIndex = slots.getSelectedRow();
        slotid = (Integer) model.getValueAt(selectedRowIndex, 0);
        initializeSeatButtons();

        // TODO add your handling code here:
    }//GEN-LAST:event_slotsMouseClicked

    private void PayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayActionPerformed
if (seatcount == test && hall.getSelectedItem().toString()!="Choosing...")
{
Ticket ti = new Ticket();
ti.setVisible(true);
dispose();
}
else
    JOptionPane.showMessageDialog(null, "Please Choose required Fields");
        //   insertBooking(paymentid);
        // TODO add your handling code here:
    }//GEN-LAST:event_PayActionPerformed

    private void hallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hallActionPerformed

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
            java.util.logging.Logger.getLogger(Seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Seats().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton A1;
    private javax.swing.JButton A10;
    private javax.swing.JButton A11;
    private javax.swing.JButton A12;
    private javax.swing.JButton A13;
    private javax.swing.JButton A14;
    private javax.swing.JButton A2;
    private javax.swing.JButton A3;
    private javax.swing.JButton A4;
    private javax.swing.JButton A5;
    private javax.swing.JButton A6;
    private javax.swing.JButton A7;
    private javax.swing.JButton A8;
    private javax.swing.JButton A9;
    private javax.swing.JButton B1;
    private javax.swing.JButton B10;
    private javax.swing.JButton B11;
    private javax.swing.JButton B12;
    private javax.swing.JButton B13;
    private javax.swing.JButton B14;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JButton B5;
    private javax.swing.JButton B6;
    private javax.swing.JButton B7;
    private javax.swing.JButton B8;
    private javax.swing.JButton B9;
    private javax.swing.JButton C1;
    private javax.swing.JButton C10;
    private javax.swing.JButton C11;
    private javax.swing.JButton C12;
    private javax.swing.JButton C13;
    private javax.swing.JButton C14;
    private javax.swing.JButton C2;
    private javax.swing.JButton C3;
    private javax.swing.JButton C4;
    private javax.swing.JButton C5;
    private javax.swing.JButton C6;
    private javax.swing.JButton C7;
    private javax.swing.JButton C8;
    private javax.swing.JButton C9;
    private javax.swing.JButton D1;
    private javax.swing.JButton D10;
    private javax.swing.JButton D11;
    private javax.swing.JButton D12;
    private javax.swing.JButton D13;
    private javax.swing.JButton D14;
    private javax.swing.JButton D2;
    private javax.swing.JButton D3;
    private javax.swing.JButton D4;
    private javax.swing.JButton D5;
    private javax.swing.JButton D6;
    private javax.swing.JButton D7;
    private javax.swing.JButton D8;
    private javax.swing.JButton D9;
    private javax.swing.JButton E1;
    private javax.swing.JButton E10;
    private javax.swing.JButton E11;
    private javax.swing.JButton E12;
    private javax.swing.JButton E13;
    private javax.swing.JButton E14;
    private javax.swing.JButton E2;
    private javax.swing.JButton E3;
    private javax.swing.JButton E4;
    private javax.swing.JButton E5;
    private javax.swing.JButton E6;
    private javax.swing.JButton E7;
    private javax.swing.JButton E8;
    private javax.swing.JButton E9;
    private javax.swing.JButton F1;
    private javax.swing.JButton F10;
    private javax.swing.JButton F11;
    private javax.swing.JButton F12;
    private javax.swing.JButton F13;
    private javax.swing.JButton F14;
    private javax.swing.JButton F2;
    private javax.swing.JButton F3;
    private javax.swing.JButton F4;
    private javax.swing.JButton F5;
    private javax.swing.JButton F6;
    private javax.swing.JButton F7;
    private javax.swing.JButton F8;
    private javax.swing.JButton F9;
    private javax.swing.JButton G1;
    private javax.swing.JButton G10;
    private javax.swing.JButton G11;
    private javax.swing.JButton G12;
    private javax.swing.JButton G13;
    private javax.swing.JButton G14;
    private javax.swing.JButton G2;
    private javax.swing.JButton G3;
    private javax.swing.JButton G4;
    private javax.swing.JButton G5;
    private javax.swing.JButton G6;
    private javax.swing.JButton G7;
    private javax.swing.JButton G8;
    private javax.swing.JButton G9;
    private javax.swing.JButton H1;
    private javax.swing.JButton H10;
    private javax.swing.JButton H11;
    private javax.swing.JButton H12;
    private javax.swing.JButton H13;
    private javax.swing.JButton H14;
    private javax.swing.JButton H2;
    private javax.swing.JButton H3;
    private javax.swing.JButton H4;
    private javax.swing.JButton H5;
    private javax.swing.JButton H6;
    private javax.swing.JButton H7;
    private javax.swing.JButton H8;
    private javax.swing.JButton H9;
    private javax.swing.JButton I1;
    private javax.swing.JButton I10;
    private javax.swing.JButton I11;
    private javax.swing.JButton I12;
    private javax.swing.JButton I13;
    private javax.swing.JButton I14;
    private javax.swing.JButton I2;
    private javax.swing.JButton I3;
    private javax.swing.JButton I4;
    private javax.swing.JButton I5;
    private javax.swing.JButton I6;
    private javax.swing.JButton I7;
    private javax.swing.JButton I8;
    private javax.swing.JButton I9;
    private javax.swing.JButton J1;
    private javax.swing.JButton J10;
    private javax.swing.JButton J11;
    private javax.swing.JButton J12;
    private javax.swing.JButton J13;
    private javax.swing.JButton J14;
    private javax.swing.JButton J2;
    private javax.swing.JButton J3;
    private javax.swing.JButton J4;
    private javax.swing.JButton J5;
    private javax.swing.JButton J6;
    private javax.swing.JButton J7;
    private javax.swing.JButton J8;
    private javax.swing.JButton J9;
    private com.k33ptoo.components.KButton Pay;
    private javax.swing.JLabel counter;
    private javax.swing.JComboBox<String> hall;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose4;
    private javax.swing.JLabel jLabelMini4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JTable slots;
    // End of variables declaration//GEN-END:variables
}

