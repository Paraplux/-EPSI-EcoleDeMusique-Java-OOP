package com.marcbouchez.models;

import com.marcbouchez.utils.Countable;

public class Jour implements Countable {

    private int id;
    private String libelle;
    private int nbWeek;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
