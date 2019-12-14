package com.kartoflane.spiresim.state;

import com.kartoflane.spiresim.report.PlaythroughSummary;

import java.util.ArrayList;
import java.util.List;

public class SimulationState {

    private List<PlaythroughSummary> playthroughSummaryList = new ArrayList<>();


    public List<PlaythroughSummary> getPlaythroughSummaryList() {
        return playthroughSummaryList;
    }

    public void setPlaythroughSummaryList(List<PlaythroughSummary> playthroughSummaryList) {
        this.playthroughSummaryList = playthroughSummaryList;
    }
}
