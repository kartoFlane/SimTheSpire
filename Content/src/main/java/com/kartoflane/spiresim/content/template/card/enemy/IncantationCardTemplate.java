package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.IncantationCardState;
import com.kartoflane.spiresim.content.state.effect.enemy.RitualEffectState;
import com.kartoflane.spiresim.content.template.effect.enemy.RitualEffectTemplate;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;

import java.util.List;

@DeriveState
public class IncantationCardTemplate extends CardTemplate<IncantationCardState> {

    private static IncantationCardTemplate INSTANCE;


    public static IncantationCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IncantationCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<IncantationCardState> getStateType() {
        return IncantationCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.NONE;
    }

    @Override
    public String getName() {
        return "Incantation";
    }

    @Override
    public CardType getCardType() {
        return CardType.POWER;
    }

    @Override
    public int getCost() {
        return 1;
    }

    public int getStartingStacks() {
        return 3;
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {

    }

    @Override
    public CardPileType onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, IncantationCardState cardState) {
        RitualEffectState effectState = StateFactory.build(RitualEffectTemplate.getInstance());
        effectState.setStacks(cardState.getStartingStacks());
        caster.applyEffect(encounterController, effectState);

        return CardPileType.USED_POWER;
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, IncantationCardState cardState) {

    }
}
