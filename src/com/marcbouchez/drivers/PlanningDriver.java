package com.marcbouchez.drivers;

import com.marcbouchez.models.*;
import com.marcbouchez.utils.Menu;
import com.marcbouchez.utils.Search;
import com.marcbouchez.utils.UniqueID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.*;

public class PlanningDriver {

    private static final List<Planning> plannings = new LinkedList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocalDate date = LocalDateTime.now().toLocalDate();

    //le numéro de semaine d'aujourd'hui
    private static final int CURRENT_WEEK = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    //le nombre maximum de semaine dépendant de l'année en cours
    private static final long MAX_WEEK_IN_YEAR = IsoFields.WEEK_OF_WEEK_BASED_YEAR.rangeRefinedBy(date).getMaximum();

    /**
     * Permet de gérer la redirection du sous menu planning
     */
    public static void manage() {

        Menu planningMenu = new Menu();

        System.out.println("Bienvenue dans l'outil de gestion des plannings.");
        System.out.println("Ici, vous pouvez créer vos différents plannings, et assigner les cours à vos planning.");
        if (getPlanning(CURRENT_WEEK) == null) {
            System.out.println("\n /!\\ Attention le planning de cette semaine n'a pas été créé");
        }
        planningMenu.addEntry(1, "Afficher le planning de cette semaine", PlanningDriver::renderDefault);
        planningMenu.addEntry(2, "Créer le planning de cette semaine (" + CURRENT_WEEK + ")" , PlanningDriver::createDefault);
        planningMenu.addEntry(3, "Créer un planning pour une semaine donnée", PlanningDriver::create);
        planningMenu.addEntry(4, "Lister les planning", PlanningDriver::list);
        planningMenu.addEntry(5, "Allez au planning...", PlanningDriver::goTo);
        planningMenu.addEntry(0, "Retour");

        planningMenu.render();
    }

    /**
     * Affiche la semaine courante
     */
    public static void renderDefault() {
        render(CURRENT_WEEK);
    }


    /**
     * Affiche la semaine demandée et fait appel à la fonction show() qui gèrera l'affichage des données
     * @param weekToDisplay semaine à afficher
     */
    public static void render(int weekToDisplay) {
        int choice;

        System.out.println(String.format("%97s", "PLANNING DE LA SEMAINE: " + weekToDisplay));
        System.out.println(String.format("%89s", date) + "\n");

        show(getPlanning(weekToDisplay));

        System.out.println("\n1. Semaine précédente");
        System.out.println("2. Semaine suivante");
        System.out.println("3. Assigner des cours");
        System.out.println("0. Retour");

        if(scanner.hasNextInt()) {
            choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                    if (weekToDisplay - 1 == 0) {
                        render((int) MAX_WEEK_IN_YEAR);
                    } else {
                        render(weekToDisplay - 1);
                    }
                    break;
                case 2 :
                    if (weekToDisplay + 1 > MAX_WEEK_IN_YEAR) {
                        render(1);
                    } else {
                        render(weekToDisplay + 1);
                    }
                    break;
                case 3 :
                    PlanningDriver.assign(weekToDisplay);
                    break;
                default :
                    break;
            }
        } else {
            System.out.println("La saisie est incorrect");
            scanner.nextLine();
            System.out.println();
        }

    }

    /**
     * Gère l'affichage d'un planning
     * @param p planning
     */
    public static void show (Planning p) {
        if (p != null) {
            // Voir pour simplifier ça
            System.out.print(String.format("|%-20s", "Jours"));
            for (Jour jour: JourDriver.getJoursSemaine()) {
                System.out.print(String.format("|%-20s", jour.getLibelle()));
            }
            System.out.println("\n_________________________________________________________________________________________________________________________________________________________________________");
            System.out.print(String.format("|%-20s", "Individuel"));
            for (Jour jour: JourDriver.getJoursSemaine()) {
                DemiJour currentDemiJour = p.getCurrentDemiJour(p.getNbWeek(), jour.getId(), true);
                if (p.getLesCoursAssignes().containsKey(currentDemiJour)) {
                    System.out.print(String.format("|%-20s", p.getLesCoursAssignes().get(currentDemiJour).getTruncatedLibelle()));
                } else {
                    System.out.print(String.format("|%-20s", ""));
                }
            }
            System.out.println(String.format("|%167s|", ""));
            System.out.print(String.format("|%-20s", "Collectif"));
            for (Jour jour: JourDriver.getJoursSemaine()) {
                DemiJour currentDemiJour = p.getCurrentDemiJour(p.getNbWeek(), jour.getId(), false);
                if (p.getLesCoursAssignes().containsKey(currentDemiJour)) {
                    System.out.print(String.format("|%-20s", p.getLesCoursAssignes().get(currentDemiJour).getTruncatedLibelle()));
                } else {
                    System.out.print(String.format("|%-20s", ""));}
                }
            System.out.println("\n_________________________________________________________________________________________________________________________________________________________________________");
        } else {
            System.out.println(String.format("%100s", "Pas de planning à cette semaine."));
        }
    }

    /**
     * Créé le planning de la semaine courante
     */
    public static void createDefault() {
        System.out.println("Nous sommes à la semaine : " + CURRENT_WEEK);
        if (getPlanning(CURRENT_WEEK) != null) {
            System.out.println("Un planning existe déjà pour cette semaine.");
        } else {
            System.out.println();
            Planning pCurrent = new Planning();
            pCurrent.setId(UniqueID.generateFrom(plannings));
            pCurrent.setNbWeek(CURRENT_WEEK);
            pCurrent.setLesJours(JourDriver.getJoursSemaine());
            plannings.add(pCurrent);
            System.out.println("Le planning de la semaine vient d'être créé.");
        }
        render(CURRENT_WEEK);
    }

    /**
     * Créé un planning selon le choix de l'utilisateur
     */
    public static void create() {
        System.out.println("Pour quelle semaine voulez vous créer un planning ? ");

        if (scanner.hasNextInt()) {
            int nbWeek = scanner.nextInt();

            if (nbWeek > 0 && nbWeek <= MAX_WEEK_IN_YEAR) {
                System.out.println("Vous voulez créer un planning pour la semaine : " + nbWeek);
                if (getPlanning(nbWeek) != null) {
                    System.out.println("Un planning existe déjà pour cette semaine.");
                } else {
                    System.out.println();
                    Planning p = new Planning();
                    p.setId(UniqueID.generateFrom(plannings));
                    p.setNbWeek(nbWeek);
                    p.setLesJours(JourDriver.getJoursSemaine());
                    plannings.add(p);
                    System.out.println("Le planning de la semaine " + nbWeek + " vient d'être créé.");
                }
            } else {
                System.out.println("Veuillez saisir un numéro de semaine valide");
            }
        }  else {
            System.out.println("La saisie est incorrect");
            scanner.nextLine();
            System.out.println();
        }

    }

    /**
     * Assigne des cours au planning de la semaine demandée
     * @param nbWeek semaine à gérer
     */
    public static void assign(int nbWeek) {
        Planning p = PlanningDriver.getPlanning(nbWeek);
        System.out.println("Vous allez assigner un cours à un jour de la semaine : " + nbWeek + "\n");
        System.out.println("Quel cours voulez vous assigner ? (choisissez son identifiant) \n");
        CoursDriver.list();

        if (scanner.hasNextInt()) {
            int coursID = scanner.nextInt();
            if (Search.withId(coursID, CoursDriver.getTousLesCours()) != null) {
                System.out.println("Cours selectionné, l'assigner à quel jour ? \n");
                for (Jour jour: JourDriver.getJoursSemaine()) {
                    if(p != null && p.creneauLibre(jour)) {
                        System.out.print(jour.getId() + ". " + jour.getLibelle() + "  ");
                    }
                }
                System.out.print("\n");
                int jourID = scanner.nextInt();
                boolean isAM = Search.withId(coursID, CoursDriver.getTousLesCours()) instanceof CoursIndividuel;
                Objects.requireNonNull(p).assignCours(nbWeek, jourID, isAM, coursID);
            } else {
                System.out.println("Veuillez choisir un identifiant de cours existant");
            }
        }  else {
            System.out.println("La saisie est incorrect");
            scanner.nextLine();
            System.out.println();
        }
        System.out.print("\n");

        render(nbWeek);
    }

    /**
     * Liste les plannings déjà créés
     */
    public static void list() {
        System.out.println("Vous avez des plannings pour les semaines : ");
        for (Planning planning : plannings ) {
            System.out.print(planning.getNbWeek() + " | ");
        }
    }

    /**
     * Navigue jusqu'à la semaine demandé et appelle la fonction render(), et ainsi de suite...
     */
    public static void goTo() {
        System.out.println("Entrez le numéro de la semaine à afficher");

        if (scanner.hasNextInt()) {
            int nbWeek = scanner.nextInt();
            if (nbWeek > 0 && nbWeek <= MAX_WEEK_IN_YEAR) {
                render(nbWeek);
            } else {
                System.out.println("Veuillez saisir un numéro de semaine valide");
            }
        }  else {
            System.out.println("La saisie est incorrect");
            scanner.nextLine();
            System.out.println();
        }
    }

    /**
     * Renvoie le planning d'une semaine demandée
     * @param nbWeek Numéro de la semaine
     * @return le planning || null
     */
    public static Planning getPlanning (int nbWeek) {
        for (Planning planning : plannings) {
            if (nbWeek == planning.getNbWeek()) {
                return planning;
            }
        }
        return null;
    }

    /**
     * @return la liste de plannings
     */
    public static List<Planning> getPlannings() {
        return plannings;
    }
}
