/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JHONATAN GRUESO PEREA
 * @since 17/07/2022
 * @version 1.0
 */
public class ConexionBD {
          
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String URL = "jdbc:mysql://localhost:8080/bd_gestion_empresa"; 
    private static Connection cnx = null;

    public static Connection obtener() {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                cnx = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("conectados exitosamente.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return cnx;
    }
    
    public static void cerrar() {
        try {
            if (cnx != null) cnx.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
