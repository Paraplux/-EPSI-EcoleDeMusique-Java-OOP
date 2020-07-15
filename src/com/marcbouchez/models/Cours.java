package com.marcbouchez.models;

import com.marcbouchez.utils.Countable;


public abstract class Cours implements Countable {

    protected int id;
    protected int ageMini;

    //List de Jour

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAgeMini(int ageMini) {
        this.ageMini = ageMini;
    }

    public abstract String getLibelle();

    public String getTruncatedLibelle() {
        String s = this.getLibelle();
        return s.length() < 18 ? s : s.substring(0, 17) + "...";
    }
}
