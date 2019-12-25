package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.state.card.base.SimpleAttackCardState;
import com.kartoflane.spiresim.content.template.card.base.SimpleAttackCardTemplate;

public class DarkStrikeCardTemplate extends SimpleAttackCardTemplate<SimpleAttackCardState> {

    private static DarkStrikeCardTemplate INSTANCE;


    public static DarkStrikeCardTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DarkStrikeCardTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<SimpleAttackCardState> getStateType() {
        return SimpleAttackCardState.class;
    }

    @Override
    public String getName() {
        return "Dark Strike";
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
