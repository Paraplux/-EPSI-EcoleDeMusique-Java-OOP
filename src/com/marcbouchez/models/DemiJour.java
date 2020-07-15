package com.marcbouchez.models;

import com.marcbouchez.drivers.JourDriver;
import com.marcbouchez.utils.Search;

import java.util.Objects;

public class DemiJour {

    private int nbWeek;
    private int jourID;
    private boolean isAM;

    public DemiJour (int nbWeek, int jourID, boolean isAM) {
        this.nbWeek = nbWeek;
        this.jourID = jourID;
        this.isAM = isAM;
    }

    public int getNbWeek() {
        return nbWeek;
    }

    public int getJourID() {
        return jourID;
    }

    public boolean isAM() {
        return isAM;
    }

    @Override
    public String toString() {
        String s = isAM ? "matin" : "après-midi";
        return "Sem." + nbWeek + " " + ((Jour) Objects.requireNonNull(Search.withId(this.jourID, JourDriver.getJoursSemaine()))).getLibelle() + " " + s;
    }
}
