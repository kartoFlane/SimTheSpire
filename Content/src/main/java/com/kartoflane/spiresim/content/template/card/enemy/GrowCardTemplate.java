package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.annotation.DeriveState;
import com.kartoflane.spiresim.content.state.card.enemy.GrowCardState;
import com.kartoflane.spiresim.content.state.effect.common.StrengthEffectState;
import com.kartoflane.spiresim.content.template.card.base.TargetNoneCardTemplate;
import com.kartoflane.spiresim.content.template.effect.common.StrengthEffectTemplate;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.template.card.CardType;

@DeriveState
public class GrowCardTemplate extends TargetNoneCardTemplate<GrowCardState> {

    private static GrowCardTemplate INSTANCE;


    public static GrowCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GrowCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public CardType getCardType() {
        return CardType.SKILL;
    }

    @Override
    public String getName() {
        return "Grow";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    public int getStartingStacks(GameController gameController) {
        return 3;
    }

    @Override
    public Class<GrowCardState> getStateType() {
        return GrowCardState.class;
    }

    public CardPileType onPlay(GameController gameController, EntityController caster, GrowCardState state) {
        StrengthEffectState effectState = StateFactory.build(gameController, StrengthEffectTemplate.getInstance());
        effectState.setStacks(state.getStartingStacks());
        caster.applyEffect(gameController, effectState);

        return CardPileType.DISCARD;
    }
}
