package com.kartoflane.spiresim.template.card;

import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.card.AmplifyCardState;
import com.kartoflane.spiresim.template.effect.VulnerableEffectTemplate;

import java.util.List;

public class BashCardTemplate implements CardTemplate<AmplifyCardState> {

    private static BashCardTemplate INSTANCE;


    public static BashCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BashCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<AmplifyCardState> getStateType() {
        return AmplifyCardState.class;
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

    @Override
    public void onDiscard(EncounterController encounterController, EntityController caster, AmplifyCardState cardState) {

    }

    @Override
    public void onExhaust(EncounterController encounterController, EntityController caster, AmplifyCardState cardState) {

    }

    @Override
    public void onRetain(EncounterController encounterController, EntityController caster, AmplifyCardState cardState) {

    }

    @Override
    public void onPlay(EncounterController encounterController, EntityController caster, List<EntityController> targets, AmplifyCardState cardState) {
        EntityController target = targets.get(0);
        System.out.printf("%s uses %s on %s!%n", caster.getState().getName(), cardState.getName(), target.getState().getName());

        target.applyDamage(encounterController, caster.buildOutgoingAttackValue(encounterController, cardState.getAttackValue()));
        target.applyEffect(encounterController, StateFactory.build(VulnerableEffectTemplate.getInstance()));
    }

    @Override
    public void onTurnStart(EncounterController encounterController, EntityController caster, AmplifyCardState cardState) {

    }

    @Override
    public void onTurnEnd(EncounterController encounterController, EntityController caster, AmplifyCardState cardState) {

    }
}
