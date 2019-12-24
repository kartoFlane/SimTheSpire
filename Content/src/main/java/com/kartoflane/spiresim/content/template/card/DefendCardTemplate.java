package com.kartoflane.spiresim.content.template.card;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.content.state.card.DefendCardState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public class DefendCardTemplate extends CardTemplate<DefendCardState> {

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

    @Override
    public CardType getCardType() {
        return CardType.SKILL;
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
        caster.applyArmor(encounterController, caster.buildOutgoingArmorValue(encounterController, cardState.getDefenseValue()));
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, DefendCardState cardState) {

    }
}
