package com.kartoflane.spiresim.content.template.card.enemy;

import com.kartoflane.spiresim.content.template.card.base.SimpleAttackAndDefendCardTemplate;
import com.kartoflane.spiresim.controller.GameController;

public class ThrashCardTemplate extends SimpleAttackAndDefendCardTemplate {

    @Override
    public String getName() {
        return "Thrash";
    }

    @Override
    public int getCost(GameController gameController) {
        return 1;
    }

    @Override
    public int getAttackValue(GameController gameController) {
        return 7;
    }

    public int getDefenseValue(GameController gameController) {
        return 5;
    }
}
