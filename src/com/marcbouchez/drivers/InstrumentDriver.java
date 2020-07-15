package com.marcbouchez.drivers;

import com.marcbouchez.models.Instrument;
import com.marcbouchez.utils.Menu;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class InstrumentDriver {

    private static List<Instrument> instruments = new LinkedList<>();
    private static final Scanner scanner = new Scanner(System.in);

    static {
        Instrument i1 = new Instrument();
        i1.setId(1);
        i1.setIntitule("Guitare");
        instruments.add(i1);

        Instrument i2 = new Instrument();
        i2.setId(2);
        i2.setIntitule("Violon");
        instruments.add(i2);

        Instrument i3 = new Instrument();
        i3.setId(3);
        i3.setIntitule("Basse");
        instruments.add(i3);

        Instrument i4 = new Instrument();
        i4.setId(4);
        i4.setIntitule("Batterie");
        instruments.add(i4);

        Instrument i5 = new Instrument();
        i5.setId(5);
        i5.setIntitule("Piano");
        instruments.add(i5);
    }

    /**
     * Gère la redirection du sous menu ayant attrait aux instruments
     */
    public static void manage() {
        Menu instrumentMenu = new Menu();

        instrumentMenu.addEntry(1, "Créer un instrument", InstrumentDriver::create);
        instrumentMenu.addEntry(2, "Lister les instruments", InstrumentDriver::list);
        instrumentMenu.addEntry(0, "Retour");

        instrumentMenu.render();
    }

    /**
     * Crée un instrument avec les informations demandées à l'utilisateur
     */
    public static void create() {
        System.out.println("Vous allez ajouter un nouvel instrument");
        Instrument i = new Instrument();
        System.out.println("Quel est son nom ?");

        try {
            i.setIntitule(scanner.next());
            instruments.add(i);
        } catch (Exception e) {
            System.out.println("La saisie est incorrecte");
        }
    }

    /**
     * Affiche l'ensemble des instruments
     */
    public static void list () {
        for (Instrument instrument : instruments) {
            System.out.println(instrument.toString());
        }
    }

    /**
     * @return la liste des instruments
     */
    public static List<Instrument> getInstruments() {
        return instruments;
    }
}
