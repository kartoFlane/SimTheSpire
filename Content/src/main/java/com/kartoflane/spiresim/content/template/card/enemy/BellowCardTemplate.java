package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.BellowCardState;
import com.kartoflane.spiresim.content.state.effect.common.StrengthEffectState;
import com.kartoflane.spiresim.content.template.card.base.TargetNoneCardTemplate;
import com.kartoflane.spiresim.content.template.effect.common.StrengthEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

@DeriveState
public class BellowCardTemplate extends TargetNoneCardTemplate<BellowCardState> {

    @Override
    public CardType getCardType() {
        return CardType.SKILL;
    }

    @Override
    public String getName() {
        return "Bellow";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    public int getStartingStacks(GameController gameController) {
        return 3;
    }

    public int getDefenseValue(GameController gameController) {
        return 6;
    }

    @Override
    public Class<BellowCardState> getStateType() {
        return BellowCardState.class;
    }

    public CardPileType onPlay(GameController gameController, EntityController caster, BellowCardState state) {
        StrengthEffectState effectState = StateFactory.build(gameController, gameController.getTemplateInstance(StrengthEffectTemplate.class));
        effectState.setStacks(state.getStartingStacks());
        caster.applyEffect(gameController, effectState);

        caster.applyArmor(gameController, caster.buildOutgoingArmorValue(gameController, getDefenseValue(gameController)));

        return CardPileType.DISCARD;
    }
}
