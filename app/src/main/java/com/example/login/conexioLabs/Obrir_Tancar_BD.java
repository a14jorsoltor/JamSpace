package com.example.login.conexioLabs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Obrir_Tancar_BD {



    public static final String URL = "jdbc:mysq l://localhost:3306/pruebajava";
    public static final String USER = "root";
    public static final String CLAVE = "";


    public Connection conexioBase(Connection connection) {

        try {

            connection = DriverManager.getConnection(URL, USER, CLAVE);
            System.out.println("Connecio realitzada usant" + "DriverManager");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection cerrarbase(Connection connection ) {
        try {
            System.out.println("hola");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
