package com.marcbouchez.models;

import com.marcbouchez.utils.Countable;

public class Instrument implements Countable {

    private int id;
    private String intitule;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return "Instrument " + id + " : " + intitule;
    }
}
