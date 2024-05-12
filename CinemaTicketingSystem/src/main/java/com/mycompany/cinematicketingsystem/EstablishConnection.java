
package com.mycompany.cinematicketingsystem;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */

public class EstablishConnection {

    public static void main(String[] args){
    

        
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=StudentInformation;encrypt=true;trustServerCertificate=true";
        String user = "admin";
        String password = "12345";

        // Replace this with your SQL query
        String sqlQuery = "SELECT Status FROM Seats WHERE  row = 'A' and seatNumber= 9";

        try {

            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
           int columnCount = resultSet.getMetaData().getColumnCount();
        List<String[]> rows = new ArrayList<>();
            while (resultSet.next()) {
                String[] rowValues = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowValues[i - 1] = resultSet.getString(i);
                }
                rows.add(rowValues);
            }
//
//            // Print all values from each row
//            for (String[] row : rows) {
//                for (String value : row) {
//                    System.out.print(value + "   ");
//                }
//                System.out.println(); // Move to the next line for the next row
//            }
            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
