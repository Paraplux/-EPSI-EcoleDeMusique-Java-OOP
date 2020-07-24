package com.marcbouchez.drivers;

import com.marcbouchez.models.Jour;

import java.util.LinkedList;
import java.util.List;

public class JourDriver {
    private static final List<Jour> joursSemaine = new LinkedList<>();
    private static final String[] libelles = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

    // Bloc d'instanciation statique pour générer les jours
    static {
        // Ici je prends comme id i + 1 car je ne risque pas de suppression de jour, ils resteront à 7 quoiqu'il arrive
        for(int i = 0; i < 7; i++) {
            Jour currentDay = new Jour();
            currentDay.setId(i + 1);
            currentDay.setLibelle(libelles[i]);
            joursSemaine.add(currentDay);
        }
    }

    /**
     * @return la liste des jours de la semaine
     */
    public static List<Jour> getJoursSemaine() {
        return joursSemaine;
    }
}
