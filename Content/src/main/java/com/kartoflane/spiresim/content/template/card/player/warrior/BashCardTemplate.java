package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.player.warrior.BashCardState;
import com.kartoflane.spiresim.content.state.effect.common.VulnerableEffectState;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.CardType;
import com.kartoflane.spiresim.content.template.effect.common.VulnerableEffectTemplate;

import java.util.List;

@DeriveState
public class BashCardTemplate extends CardTemplate<BashCardState> {

    private static BashCardTemplate INSTANCE;


    public static BashCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BashCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<BashCardState> getStateType() {
        return BashCardState.class;
    }

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.SINGLE;
    }

    @Override
    public String getName() {
        return "Bash";
    }

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
    }

    @Override
    public int getCost() {
        return 2;
    }

    public int getAttackValue() {
        return 8;
    }

    public int getStartingStacks() {
        return 1;
    }

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, BashCardState cardState) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, BashCardState cardState) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, BashCardState cardState) {

    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, BashCardState cardState) {
        EntityController target = targets.get(0);

        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, cardState.getAttackValue()));

        VulnerableEffectState effectState = StateFactory.build(VulnerableEffectTemplate.getInstance());
        effectState.setStacks(cardState.getStartingStacks());
        target.applyEffect(encounterController, effectState);
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, BashCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, BashCardState cardState) {

    }
}
