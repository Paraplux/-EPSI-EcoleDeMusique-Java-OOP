package com.marcbouchez.main;

import com.marcbouchez.drivers.CoursDriver;
import com.marcbouchez.drivers.InstrumentDriver;
import com.marcbouchez.drivers.PlanningDriver;
import com.marcbouchez.utils.Menu;

public class Main {

    public static void main(String[] args) {
        /*

        VERSION DU JDK : 13

        Bonjour Julien.

        Pour le fonctionnement de l'application j'ai pris un peu de liberté donc j'ai vu les choses comme suit :
            - La personne peut ajouter des instruments, elle peut créer des cours, collectifs ou individuels.
            - Elle peut venir créer des plannings, (un planning = une semaine)
            - Je pars du principe que les jours peuvent comporter deux cours, un le matin et un l'après midi
            - Que le matin ce sont les cours individuels et l'après midi ce sont les cours collectifs
            - Et elle peut donc assigner des cours à ses planning, le programme va automatiquement gérer si c'est un cours du matin ou pas
            selon que ce soit collectif ou individuel

        Pour tout ce qui est classes représentant un objet je range ça dans models
        Pour tout ce qui va gérer mes modèles, les créer, les lister, je range ça dans drivers
        CoursDriver va gérer mes Cours etc...

        Pour le menu et autres opération j'ai créé un package utils

        J'espère que cette version te plaira, tu verras que certaines fonctions n'ont pas le même nom, ni ne sont pas forcément au bon endroit
        J'ai fait des choix par rapport à la maintenabilité et au découpage que j'ai choisi

            - Par exemple la méthode aLieuLeJour() n'est pas présente dans mon code, puisque l'affichage du planning en mode emploi du temps
            te donne déjà un apperçu des cours par jour en revanche une méthode permettant de récapituler les cours et leur demi journée est présente

         J'ai hésité à laisser ou pas les semaines passées, j'ai finalement choisi de les laisser au cas où on aurait besoin d'y jeter un oeil
         C'est également possible avec cette version de venir écrire dans un fichier, plus tard, nos objets

        J'espère avoir géré un maximum de cas de figure mais c'est dur de tester son propre code je trouve, on est pas assez méchant avec soi même
        Donc je te laisse naviguer, tester et triturer mon code, bonne lecture !
         */

	    app();

    }

    public static void app () {

        System.out.println("Bienvenue dans votre gestionnaire de cours de musique, test");
        System.out.println("Vous allez pouvoir gérer une année entière de cours");


        // Menu
        /*
        L'utilisation de la classe Menu est simple, on créer un menu en l'instanciant
        Puis on agit dessus en ajoutant des entrées au menu
        Si l'entrée possède un callback alors c'est une entrée normale,
        si l'utilisateur choisi le numéro, alors le callback est appelé
        Si l'entrée ne possède pas de callback alors c'est une entrée pour sortir du menu
        La méthode render() me permet de générer le menu
        */

        Menu mainMenu = new Menu();

        mainMenu.addEntry(1, "Gestion du planning", PlanningDriver::manage);
        mainMenu.addEntry(2, "Gestion des cours", CoursDriver::manage);
        mainMenu.addEntry(3, "Gestion des instruments", InstrumentDriver::manage);
        mainMenu.addEntry(0, "Quitter");

        mainMenu.render();
    }
}
