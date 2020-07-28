package com.marcbouchez.models;

import com.marcbouchez.drivers.InstrumentDriver;
import com.marcbouchez.utils.Search;

public class CoursIndividuel extends Cours {
    private Instrument instrument;

    public void setInstrument(int instrumentID) {
        this.instrument = (Instrument) Search.withId(instrumentID, InstrumentDriver.getInstruments());
    }

    public String getLibelle() {
        return instrument.getIntitule();
    }

    @Override
    public String toString() {
        return "Cours individuel " + id + " : " + instrument.getIntitule() + " (" + ageMini + "ans min.)";
    }
}
