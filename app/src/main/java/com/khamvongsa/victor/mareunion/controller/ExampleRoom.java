package com.khamvongsa.victor.mareunion.controller;


import android.os.Parcel;
import android.os.Parcelable;

public class ExampleRoom {

    private long id;

    private String nom;

    private int couleur;

    public ExampleRoom(long id, String nom, int couleur) {

        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
    }
/*
    protected ExampleRoom(Parcel in) {
        id = in.readLong();
        nom = in.readString();
        couleur = in.readInt();
    }

    public static final Creator<ExampleRoom> CREATOR = new Creator<ExampleRoom>() {
        @Override
        public ExampleRoom createFromParcel(Parcel in) {
            return new ExampleRoom(in);
        }

        @Override
        public ExampleRoom[] newArray(int size) {
            return new ExampleRoom[size];
        }
    };

 */

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
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nom);
        dest.writeInt(couleur);
    }

 */
}
