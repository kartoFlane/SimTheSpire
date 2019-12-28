package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.content.template.card.base.SimpleAttackCardTemplate;
import com.kartoflane.spiresim.controller.GameController;

public class BiteCardTemplate extends SimpleAttackCardTemplate<SimpleAttackCardState> {

    private static BiteCardTemplate INSTANCE;


    public static BiteCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BiteCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Bite";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    @Override
    public int getAttackValue(GameController gameController) {
        return gameController.getState().getRandom().nextInt(5, 7);
    }

    @Override
    public Class<? extends SimpleAttackCardState> getStateType() {
        return SimpleAttackCardState.class;
    }
}
