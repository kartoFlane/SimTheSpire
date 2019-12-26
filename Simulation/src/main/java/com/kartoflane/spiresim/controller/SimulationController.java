package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.content.template.entity.enemy.CultistEntityTemplate;
import com.kartoflane.spiresim.content.template.entity.player.WarriorEntityTemplate;
import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.report.PlaythroughSummary;
import com.kartoflane.spiresim.state.EncounterState;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.SimulationState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.util.RandomExt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationController implements StateController<SimulationState> {

    private final SimulationState state;


    public SimulationController(SimulationState state) {
        this.state = state;
    }

    public SimulationState getState() {
        return this.state;
    }

    public void start() {
        RandomExt random = new RandomExt();

        for (int i = 0; i < 100; ++i) {
            GameController gameController = buildNewGame(random);

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
        System.out.printf("Median encounters: %s%n", computeMedian(
                state.getPlaythroughSummaryList().stream()
                        .map(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                        .sorted()
                        .collect(Collectors.toList())
        ));

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
        System.out.printf("Median turns: %.2f%n", computeMedian(
                state.getPlaythroughSummaryList().stream()
                        .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                        .map(EncounterSummary::getTurns)
                        .sorted()
                        .collect(Collectors.toList())
        ));
    }

    private GameController buildNewGame(RandomExt random) {
        GameController gameController = new GameController(new GameState(random));
        EntityState playerEntity = StateFactory.build(gameController, WarriorEntityTemplate.getInstance());
        gameController.initialize(playerEntity);

        return gameController;
    }

    private EncounterState buildNewEncounter(GameController gameController) {
        List<EntityState> enemyEntities = Arrays.asList(
                StateFactory.build(gameController, CultistEntityTemplate.getInstance())
        );

        return new EncounterState(enemyEntities);
    }

    private double computeMedian(List<Integer> sortedSamplesList) {
        final int halfIndex = sortedSamplesList.size() / 2;

        if (sortedSamplesList.size() % 2 == 1) {
            return sortedSamplesList.get(halfIndex);
        } else {
            int lower = sortedSamplesList.get(halfIndex - 1);
            int upper = sortedSamplesList.get(halfIndex);
            return (lower + upper) / 2.0;
        }
    }
}
