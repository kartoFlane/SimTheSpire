package com.kartoflane.spiresim.report;

import java.util.List;

public class PlaythroughSummary {

    private List<EncounterSummary> encounterSummaries;


    public void setEncounterSummaries(List<EncounterSummary> encounterSummaries) {
        this.encounterSummaries = encounterSummaries;
    }

    public PlaythroughSummary withEncounterSummaries(List<EncounterSummary> encounterSummaries) {
        this.setEncounterSummaries(encounterSummaries);
        return this;
    }

    public List<EncounterSummary> getEncounterSummaries() {
        return encounterSummaries;
    }
}
