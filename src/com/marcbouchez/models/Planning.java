package com.marcbouchez.models;

import com.marcbouchez.drivers.CoursDriver;
import com.marcbouchez.utils.Countable;
import com.marcbouchez.utils.Search;

import java.util.*;

public class Planning implements Countable {

    private int id;
    private int nbWeek;
    private HashMap<DemiJour, Cours> lesCoursAssignes = new HashMap<>();
    private List<Jour> lesJours = new LinkedList<>();
    private Set<Map.Entry<DemiJour, Cours>> set = lesCoursAssignes.entrySet();

    /**
     * Assigne un cours à sa demi journée en fonction de
     * @param nbWeek numéro de la semaine
     * @param jourID l'id du jour
     * @param isAM si c'est le matin ou l'après midi
     * @param coursID l'id du cours
     */
    public void assignCours (int nbWeek, int jourID, boolean isAM, int coursID) {
        this.lesCoursAssignes.put(new DemiJour(nbWeek, jourID, isAM), (Cours) Search.withId(coursID, CoursDriver.getTousLesCours()));
    }

    /**
     * @param nbWeek numéro de la semaine
     * @param jourID l'id du jour
     * @param isAM si c'est le matin ou l'après midi
     * @return renvoie la demi journée
     */
    public DemiJour getCurrentDemiJour (int nbWeek, int jourID, boolean isAM) {
        for(Map.Entry<DemiJour, Cours> pair : this.set) {
            DemiJour dj = pair.getKey();
            if (dj.getNbWeek() == nbWeek && dj.getJourID() == jourID && dj.isAM() == isAM) {
                return dj;
            }
        }
        return null;
    }

    /**
     * Détermine si la demi journée est libre
     * @param jour le jour à vérifier
     * @return true || false
     */
    public boolean creneauLibre (Jour jour) {
        for(Map.Entry<DemiJour, Cours> pair : this.set) {
            DemiJour dj = pair.getKey();
            if (dj.getJourID() == jour.getId()) {
                return false;
            }
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbWeek() {
        return nbWeek;
    }

    public void setNbWeek(int nbWeek) {
        this.nbWeek = nbWeek;
    }

    public List<Jour> getLesJours() {
        return lesJours;
    }

    public void setLesJours(List<Jour> lesJours) {
        this.lesJours = lesJours;
    }

    public void setLesCoursAssignes(HashMap<DemiJour, Cours> lesCoursAssignes) {
        this.lesCoursAssignes = lesCoursAssignes;
    }

    public HashMap<DemiJour, Cours> getLesCoursAssignes () {
        return lesCoursAssignes;
    }
}
