package com.example.login.conexioLabs;

public class dadesModel {
    public dadesModel(String nomFoto, String descripcio, String userMail, String username, int id) {
        this.nomFoto = nomFoto;
        this.descripcio = descripcio;
        this.userMail = userMail;
        this.username = username;
        this.id = id;
    }

    String nomFoto, descripcio, userMail, username;
    int id;

    public String getNomFoto() {
        return nomFoto;
    }

    public void setNomFoto(String nomFoto) {
        this.nomFoto = nomFoto;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void username(String username) {
    }

    public void userMail(String userMail) {
    }
}
