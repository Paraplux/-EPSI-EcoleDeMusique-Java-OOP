package com.marcbouchez.drivers;

import com.marcbouchez.models.Cours;
import com.marcbouchez.models.CoursIndividuel;
import com.marcbouchez.utils.Search;
import com.marcbouchez.utils.UniqueID;

import java.util.Scanner;

public class CoursIndividuelDriver {

    protected static final Scanner scanner = new Scanner(System.in);

    /**
     * Crée un cours collectif selon les informations demandées à l'utilisateur
     */
    public static void create() {
        System.out.println("Vous allez créer un nouveau cours individuel.");
        CoursIndividuel ci = new CoursIndividuel();
        System.out.println("Sur quel instrument porte le cours ?");
        InstrumentDriver.list();
        try {
            int instrumentChoice = scanner.nextInt();
            if (Search.withId(instrumentChoice, InstrumentDriver.getInstruments()) != null) {
                ci.setInstrument(instrumentChoice);
            } else {
                System.out.println("La saisie est incorrecte");
                return;
            }
            System.out.println("Quel est l'âge minimum que l'on doit avoir pour y assister ?");
            ci.setAgeMini(scanner.nextInt());
            ci.setId(UniqueID.generateFrom(CoursDriver.getTousLesCours()));

            CoursDriver.addCours(ci);
        } catch (Exception e) {
            System.out.println("La saisie est incorrecte");
        }
    }


    /**
     * Liste l'ensemble des cours individuels
     */
    public static void list() {
        for (Cours cours : CoursDriver.getTousLesCours()) {
            if (cours instanceof CoursIndividuel) {
                System.out.println(cours.toString());
            }
        }
    }
}
