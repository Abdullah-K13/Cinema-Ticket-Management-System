/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cinematicketingsystem;

import static com.mycompany.cinematicketingsystem.nameandpass.logid;
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
public class Screening extends javax.swing.JFrame {
    public static int movieId;
   public static  int slotid;
    public static int hallid;
    String slotna;
     PreparedStatement pstmt=null;
    Statement stmt=null;
    ResultSet res=null;
    
byte[] image;
String imagepath = "";
ImageIcon myimage;
    String filepath;
    byte[] imageData;
  private static final String DB_URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=StudentInformation;encrypt=true;trustServerCertificate=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "13245";
    /**
     * Creates new form addemp
     */
    public Screening() {
        initComponents();
        
        
         DefaultTableModel tblmodel = (DefaultTableModel) Table.getModel();
         DefaultTableModel tbmodel = (DefaultTableModel) slots.getModel();

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
                    hallid = loginId;
                } else {
                    System.out.println("No LoginId found for username " + hallname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertData(String column1Value, String column2Value, int column3Value, String column4Value,byte[] column5Value) {
        try (
               java.sql.Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=StudentInformation;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
            // SQL query to insert data into the table
            String sql = "INSERT INTO movie (title, genre, duration, showingdate, imageData) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                // Set values for each column
                pstmt.setString(1, column1Value);
                pstmt.setString(2, column2Value);
                pstmt.setInt(3, column3Value);
                pstmt.setString(4, column4Value);
                pstmt.setBytes(5, column5Value);
                // Execute the query
                pstmt.executeUpdate();
                System.out.println("Data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertCinema() {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // Convert java.util.Date to java.sql.Date
        
        // Call the stored procedure
        String storedProcedureCall = "{call InsertCinemaMovie(?, ?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each column
            callableStatement.setInt(1, movieId);
            callableStatement.setInt(2, slotid);
            callableStatement.setInt(3, hallid);
       
            callableStatement.executeUpdate();
            System.out.println("Data inserted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void deleteData(String title) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=StudentInformation;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // SQL query to delete a record from the table
        String sql = "DELETE FROM movie WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the value for the parameter (in this case, movieId)
            pstmt.setString(1, title);

            // Execute the query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("No record found with the given ID.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void updateData(int movieId,  String status) {
    try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CinmeaTicketingSystem;encrypt=true;trustServerCertificate=true", "admin", "12345")) {
        // Call the stored procedure
        String storedProcedureCall = "{call UpdateMovieStatus(?, ?)}";
        try (CallableStatement callableStatement = connection.prepareCall(storedProcedureCall)) {
            // Set values for each parameter
            callableStatement.setInt(1, movieId);
            callableStatement.setString(2, status);
     

            // Execute the stored procedure
            callableStatement.executeUpdate();
            System.out.println("Data updated successfully!");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        slots = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        iglable = new javax.swing.JLabel();
        update2 = new com.k33ptoo.components.KButton();
        view2 = new com.k33ptoo.components.KButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        hall = new javax.swing.JComboBox<>();
        slotname = new javax.swing.JLabel();
        movie = new javax.swing.JLabel();
        halsss = new javax.swing.JLabel();
        slot = new javax.swing.JLabel();

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table1);

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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 124, 190));
        jLabel10.setText("Available Movies");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 124, 190));
        jLabel11.setText("Available Slots");

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
        jScrollPane3.setViewportView(slots);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel11)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        Title.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel13.setText("Current:");

        iglable.setBackground(new java.awt.Color(255, 255, 255));
        iglable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choosing", "Showing", "Not Showing" }));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Slot Name:");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Hall:");

        hall.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choosing...", "Hall 1", "Hall 2", "Hall 3", "Hall 4", "Hall 5 ", "Hall F" }));
        hall.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hallItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGap(73, 73, 73)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addGap(18, 18, 18)
                                            .addComponent(slotname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel13)
                                                    .addGap(36, 36, 36))
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(21, 21, 21)))
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(hall, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jComboBox1, 0, 115, Short.MAX_VALUE))))
                                    .addGap(110, 110, 110)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(movie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(halsss, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(view2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(update2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(340, Short.MAX_VALUE)
                    .addComponent(slot, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(19, 19, 19)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(iglable, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Title)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(movie, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(hall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(slotname, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(halsss, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(update2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(view2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(411, Short.MAX_VALUE)
                    .addComponent(slot, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(177, 177, 177)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void update2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update2ActionPerformed

        String current = jComboBox1.getSelectedItem().toString();
        updateData(movieId,current);
movie.setText(Integer.toString(movieId));
slot.setText(Integer.toString(slotid));
halsss.setText(Integer.toString(hallid));
        insertCinema();
        
        
    }//GEN-LAST:event_update2ActionPerformed

    private void view2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view2ActionPerformed
        admin_login ad = new admin_login();
        ad.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_view2ActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
    int i = Table.getSelectedRow();
        TableModel model = Table.getModel();
        String title;
        movieId = Integer.parseInt(model.getValueAt(i, 0).toString());
        title= model.getValueAt(i, 1).toString();
       
        Title.setText(title);
 
         byte[] image = retrieveImageFromDatabase(title);
         iglable.setIcon(seticon(null, image));
           

        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
//        // TODO add your handling code here:
//         TableModel model = slots.getModel();
//         int selectedRowIndex = slots.getSelectedRow();
//       //  slotna=model.getValueAt(selectedRowIndex, 1).toString();
//         slotname.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }//GEN-LAST:event_Table1MouseClicked

    private void slotsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slotsMouseClicked
 TableModel model = slots.getModel();
         int selectedRowIndex = slots.getSelectedRow();
       //  slotna=model.getValueAt(selectedRowIndex, 1).toString();
         slotname.setText(model.getValueAt(selectedRowIndex, 1).toString());
  slotid = (Integer) model.getValueAt(selectedRowIndex, 0);


        // TODO add your handling code here:
    }//GEN-LAST:event_slotsMouseClicked

    private void hallItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hallItemStateChanged
        fetchhallid(hall.getSelectedItem().toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_hallItemStateChanged

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
            java.util.logging.Logger.getLogger(Screening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Screening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Screening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Screening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Screening().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JTable Table1;
    private javax.swing.JLabel Title;
    private javax.swing.JComboBox<String> hall;
    private javax.swing.JLabel halsss;
    private javax.swing.JLabel iglable;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel movie;
    private javax.swing.JLabel slot;
    private javax.swing.JLabel slotname;
    private javax.swing.JTable slots;
    private com.k33ptoo.components.KButton update2;
    private com.k33ptoo.components.KButton view2;
    // End of variables declaration//GEN-END:variables
}
