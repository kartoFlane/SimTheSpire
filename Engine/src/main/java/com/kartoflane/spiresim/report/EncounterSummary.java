package com.kartoflane.spiresim.report;

public class EncounterSummary {

    private int turns;


    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public EncounterSummary withTurns(int turns) {
        this.setTurns(turns);
        return this;
    }
}
