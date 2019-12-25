package com.kartoflane.spiresim.content.template.card.player.warrior;

import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.content.template.card.base.SimpleAttackCardTemplate;

public class StrikeCardTemplate extends SimpleAttackCardTemplate<SimpleAttackCardState> {

    private static StrikeCardTemplate INSTANCE;


    public static StrikeCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StrikeCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<SimpleAttackCardState> getStateType() {
        return SimpleAttackCardState.class;
    }

    @Override
    public String getName() {
        return "Strike";
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public int getAttackValue() {
        return 6;
    }
}
