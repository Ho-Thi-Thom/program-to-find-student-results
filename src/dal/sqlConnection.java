/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author MYPC
 */
public class sqlConnection {

    protected Connection conn = null;
    protected Statement st = null;
    protected ResultSet rs = null;

    protected Connection getConnect() {
        if (conn == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=inforStudentSGU;"
                        + "username=sa;password=123");
                System.out.println("Thành công");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Lỗi không có gói kết nối:");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối sql :");
            }
        }
        return conn;
    }

    public static void main(String[] arvg) {
//        new sqlConnection();
        sqlConnection sql = new sqlConnection();
        sql.getConnect();
    }
}
