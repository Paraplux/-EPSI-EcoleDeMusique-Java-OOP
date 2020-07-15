package com.marcbouchez.models;

import com.marcbouchez.utils.Countable;

public class CoursCollectif extends Cours implements Countable {

    private String libelle;
    private int ageMaxi;
    private int nbPlaceMaxi;

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setAgeMaxi(int ageMaxi) {
        this.ageMaxi = ageMaxi;
    }

    public void setNbPlaceMaxi(int nbPlaceMaxi) {
        this.nbPlaceMaxi = nbPlaceMaxi;
    }

    @Override
    public String toString() {
        return "Cours collectif " + id + " : " + libelle + " (Entre " + ageMini + " et " + ageMaxi + " ans. " + nbPlaceMaxi + "places max.)";
    }
}
