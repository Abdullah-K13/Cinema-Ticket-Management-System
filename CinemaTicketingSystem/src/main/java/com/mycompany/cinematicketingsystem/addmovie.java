/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cinematicketingsystem;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author hp
 */
public class addmovie extends javax.swing.JFrame {
    int movieId;
     PreparedStatement pstmt=null;
    Statement stmt=null;
    ResultSet res=null;
     String stats;
    String MovieType;
    String movieTitle;
    
byte[] image;

ImageIcon myimage;
    String filepath;
    byte[] imageData;
  private static final String DB_URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=StudentInformation;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "13245";
    /**
     * Creates new form addemp
     */
    public addmovie() {
        initComponents();
     loadDataFromDatabase();
    }
    
      private void refreshTable() {
        loadDataFromDatabase();
    }
        private void loadDataFromDatabase() {
        DefaultTableModel tblmodel = (DefaultTableModel) Table.getModel();
   tblmodel.setRowCount(0);
        tblmodel.setColumnCount(0);
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
public static void insertData(String title, String genre, String status, Date releaseDate, int duration, String show, String movieType, byte[] imageData) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlReleaseDate = new java.sql.Date(releaseDate.getTime());

        // Call the stored procedure
        String storedProcedureCall = "{call InsertMovie(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each column
            callableStatement.setString(1, title);
            callableStatement.setString(2, genre);
            callableStatement.setString(3, status);
            callableStatement.setDate(4, sqlReleaseDate); // Use java.sql.Date
            callableStatement.setInt(5, duration);
            callableStatement.setString(6, show);
            callableStatement.setString(7, movieType);
            callableStatement.setBytes(8, imageData);
            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   public static void deleteData(int movieId) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // Call the stored procedure
        String storedProcedureCall = "{call DeleteMovie(?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set the value for the parameter
            callableStatement.setInt(1, movieId);

            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data deleted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   public static void updateData(int movieId, String title, String genre, String status, String releaseDate, int duration, String show, String movieType, byte[] imageData) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // Convert String date to java.sql.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = dateFormat.parse(releaseDate);
        java.sql.Date sqlReleaseDate = new java.sql.Date(utilDate.getTime());

        // Call the stored procedure
        String storedProcedureCall = "{call UpdateMovie(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each parameter
            callableStatement.setInt(1, movieId);
            callableStatement.setString(2, title);
            callableStatement.setString(3, genre);
            callableStatement.setString(4, status);
            callableStatement.setDate(5, sqlReleaseDate); // Use java.sql.Date
            callableStatement.setInt(6, duration);
            callableStatement.setString(7, show);
            callableStatement.setString(8, movieType);
            callableStatement.setBytes(9, imageData);

            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data updated successfully!");
        }
    } catch (SQLException | ParseException e) {
        e.printStackTrace();
    }
}

    
public byte[] retrieveImageFromDatabase(String imageId) {
        byte[] imageData = null;

        try (
              java.sql.Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            // SQL query to retrieve image data from the database
            String sql = "SELECT imageData FROM movie WHERE moviename = ?";
            
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        genre = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        duration1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        showingdate = new com.toedter.calendar.JDateChooser();
        iglable = new javax.swing.JLabel();
        imgupload = new com.k33ptoo.components.KButton();
        insert = new com.k33ptoo.components.KButton();
        update2 = new com.k33ptoo.components.KButton();
        delete2 = new com.k33ptoo.components.KButton();
        view2 = new com.k33ptoo.components.KButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setText("Movie Title");

        title.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Genre");

        genre.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel12.setText("Duration");

        duration1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel13.setText("Release Date");

        iglable.setBackground(new java.awt.Color(255, 255, 255));
        iglable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        imgupload.setText("Image");
        imgupload.setkBorderRadius(50);
        imgupload.setkEndColor(new java.awt.Color(2, 149, 250));
        imgupload.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        imgupload.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        imgupload.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        imgupload.setkIndicatorThickness(10);
        imgupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imguploadActionPerformed(evt);
            }
        });

        insert.setText("Insert");
        insert.setkBorderRadius(50);
        insert.setkEndColor(new java.awt.Color(2, 149, 250));
        insert.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        insert.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        insert.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        insert.setkIndicatorThickness(10);
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });

        update2.setText("Update");
        update2.setkBorderRadius(50);
        update2.setkEndColor(new java.awt.Color(2, 149, 250));
        update2.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        update2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        update2.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        update2.setkIndicatorThickness(10);
        update2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update2ActionPerformed(evt);
            }
        });

        delete2.setText("Delete");
        delete2.setkBorderRadius(50);
        delete2.setkEndColor(new java.awt.Color(2, 149, 250));
        delete2.setkHoverEndColor(new java.awt.Color(234, 93, 1));
        delete2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        delete2.setkHoverStartColor(new java.awt.Color(234, 93, 1));
        delete2.setkIndicatorThickness(10);
        delete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete2ActionPerformed(evt);
            }
        });

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
        jLabel9.setText("Add Movies");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Movie Type");

        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose...", "2D", "3D", "Both" }));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Status");

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose...", "Upcoming", "Ongoing" }));

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
                        .addGap(140, 140, 140)
                        .addComponent(imgupload, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 191, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 13, Short.MAX_VALUE)
                                        .addComponent(insert, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(update2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(delete2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(duration1)
                                        .addComponent(genre)
                                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(showingdate, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 49, Short.MAX_VALUE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel9))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imgupload, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duration1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(showingdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insert, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(view2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 99, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imguploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imguploadActionPerformed
        // TODO add your handling code here:
        

        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All pic", "png","jpe","jpeg","gif");
        file.addChoosableFileFilter(filter);

        int a = file.showSaveDialog(null);

        if(a==JFileChooser.APPROVE_OPTION){
            File f =file.getSelectedFile();
            String p =f.getAbsolutePath();
            iglable.setIcon(seticon(p, null));
            
            try{
               
                File image = new File(p);
    FileInputStream fis = new FileInputStream(image);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];

    int readNum;
    while ((readNum = fis.read(buf)) != -1) {
        bos.write(buf, 0, readNum);
        
         imageData = bos.toByteArray();
    }
            
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            // filepath= file.getSelectedFile().getAbsolutePath();
        }
    }//GEN-LAST:event_imguploadActionPerformed

    private void update2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update2ActionPerformed
       int duration = Integer.parseInt(duration1.getText());
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date selectedDate = showingdate.getDate();
String formattedDate = dateFormat.format(selectedDate);
        // byte[] image = retrieveImageFromDatabase(movieTitle);

       updateData(movieId, title.getText(), genre.getText(),this.status.getSelectedItem().toString(),formattedDate, duration, null,type.getSelectedItem().toString() ,imageData);
        refreshTable();
       
    }//GEN-LAST:event_update2ActionPerformed

    private void delete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete2ActionPerformed

        deleteData(movieId);
         refreshTable();
        // TODO add your handling code here:
    }//GEN-LAST:event_delete2ActionPerformed

    private void view2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view2ActionPerformed
        admin_login ad = new admin_login();
        ad.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_view2ActionPerformed

    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed
       String status = null;
       int duration = Integer.parseInt(duration1.getText());
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date selectedDate = showingdate.getDate();
String formattedDate = dateFormat.format(selectedDate);
//test1.setText(formattedDate);
        insertData(title.getText(), genre.getText(),this.status.getSelectedItem().toString(),selectedDate, duration, null,type.getSelectedItem().toString() ,imageData);
    refreshTable();
// TODO add your handling code here:
    }//GEN-LAST:event_insertActionPerformed

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
         stats = model.getValueAt(i, 3).toString();
        duration1.setText(model.getValueAt(i, 5).toString());
    //    showingdate.setDateFormatString(model.getValueAt(i, 4).toString());
java.sql.Date sqlDate = (java.sql.Date) model.getValueAt(i, 4);          //String dateString = "13/13/2023";
showingdate.setDate(sqlDate);

//           try {
//               Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
//                showingdate.setDate(date);
//           } catch (ParseException ex) {
//               Logger.getLogger(UpdateComplainStatus.class.getName()).log(Level.SEVERE, null, ex);
//           }
            
           MovieType = model.getValueAt(i, 7).toString();
           
           
         byte[] image = retrieveImageFromDatabase(title);
         iglable.setIcon(seticon(null, image));
           
         String test="2D";
     //test1.setText(test);
           status.setSelectedItem(stats);
     type.setSelectedItem(MovieType);

        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

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
            java.util.logging.Logger.getLogger(addmovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addmovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addmovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addmovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addmovie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private com.k33ptoo.components.KButton delete2;
    private javax.swing.JTextField duration1;
    private javax.swing.JTextField genre;
    private javax.swing.JLabel iglable;
    private com.k33ptoo.components.KButton imgupload;
    private com.k33ptoo.components.KButton insert;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser showingdate;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTextField title;
    private javax.swing.JComboBox<String> type;
    private com.k33ptoo.components.KButton update2;
    private com.k33ptoo.components.KButton view2;
    // End of variables declaration//GEN-END:variables
}
