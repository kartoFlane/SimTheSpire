package com.kartoflane.spiresim.content.template.entity.enemy;

import com.kartoflane.spiresim.content.template.card.enemy.DarkStrikeCardTemplate;
import com.kartoflane.spiresim.content.template.card.enemy.IncantationCardTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.entity.EnemyEntityTemplate;

import java.util.Arrays;
import java.util.List;

public class CultistEntityTemplate extends EnemyEntityTemplate<EntityState> {

    private static CultistEntityTemplate INSTANCE;


    public static CultistEntityTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CultistEntityTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<EntityState> getStateType() {
        return EntityState.class;
    }

    @Override
    public String getName() {
        return "Cultist";
    }

    @Override
    public int getHealth(GameController gameController) {
        return gameController.getState().getRandom().nextInt(48, 54);
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck() {
        return Arrays.asList(
                IncantationCardTemplate.getInstance(),
                DarkStrikeCardTemplate.getInstance()
        );
    }
}
