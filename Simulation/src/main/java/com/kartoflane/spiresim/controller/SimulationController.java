package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.content.template.encounter.act1.CultistEncounterTemplate;
import com.kartoflane.spiresim.content.template.encounter.act1.JawWormEncounterTemplate;
import com.kartoflane.spiresim.content.template.encounter.act1.Louses2EncounterTemplate;
import com.kartoflane.spiresim.content.template.entity.player.WarriorEntityTemplate;
import com.kartoflane.spiresim.report.EncounterSummary;
import com.kartoflane.spiresim.report.PlaythroughSummary;
import com.kartoflane.spiresim.report.SampleStatistics;
import com.kartoflane.spiresim.state.GameState;
import com.kartoflane.spiresim.state.SimulationState;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.encounter.EncounterState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.TemplateManager;
import com.kartoflane.spiresim.template.encounter.EncounterTemplate;
import com.kartoflane.spiresim.util.RandomExt;

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
        TemplateManager templateManager = new TemplateManager();
        RandomExt random = new RandomExt();

        for (int i = 0; i < 100; ++i) {
            GameController gameController = buildNewGame(templateManager, random);

            PlaythroughSummary summary = gameController.simulateGame(this::buildNewEncounter);

            state.getPlaythroughSummaryList().add(summary);
        }

        System.out.println("\n\n==============================================\n");

        SampleStatistics encountersStatistics = SampleStatistics.ofIntegers(
                state.getPlaythroughSummaryList().stream()
                        .map(playthroughSummary -> playthroughSummary.getEncounterSummaries().size())
                        .collect(Collectors.toList())
        );
        SampleStatistics turnStatistics = SampleStatistics.ofIntegers(
                state.getPlaythroughSummaryList().stream()
                        .flatMap(playthroughSummary -> playthroughSummary.getEncounterSummaries().stream())
                        .map(EncounterSummary::getTurns)
                        .collect(Collectors.toList())
        );

        System.out.printf("Total encounters: %s%n", encountersStatistics.getCount());
        System.out.printf("Min encounters: %.0f%n", encountersStatistics.getMin());
        System.out.printf("Max encounters: %.0f%n", encountersStatistics.getMax());
        System.out.printf("Average encounters: %.2f%n", encountersStatistics.getAverage());
        System.out.printf("Median encounters: %.2f%n", encountersStatistics.getMedian());
        System.out.printf("Std. deviation: %.2f%n", encountersStatistics.getStandardDeviation());

        System.out.println();
        System.out.printf("Min turns: %.0f%n", turnStatistics.getMin());
        System.out.printf("Max turns: %.0f%n", turnStatistics.getMax());
        System.out.printf("Average turns: %.2f%n", turnStatistics.getAverage());
        System.out.printf("Median turns: %.2f%n", turnStatistics.getMedian());
        System.out.printf("Std. deviation: %.2f%n", turnStatistics.getStandardDeviation());
    }

    private GameController buildNewGame(TemplateManager templateManager, RandomExt random) {
        GameController gameController = new GameController(new GameState(templateManager, random));
        EntityState playerEntity = StateFactory.build(gameController, gameController.getTemplateInstance(WarriorEntityTemplate.class));
        gameController.initialize(playerEntity);

        return gameController;
    }

    private EncounterState buildNewEncounter(GameController gameController) {
        EncounterTemplate<EncounterState> randomEncounterTemplate = gameController.getState().getRandom()
                .randomElement(
                        gameController.getTemplateInstance(CultistEncounterTemplate.class),
                        gameController.getTemplateInstance(JawWormEncounterTemplate.class),
                        gameController.getTemplateInstance(Louses2EncounterTemplate.class)
                );

        return StateFactory.build(gameController, randomEncounterTemplate);
    }
}
