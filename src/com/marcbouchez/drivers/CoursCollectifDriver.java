package com.marcbouchez.drivers;

import com.marcbouchez.models.Cours;
import com.marcbouchez.models.CoursCollectif;
import com.marcbouchez.utils.UniqueID;

import java.util.Scanner;

public class CoursCollectifDriver {

    protected static final Scanner scanner = new Scanner(System.in);


    /**
     * Crée un cours collectif selon les informations demandées à l'utilisateur
     */
    public static void create() {
        System.out.println("Vous allez créer un nouveau cours collectif.");
        CoursCollectif cc = new CoursCollectif();
        System.out.println("Veuillez entrer le libellé ?");

        try {
            cc.setLibelle(scanner.next());
            System.out.println("Quel est l'âge minimum que l'on doit avoir pour y assister ?");
            cc.setAgeMini(scanner.nextInt());
            System.out.println("Quel est l'âge maximum que l'on doit avoir pour y assister ?");
            cc.setAgeMaxi(scanner.nextInt());
            System.out.println("Combien de places maximum possède le cours ?");
            cc.setNbPlaceMaxi(scanner.nextInt());
            cc.setId(UniqueID.generateFrom(CoursDriver.getTousLesCours()));

            CoursDriver.addCours(cc);
        } catch (Exception e) {
            System.out.println("La saisie est incorrecte");
        }
    }

    /**
     * Liste l'ensemble des cours collectifs
     */
    public static void list() {
        for (Cours cours : CoursDriver.getTousLesCours()) {
            if (cours instanceof CoursCollectif) {
                System.out.println(cours.toString());
            }
        }
    }
}
