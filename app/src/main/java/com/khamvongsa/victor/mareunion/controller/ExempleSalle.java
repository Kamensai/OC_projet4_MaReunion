package com.khamvongsa.victor.mareunion.controller;


public class ExempleSalle {

    private long id;

    private String nom;

    private int couleur;

    public ExempleSalle (long id, String nom, int couleur) {

        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}
