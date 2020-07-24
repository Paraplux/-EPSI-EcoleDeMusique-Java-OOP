package com.marcbouchez.utils;

import java.util.List;

public class UniqueID {

    /**
     * Generate a id from a list of countable object
     * @param entries Entries is a list of Object that must implements Countable
     * @return a unique ID
     */
    public static int generateFrom (List<?> entries) {
        if (entries != null) {
            return entries.size() == 0 ? 1 : ((Countable) entries.get(entries.size() - 1)).getId() + 1;
        } else {
            return -1 ;
        }

    }
}
