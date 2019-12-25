package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

public abstract class PowerCardTemplate<S extends CardState> extends CardTemplate<S> {

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public CardType getCardType() {
        return CardType.POWER;
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public final CardPileType onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, S state) {
        onPlay(encounterController, caster, state);

        return CardPileType.USED_POWER;
    }

    public void onPlay(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, S state) {
    }
}
