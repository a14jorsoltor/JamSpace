package com.example.login;

public class Post {

    public Post(String decJoc, String nomFoto, String nomUser, int id, String nomJoc) {
        DecJoc = decJoc;
        NomFoto = nomFoto;
        NomUser = nomUser;
        this.id = id;
        this.nomJoc = nomJoc;
    }

    String DecJoc;
    String NomFoto;
    String NomUser;
    int id;
    String nomJoc;

    public String getDecJoc() {
        return DecJoc;
    }

    public void setDecJoc(String decJoc) {
        DecJoc = decJoc;
    }

    public String getNomFoto() {
        return NomFoto;
    }

    public void setNomFoto(String nomFoto) {
        NomFoto = nomFoto;
    }

    public String getNomUser() {
        return NomUser;
    }

    public void setNomUser(String nomUser) {
        NomUser = nomUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomJoc() {
        return nomJoc;
    }

    public void setNomJoc(String nomJoc) {
        this.nomJoc = nomJoc;
    }
}
