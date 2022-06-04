package com.example.login.conexioLabs;
import android.provider.ContactsContract;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class dadesControl {

    private conexiolabs conectar;
    //private dadesModel modelo; // dades taula
    private Connection con;

    public dadesControl(conexiolabs conectar, Connection con) {
        conectar = new conexiolabs();

        this.conectar = conectar;
        this.con = con;
    }

    public void insertar(dadesModel model) throws SQLException {
        conectar.getConexion();
        PreparedStatement ps;
        String sql;

        model.getId();
        model.getDescripcio();
        model.getNomFoto();
        model.getUserMail();
        model.getUsername();

    }

    public void dadesControlUp(dadesModel model) throws SQLException{
        conectar.getConexion();
        PreparedStatement ps;
        String sql;

        model.getId();
        model.getDescripcio();
        model.getNomFoto();
        model.getUserMail();
        model.getUsername();
    }
}
