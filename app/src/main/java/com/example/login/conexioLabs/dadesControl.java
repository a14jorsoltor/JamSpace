package com.example.login.conexioLabs;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class dadesControl {
    static Connection connection;
    private static final Obrir_Tancar_BD OTDB = new Obrir_Tancar_BD();
    static Statement sta = null;
    public static boolean semaforo;
    static final String Taula_Portfoli = "portfoli";

    public void insertar(dadesModel model) throws SQLException {
        connection = OTDB.conexioBase(connection);




        String sentenciaSql ="INSERT INTO " +Taula_Portfoli +" (" +"descripcio" + "," + "nomFoto" + "," + "userMail"  +"," +"username" +
                "VALUES(" + model.getDescripcio()+"," + model.getNomFoto() + "," +  model.getUserMail() + "," + model.getUsername() +"); ";

        try {
            sta = connection.createStatement();
            sta.executeUpdate(sentenciaSql);
            semaforo = true;
        } catch (SQLException e) {
            System.out.println("Error");
            semaforo = false;
        } finally {
            if(sta!=null)
                sta.close();
            connection = OTDB.cerrarbase(connection);
        }


        connection = OTDB.cerrarbase(connection);

    }

    public void dadesControlUp(dadesModel model, String userMailName) throws SQLException{

        connection = OTDB.conexioBase(connection);



        String sentenciaSql = "UPDATE " +  Taula_Portfoli +
                "SET descripcio =" + model.getDescripcio() + "," +  " nomFoto ="  + model.getNomFoto() + "," +  " userMail =" +  model.getUserMail() + "," + " username =" + model.getUsername() +
                " WHERE userMail =" +  userMailName + ";";

        try {
            sta = connection.createStatement();
            sta.executeUpdate(sentenciaSql);
            semaforo = true;
        } catch (SQLException e) {
            System.out.println("Error");
            semaforo = false;
        } finally {
            if(sta!=null)
                sta.close();
            connection = OTDB.cerrarbase(connection);
        }


        connection = OTDB.cerrarbase(connection);
    }
}
