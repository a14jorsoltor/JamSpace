package com.example.login.conexioLabs;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexiolabs {

    public static final String URL = "jdbc:mysq l://localhost:3306/pruebajava";
    public static final String USER = "root";
    public static final String CLAVE = "";


    public Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysq l.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }
}




