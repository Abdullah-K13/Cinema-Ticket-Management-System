/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cinematicketingsystem;

import java.awt.Image;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author hp
 */

public class PurchaseTicket extends javax.swing.JFrame {
    public static int movieId;
    public static int paymentid;

    public static int quan;
byte[] image;
 String stats;
   public static String MovieType;
  public static  String movieTitle;
   public static  String genre1;
    public static  String duration;
ImageIcon myimage;
    String filepath;
    byte[] imageData;
    int sum;
 PurchaseTicket() {
        initComponents();
                setLocationRelativeTo(null);
                
      DefaultTableModel tblmodel = (DefaultTableModel) Table.getModel();

try {
    Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345");

    // Call the stored procedure
    String storedProcedureCall = "{call GetMovies}";
    try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
        ResultSet res = callableStatement.executeQuery();
        ResultSetMetaData metaData = res.getMetaData();

        // Remove existing columns from the table model
        tblmodel.setColumnCount(0);

        // Add columns to the table model
        int columns = metaData.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            tblmodel.addColumn(metaData.getColumnName(i));
        }

        // Add rows to the table model
        while (res.next()) {
            Object[] row = new Object[columns];
            for (int i = 1; i <= columns; i++) {
                row[i - 1] = res.getObject(i);
            }
            tblmodel.addRow(row);
        }
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}

    }
    

       
        
        public static void insertBooking() {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
   
        // Call the stored procedure
        String storedProcedureCall = "{call InsertBookingInfo()}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each column
                        //  callableStatement.setInt(1, paymentid);

           // Use java.sql.Date
           
            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        }
    public static void insertTicket(  String row, int Seatid) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
   
        // Call the stored procedure
        String storedProcedureCall = "{call InsertClientTicket(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each column
           // Use java.sql.Date
            callableStatement.setInt(1,Interface2.clid);
             callableStatement.setInt(2, PurchaseTicket.movieId);
                callableStatement.setInt(3, Seats.slotid);
                callableStatement.setString(4, row);
                 callableStatement.setInt(5, Seatid);
                 callableStatement.setInt(6, Seats.hallid);
                 
            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
        public static void InsertPayment(int Amount,String Ptype, int clientid) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
   
        // Call the stored procedure
        String storedProcedureCall = "{call InsertPayment(?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each column
           // Use java.sql.Date
            callableStatement.setInt(1, Amount);
             callableStatement.setString(2, Ptype);
              callableStatement.setInt(3, clientid);
             
            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public byte[] retrieveImageFromDatabase(String imageId) {
        byte[] imageData = null;

        try (
              java.sql.Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            // SQL query to retrieve image data from the database
            String sql = "SELECT imageData FROM movie WHERE movieName = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, imageId);

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve the image data as a binary stream
                        java.io.InputStream inputStream = resultSet.getBinaryStream("imageData");

                        // Convert the binary stream to a byte array
                        if (inputStream != null) {
                            imageData = inputStream.readAllBytes();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageData;
    }
public ImageIcon seticon(String m, byte[] image){
    if(m != null){
        myimage = new ImageIcon(m);
    }
    else
    {
        myimage = new ImageIcon(image);
    }
    Image img1 = myimage.getImage();
    Image img2 = img1.getScaledInstance(iglable.getWidth(),iglable.getHeight(),Image.SCALE_SMOOTH);
ImageIcon i = new ImageIcon(img2);
return i;
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
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        view2 = new com.k33ptoo.components.KButton();
        jLabel9 = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        genre = new javax.swing.JLabel();
        duration1 = new javax.swing.JLabel();
        type = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        iglable = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        quantity = new javax.swing.JSpinner();
        price = new javax.swing.JLabel();
        calculate = new com.k33ptoo.components.KButton();
        jLabel16 = new javax.swing.JLabel();
        ptype = new javax.swing.JComboBox<>();
        calculate1 = new com.k33ptoo.components.KButton();
        jLabel18 = new javax.swing.JLabel();
        Pay = new com.k33ptoo.components.KButton();
        pprice = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

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
                .addContainerGap(643, Short.MAX_VALUE)
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

        kGradientPanel2.setkEndColor(new java.awt.Color(68, 160, 141));
        kGradientPanel2.setkGradientFocus(160);
        kGradientPanel2.setkStartColor(new java.awt.Color(9, 54, 55));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setText("Movie Title");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Genre");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel12.setText("Duration");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel13.setText("Showing Date");

        view2.setText("Return");
        view2.setkBorderRadius(50);
        view2.setkEndColor(new java.awt.Color(2, 149, 250));
        view2.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        view2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        view2.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        view2.setkIndicatorThickness(10);
        view2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 124, 190));
        jLabel9.setText("Purchase Ticket");

        title.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        genre.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        duration1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        type.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel17.setText("Movie Type");

        date.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(view2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addGap(0, 35, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(duration1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(168, 168, 168))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel17))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel9)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(genre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(duration1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(381, 381, 381)
                        .addComponent(view2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        iglable.setBackground(new java.awt.Color(255, 255, 255));
        iglable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Price");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Qunatity");

        quantity.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        quantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                quantityStateChanged(evt);
            }
        });
        quantity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quantityMouseClicked(evt);
            }
        });

        calculate.setText("Pay");
        calculate.setkBorderRadius(50);
        calculate.setkEndColor(new java.awt.Color(2, 149, 250));
        calculate.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        calculate.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        calculate.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        calculate.setkIndicatorThickness(10);
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("Payment Type:");

        ptype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choosing...", "Cash ", "Credit Card", "PayPal", " " }));

        calculate1.setText("Select Seats");
        calculate1.setkBorderRadius(50);
        calculate1.setkEndColor(new java.awt.Color(2, 149, 250));
        calculate1.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        calculate1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        calculate1.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        calculate1.setkIndicatorThickness(10);
        calculate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculate1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        Pay.setText("Pay");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(770, 770, 770)
                        .addComponent(calculate, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(pprice, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ptype, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(Pay, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(235, 235, 235)
                        .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(calculate1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(pprice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(ptype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addComponent(calculate1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(calculate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelClose4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClose4MouseClicked

        System.exit(0);
    }//GEN-LAST:event_jLabelClose4MouseClicked

    private void jLabelMini4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMini4MouseClicked

        this.setState(JFrame.ICONIFIED); //to minimize JFrame
    }//GEN-LAST:event_jLabelMini4MouseClicked

    private void view2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view2ActionPerformed

    }//GEN-LAST:event_view2ActionPerformed

    private void quantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_quantityStateChanged
        if (MovieType.equals("2D")) {
            int sum = Integer.parseInt(quantity.getValue().toString()) * 800;
            pprice.setText(String.valueOf(sum));
                        quan= Integer.parseInt(quantity.getValue().toString());

        }
        else
            if (MovieType.equals("3D")) {
            int sum = Integer.parseInt(quantity.getValue().toString()) * 1000;
            pprice.setText(String.valueOf(sum));
            quan= Integer.parseInt(quantity.getValue().toString());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityStateChanged

    private void quantityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quantityMouseClicked
        if (MovieType.equals("2D")) {
            int sum = Integer.parseInt(quantity.getValue().toString()) * 800;
            pprice.setText(String.valueOf(sum));
                        quan= Integer.parseInt(quantity.getValue().toString());

        }
        else 
if (MovieType.equals("3D")) {
            int sum = Integer.parseInt(quantity.getValue().toString()) * 1000;
            pprice.setText(String.valueOf(sum));
                        quan= Integer.parseInt(quantity.getValue().toString());

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityMouseClicked

    private void calculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateActionPerformed
      //  insertPayment(Integer.parseInt(price.getText()), ptype.getSelectedItem().toString(), clientid);
      //  insertBooking();
        // TODO add your handling code here:
    }//GEN-LAST:event_calculateActionPerformed

    private void calculate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculate1ActionPerformed
if(quantity.getValue().toString() == "0" || ptype.getSelectedItem().toString() == "Choosing...")
{
    JOptionPane.showMessageDialog(null, "please choose every field");
}
else
{
        InsertPayment(Integer.parseInt(pprice.getText()), ptype.getSelectedItem().toString(), Interface2.clid);
        insertBooking();
        
        Seats seat = new Seats();
seat.setVisible(true);
dispose();
}
        // TODO add your handling code here:
    }//GEN-LAST:event_calculate1ActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        int i = Table.getSelectedRow();
        TableModel model = Table.getModel();
        String title;
        movieId = Integer.parseInt(model.getValueAt(i, 0).toString());
        title= model.getValueAt(i, 1).toString();
        movieTitle= model.getValueAt(i, 1).toString();

        this.title.setText(title);
        //test1.setText(String.valueOf(movieId));
        genre.setText(model.getValueAt(i, 2).toString());
        genre1 = model.getValueAt(i, 2).toString();
        stats = model.getValueAt(i, 3).toString();
        duration1.setText(model.getValueAt(i, 5).toString());
        //    showingdate.setDateFormatString(model.getValueAt(i, 4).toString());
        MovieType = model.getValueAt(i, 6).toString();
        java.sql.Date sqlDate = (java.sql.Date) model.getValueAt(i, 4);          //String dateString = "13/13/2023";
        date.setText(sqlDate.toString());
        type.setText(MovieType);

        //           try {
            //               Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            //                showingdate.setDate(date);
            //           } catch (ParseException ex) {
            //               Logger.getLogger(UpdateComplainStatus.class.getName()).log(Level.SEVERE, null, ex);
            //           }

       // MovieType = model.getValueAt(i, 6).toString();

        byte[] image = retrieveImageFromDatabase(title);
        iglable.setIcon(seticon(null, image));

        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    private void PayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayActionPerformed
        InsertPayment(Integer.parseInt(pprice.getText()), ptype.getSelectedItem().toString(), Interface2.clid);
        insertBooking();
    //    insertTicket(paymentid, movieId, movieId, paymentid, WIDTH, stats, WIDTH);
        // TODO add your handling code here:
    }//GEN-LAST:event_PayActionPerformed

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
            java.util.logging.Logger.getLogger(PurchaseTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseTicket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton Pay;
    private javax.swing.JTable Table;
    private com.k33ptoo.components.KButton calculate;
    private com.k33ptoo.components.KButton calculate1;
    private javax.swing.JLabel date;
    private javax.swing.JLabel duration1;
    private javax.swing.JLabel genre;
    private javax.swing.JLabel iglable;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelClose4;
    private javax.swing.JLabel jLabelMini4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel pprice;
    private javax.swing.JLabel price;
    private javax.swing.JComboBox<String> ptype;
    private javax.swing.JSpinner quantity;
    private javax.swing.JLabel title;
    private javax.swing.JLabel type;
    private com.k33ptoo.components.KButton view2;
    // End of variables declaration//GEN-END:variables
}
