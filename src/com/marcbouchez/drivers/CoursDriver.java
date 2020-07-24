package com.marcbouchez.drivers;

import com.marcbouchez.models.CoursIndividuel;
import com.marcbouchez.models.CoursCollectif;
import com.marcbouchez.models.Cours;
import com.marcbouchez.models.Planning;
import com.marcbouchez.models.DemiJour;
import com.marcbouchez.utils.Menu;

import java.util.*;

public class CoursDriver {

    static List<Cours> tousLesCours = new LinkedList<>();

    // Bloc statique pour instancier quelques données par défaut
    static {
        CoursIndividuel ci1 = new CoursIndividuel();
        ci1.setId(1);
        ci1.setInstrument(1);
        ci1.setAgeMini(8);
        tousLesCours.add(ci1);
        CoursIndividuel ci2 = new CoursIndividuel();
        ci2.setId(2);
        ci2.setInstrument(2);
        ci2.setAgeMini(9);
        tousLesCours.add(ci2);
        CoursIndividuel ci3 = new CoursIndividuel();
        ci3.setId(3);
        ci3.setInstrument(3);
        ci3.setAgeMini(10);
        tousLesCours.add(ci3);
        CoursIndividuel ci4 = new CoursIndividuel();
        ci4.setId(4);
        ci4.setInstrument(4);
        ci4.setAgeMini(6);
        tousLesCours.add(ci4);


        CoursCollectif cc1 = new CoursCollectif();
        cc1.setId(5);
        cc1.setLibelle("Cours de solfège");
        cc1.setAgeMini(8);
        cc1.setAgeMaxi(16);
        cc1.setNbPlaceMaxi(10);
        tousLesCours.add(cc1);
        CoursCollectif cc2 = new CoursCollectif();
        cc2.setId(6);
        cc2.setLibelle("Découverte des instruments");
        cc2.setAgeMini(8);
        cc2.setAgeMaxi(14);
        cc2.setNbPlaceMaxi(12);
        tousLesCours.add(cc2);
        CoursCollectif cc3 = new CoursCollectif();
        cc3.setId(7);
        cc3.setLibelle("Les cordes");
        cc3.setAgeMini(8);
        cc3.setAgeMaxi(12);
        cc3.setNbPlaceMaxi(6);
        tousLesCours.add(cc3);
        CoursCollectif cc4 = new CoursCollectif();
        cc4.setId(8);
        cc4.setLibelle("Les percussions");
        cc4.setAgeMini(8);
        cc4.setAgeMaxi(16);
        cc4.setNbPlaceMaxi(14);
        tousLesCours.add(cc4);
    }

    /**
     * Permet la redirection du sous menu ayant attrait au cours
     */
    public static void manage() {
        //Sous menu

        Menu coursMenu = new Menu();

        System.out.println("Bienvenue dans l'outil de gestion des cours.");
        System.out.println("Ici, vous pouvez créer vos différents cours, afin de les assigner à un planning, utilisez l'outil de gestion de planning.");

        coursMenu.addEntry(1, "Créer un cours collectif", CoursCollectifDriver::create);
        coursMenu.addEntry(2, "Créer un cours individuel", CoursIndividuelDriver::create);
        coursMenu.addEntry(3, "Lister tous les cours", CoursDriver::list);
        coursMenu.addEntry(4, "Lister uniquement les cours collectifs", CoursCollectifDriver::list);
        coursMenu.addEntry(5, "Lister uniquement les cours individuel", CoursIndividuelDriver::list);
        coursMenu.addEntry(6, "Lister les cours déjà assignés et leur demi-journée respective", CoursDriver::recap);
        coursMenu.addEntry(0, "Retour");

        coursMenu.render();
    }


    /**
     * Ajoute un cours à l'ensemble de nos cours
     * @param c un cours
     */
    public static void addCours(Cours c) {
        tousLesCours.add(c);
    }

    /**
     * @return la liste de tous nos cours
     */
    public static List<Cours> getTousLesCours () {
        return tousLesCours;
    }

    /**
     * Affiche la liste de tous nos cours
     */
    public static void list() {
        for (Cours cours : tousLesCours) {
            System.out.println(cours.toString());
        }
    }

    /**
     * Affiche un récapitulatif des cours déjà assignés et de leur demi journée respectives
     */
    public static void recap() {
        for (Planning p : PlanningDriver.getPlannings()) {
            HashMap<DemiJour, Cours> lesCoursAssignes = p.getLesCoursAssignes();
            Set<Map.Entry<DemiJour, Cours>> set = lesCoursAssignes.entrySet();
            for(Map.Entry<DemiJour, Cours> pair : set) {
                DemiJour dj = pair.getKey();
                Cours cours = pair.getValue();
                System.out.println(dj.toString() + " => " + cours.getLibelle());
            }
        }
    }
}
