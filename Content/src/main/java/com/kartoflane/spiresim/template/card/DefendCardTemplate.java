package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.card.DefendCardState;
import com.kartoflane.spiresim.template.CardTemplate;

import java.util.List;

public class DefendCardTemplate implements CardTemplate<DefendCardState> {

    private static DefendCardTemplate INSTANCE;


    public static DefendCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DefendCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<DefendCardState> getStateType() {
        return DefendCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public int getCost() {
        return 1;
    }

    public int getDefenseValue() {
        return 6;
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, DefendCardState cardState) {
        System.out.printf("%s uses %s!%n", caster.getState().getName(), cardState.getName());
        caster.applyArmor(encounterController, caster.buildArmorValue(encounterController, cardState.getDefenseValue()));
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }
}
