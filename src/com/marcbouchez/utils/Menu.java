package com.marcbouchez.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Marc Bouchez
 * Personnal class created to easily render basic menu with callback functions
 *
 * Use it like this
 * Menu myMenu = new Menu();
 *
 * myMenu.addEntry(1, "My menu choice", AnyClass::myFunction);
 * myMenu.addEntry(0, "My menu quit choice");
 *
 * myMenu.render();
 *
 * By not passing any callback to your menu entry, you make it work like a "quit choice"
 */
public class Menu {

    List<MenuEntry> menuEntries;
    static final Scanner scanner = new Scanner(System.in);

    public Menu() {
        this.menuEntries = new LinkedList<>();
    }

    /**
     * Create a normal entry in the menu
     * @param choice Choice to choose
     * @param entry String to display
     * @param callback Callback to run when selected
     */
    public void addEntry (int choice, String entry, Runnable callback) {
        MenuEntry menuEntry = new MenuEntry(choice, entry, callback);
        this.menuEntries.add(menuEntry);
    }

    /**
     * Create a quit entry in the menu
     * @param choice Choice to choose
     * @param entry String to display
     */
    public void addEntry (int choice, String entry) {
        MenuEntry menuEntry = new MenuEntry(choice, entry);
        this.menuEntries.add(menuEntry);
    }


    /**
     * Render the menu then call the process. If we choose the quit choice, then we quit the menu
     */
    public void render () {
        boolean repeat = true;

        while (repeat) {

            System.out.print("\n");
            for (MenuEntry entry : menuEntries) {
                entry.display();
            }

            if (scanner.hasNextInt()) {
                int userChoice = scanner.nextInt();
                if (userChoice != getQuitChoice()) {
                    process(userChoice);
                    repeat = false;
                } else if (userChoice == getQuitChoice()) {
                    repeat = false;
                } else {
                    System.out.println("La saisie est incorrecte");
                }
            } else {
                System.out.println("La saisie est incorrecte");
                scanner.nextLine();
                System.out.println();
            }
        }
    }

    /**
     * Get the userEntry, if it finds entry, launch the callback, if not, re-render()
     * @param userEntry
     */
    public void process (int userEntry) {
        boolean foundAChoice = false;
        do {
            for (MenuEntry entry : menuEntries) {
                if (userEntry == entry.choice) {
                    foundAChoice = true;
                    entry.callback.run();
                }
            }
            render();
        } while (!foundAChoice);
    }

    /**
     * Return the choice of the quit entry
     * @return int user quit choice || -1 if no quit choice
     */
    public int getQuitChoice () {
        for (MenuEntry entry : menuEntries) {
            if (entry.callback == null ) {
                return entry.choice;
            }
        }
        return -1;
    }

    /**
     * Inner Class to represent a menu entry
     */
    class MenuEntry {
        private int choice;
        private String entry;
        private Runnable callback;

        public MenuEntry (int choice, String entry, Runnable callback) {
            this.choice = choice;
            this.entry = entry;
            this.callback = callback;
        }

        public MenuEntry (int choice, String entry) {
            this.choice = choice;
            this.entry = entry;
            this.callback = null;
        }


        public void display() {
            System.out.println(this.choice + ". " + this.entry);
        }
    }
}
