package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.report.PlaythroughSummary;
import com.kartoflane.spiresim.state.*;
import com.kartoflane.spiresim.template.entity.enemy.SlaverEntityTemplate;
import com.kartoflane.spiresim.template.entity.player.WarriorEntityTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationController implements StateController<SimulationState> {

    private final SimulationState state;


    public SimulationController(SimulationState state) {
        this.state = state;
    }

    public SimulationState getState() {
        return this.state;
    }

    public void start() {
        for (int i = 0; i < 100; ++i) {
            GameState gameState = buildNewGame(new Random());
            GameController gameController = new GameController(gameState);

            PlaythroughSummary summary = gameController.simulateGame(this::buildNewEncounter);

            state.getPlaythroughSummaryList().add(summary);
        }

        System.out.println("\n\n==============================================\n");

        int encountersTotal = state.getPlaythroughSummaryList().stream()
                .mapToInt(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                .sum();
        System.out.printf("Total encounters: %s%n", encountersTotal);
        System.out.printf("Min encounters: %s%n", state.getPlaythroughSummaryList().stream()
                .mapToInt(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                .min().orElse(0));
        System.out.printf("Max encounters: %s%n", state.getPlaythroughSummaryList().stream()
                .mapToInt(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                .max().orElse(0));
        System.out.printf("Average encounters: %.2f%n", state.getPlaythroughSummaryList().stream()
                .mapToInt(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                .average().orElse(0));
        System.out.printf("Median encounters: %s%n", state.getPlaythroughSummaryList().stream()
                .mapToInt(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                .sorted().skip(state.getPlaythroughSummaryList().size() / 2)
                .findFirst().orElse(0));

        System.out.println();
        System.out.printf("Min turns: %s%n", state.getPlaythroughSummaryList().stream()
                .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                .mapToInt(EncounterSummary::getTurns)
                .min().orElse(0));
        System.out.printf("Max turns: %s%n", state.getPlaythroughSummaryList().stream()
                .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                .mapToInt(EncounterSummary::getTurns)
                .max().orElse(0));
        System.out.printf("Average turns: %.2f%n", state.getPlaythroughSummaryList().stream()
                .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                .mapToInt(EncounterSummary::getTurns)
                .average().orElse(0));
        System.out.printf("Median turns: %s%n", state.getPlaythroughSummaryList().stream()
                .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                .mapToInt(EncounterSummary::getTurns)
                .sorted().skip(encountersTotal / 2)
                .findFirst().orElse(0));
    }

    private GameState buildNewGame(Random random) {
        EntityState playerEntity = StateFactory.build(WarriorEntityTemplate.getInstance());

        return new GameState(random, playerEntity);
    }

    private EncounterState buildNewEncounter() {
        List<EntityState> enemyEntities = Arrays.asList(
                StateFactory.build(SlaverEntityTemplate.getInstance())
        );

        return new EncounterState(enemyEntities);
    }
}
