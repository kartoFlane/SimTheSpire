package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.player.warrior.BashCardState;
import com.kartoflane.spiresim.content.state.effect.common.VulnerableEffectState;
import com.kartoflane.spiresim.content.template.card.base.SimpleAttackCardTemplate;
import com.kartoflane.spiresim.content.template.effect.common.VulnerableEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.CardPileType;

@DeriveState
public class BashCardTemplate extends SimpleAttackCardTemplate<BashCardState> {

    @Override
    public Class<BashCardState> getStateType() {
        return BashCardState.class;
    }

    @Override
    public String getName() {
        return "Bash";
    }

    @Override
    public int getCost(GameController gameController) {
        return 2;
    }

    @Override
    public int getAttackValue(GameController gameController) {
        return 8;
    }

    public int getStartingStacks(GameController gameController) {
        return 1;
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, EntityController target, BashCardState cardState) {
        VulnerableEffectState effectState = StateFactory.build(gameController, gameController.getTemplateInstance(VulnerableEffectTemplate.class));
        effectState.setStacks(cardState.getStartingStacks());
        target.applyEffect(gameController, effectState);

        return CardPileType.DISCARD;
    }
}
