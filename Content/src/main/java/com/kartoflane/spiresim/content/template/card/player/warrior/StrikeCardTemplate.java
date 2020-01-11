package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.content.template.card.base.SimpleAttackCardTemplate;
import com.kartoflane.spiresim.controller.GameController;

public class StrikeCardTemplate extends SimpleAttackCardTemplate<SimpleAttackCardState> {

    @Override
    public Class<SimpleAttackCardState> getStateType() {
        return SimpleAttackCardState.class;
    }

    @Override
    public String getName() {
        return "Strike";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    @Override
    public int getAttackValue(GameController gameController) {
        return 6;
    }
}
