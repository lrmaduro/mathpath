/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coil.prototipo;
import java.sql.*;
/**
 *
 * @author Luis
 */
public class dbConnections {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/usuarios";
        String user = "root";
        String password = "PCOIL312*.";

        return DriverManager.getConnection(url, user, password);
    }
}
