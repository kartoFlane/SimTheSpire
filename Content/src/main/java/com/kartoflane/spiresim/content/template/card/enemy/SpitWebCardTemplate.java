package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.SpitWebCardState;
import com.kartoflane.spiresim.content.state.effect.common.WeakEffectState;
import com.kartoflane.spiresim.content.template.card.base.TargetSingleCardTemplate;
import com.kartoflane.spiresim.content.template.effect.common.WeakEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

@DeriveState
public class SpitWebCardTemplate extends TargetSingleCardTemplate<SpitWebCardState> {

    private static SpitWebCardTemplate INSTANCE;


    public static SpitWebCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpitWebCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public CardType getCardType() {
        return CardType.ATTACK;
    }

    @Override
    public String getName() {
        return "Spit Web";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    public int getStartingStacks(GameController gameController) {
        return 2;
    }

    @Override
    public Class<SpitWebCardState> getStateType() {
        return SpitWebCardState.class;
    }

    @Override
    public CardPileType onPlay(GameController gameController, EntityController caster, EntityController target, SpitWebCardState cardState) {
        WeakEffectState effectState = StateFactory.build(gameController, WeakEffectTemplate.getInstance());
        effectState.setStacks(cardState.getStartingStacks());
        target.applyEffect(gameController, effectState);

        return CardPileType.DISCARD;
    }
}
