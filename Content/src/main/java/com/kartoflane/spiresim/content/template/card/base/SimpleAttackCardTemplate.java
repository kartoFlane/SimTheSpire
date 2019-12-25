package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public abstract class SimpleAttackCardTemplate<S extends SimpleAttackCardState> extends CardTemplate<S> {

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.SINGLE;
    }

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
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
        EntityController target = targets.get(0);
        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, state.getAttackValue()));

        onPlay(encounterController, caster, target, state);

        return CardPileType.DISCARD;
    }

    public void onPlay(EncounterController encounterController, EntityController caster, EntityController target, S state) {
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, S state) {
    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, S state) {
    }

    public abstract int getAttackValue();
}
