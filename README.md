# Favorize repository

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![expo version](https://img.shields.io/badge/jdk-13-orange)](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-green)](https://github.com/Paraplux/-EPSI-EcoleDeMusique-Java-OOP/issues)

 - Name of this project : Project Music School
 - Language : Java
 - Plateforme : Cross-plateform CLI

The purpose of this project is barely simple, it aims to handle school music courses. The user will be able to :
    - Create instrument
    - Create individual course
    - Create group course
    - Link those courses with days of week
    - Navigate through a menu to perfroms theses actions


***NOTE that the code is partially in french because it was a request, I was following a french UML diagram. And so the display is french too***

## Overview 

[Architecture](#architecture)

[Utils](#utils)

[Application](#application)

[Miscellaneous](#miscellaneous)



## Architecture

I've made some choices about my project architecture, it's a personnal taste, but I try to follow conventions as close as possible.

```
project
│   ...
└───out
└───src/com.marcbouchez
    │
    └───main
        │── Main.java
    └───models¹
        │   ...
        │── Cours.java
        │── CoursIndividuel.java
        │── CoursCollectif.java
        │   ...
    └───drivers²
        │   ...
        │── CoursDriver.java
        │   ...
    └───utils³
        │   ...
        │── Menu.java
        │── Search.java
        │   ...
│   ...
```

### ¹Models

A model is a representation of an object, if I need to handle a planning of courses, I have to create a course model

Let's take a look a the handling of my instruments

My ``Instrument`` model is just a POJO (Plain Old Java Object) with private member attributes and getters / setters.

**models/Instrument.java**
```java
public class Instrument implements Countable {

    private int id;
    private String intitule;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return "Instrument " + id + " : " + intitule;
    }
}
```

### ²Drivers

Now what I call a driver is a class that handles operations about a collection of model instances.
For example if I need to handle operations about courses, I create a driver for this model to perform thoses actions like "create a course", "get all the courses", etc.

Previously we made the model to represent instruments, here's how I manage them with my driver.
I create a static ``List`` of `Instrument` and I perform actions to this ``List`` : addition, deletion, etc.

Here's the creation of an instrument : 

**drivers/InstrumentDriver.java**
```java
public class InstrumentDriver {
    private static final List<Instrument> instruments = new LinkedList<>();

    //...

    /**
     * Create an instrument with user entry
     * @return void
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
    //...
}
```

### ³Utils

See the [next](#utils) section


## Utils

In the utils package, I will put everythings I need to make my development easier. We'll take a look at two of them.

### Search

Several times I used to search for a particular object in a List of my object by his `id`, and I didn't want to repeat myself over and over again.
My method was to create an interface with just a `getId()` method, and I ensure my model to implement that inteface.

Here's the interface :
**utils/Countable.java**
```java
public interface Countable {

    int getId();

}
```

The implementation (note that the model needs to override the ``getId()`` method) :

**models/Instrument.java**
```java
public class Instrument implements Countable {
    ...
    @Override
    public int getId() {
        return id;
    }
    ...
}
```

And how I used it :

**utils/Search.java**
```java
public class Search {

    /**
     * Search and id in a list and return the item
     * @param id id to search for
     * @param items list to be run through
     * @return Countable item
     */
    public static Object withId (int id, List<?> items) {
        for (Object item : items) {
            Countable countableItem = (Countable) item;
            if (countableItem.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
```

### Menu

I was also bored about the idea to generate again and again a menu, so I create a class to handle this.

Here's how I perform that : 

**utils/Menu.java**

I create a ``Menu.java`` class that contains a ``List`` of ``MenuEntry``. ``MenuEntry`` is an inner class that reprensents an entry of the created menu.

A menu entry has a integer property that reprensents the choice (1, 2, ...), a string property to display the menu line and optionnally a runnable callback
```java
...
/**
 * Inner Class to represent a menu entry
 */
static class MenuEntry {
    private final int choice;
    private final String entry;
    private final Runnable callback;

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
...
```

Now I can create menu entry (I've stripped the javadoc for the README): 

```java

public void addEntry (int choice, String entry, Runnable callback) {...}

public void addEntry (int choice, String entry) {...}
```

The trick is to make the difference between a normal entry and a quit entry.

To get the choice of the quit entry : 

```java
public int getQuitChoice () {
    for (MenuEntry entry : menuEntries) {
        if (entry.callback == null ) {
            return entry.choice;
        }
    }
    return -1;
}
```

To render the menu : 

> Note : this point is important, by surcharging the ``addEntry()`` method I can now perform two actions : 
>    - First, if we do add a callback, the menu entry act normally : the user make a choice and the callback is called
>    - But if we don't add callback, the menu entry is considered as a "quit entry" : the user make the choice and we quit the menu

```java
public void render () {
    boolean repeat = true;

    while (repeat) {

        System.out.print("\n");
        for (MenuEntry entry : menuEntries) {
            entry.display();
        }

        if (scanner.hasNextInt()) {
            int userChoice = scanner.nextInt();

            // Here's how I break differentiate normal entry from quit entry
            // The quit entry breaks the menu and leave it
            // The normal one call the process() method on the user choice 
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
```

And finally we can process by searching the choice in the entries and ``run()`` the callback : 

```java
public void process (int userEntry) {
    boolean foundAChoice = false;
    do {
        for (MenuEntry entry : menuEntries) {
            if (userEntry == entry.choice) {
                foundAChoice = true;
                if (entry.callback != null) {
                    entry.callback.run();
                }
            }
        }
        render();
    } while (!foundAChoice);
}
```

## Application

The rest of the application works pretty basic : 

The user can create and manage : 

- instruments

```
1. Créer un instrument
2. Lister les instruments
0. Retour
```

- courses 

```
1. Créer un cours collectif
2. Créer un cours individuel
3. Lister tous les cours
4. Lister uniquement les cours collectifs
5. Lister uniquement les cours individuel
6. Lister les cours déjà assignés et leur demi-journée respective
0. Retour
```

- plannings

```
1. Afficher le planning de cette semaine
2. Créer le planning de cette semaine X
3. Créer un planning pour une semaine donnée
4. Lister les planning
5. Allez au planning...
0. Retour
```

- And display it (I made the choice to display the planning in "timetable mode") : 

```
                                                                       PLANNING DE LA SEMAINE: 31
                                                                               2020-07-28

|Jours               |Lundi               |Mardi               |Mercredi            |Jeudi               |Vendredi            |Samedi              |Dimanche            
_________________________________________________________________________________________________________________________________________________________________________
|Individuel          |Basse               |                    |                    |                    |                    |                    |                 
|                                                                                                                                                                       |
|Collectif           |                    |                    |                    |Cours de solfège    |                    |                    |                    
_________________________________________________________________________________________________________________________________________________________________________
```

## Miscellaneous

### Contributions

Feel free to take the sources and contribute, I'll appreciate it.

### License


<p style="text-align:center"><strong>MIT</strong></p>
<p style="text-align:center">Made with  ❤  by Marc Bouchez "Paraplux" - 2020</p>