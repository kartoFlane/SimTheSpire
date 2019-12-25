package com.kartoflane.spiresim.content.template.entity.enemy;

import com.kartoflane.spiresim.content.template.card.player.warrior.StrikeCardTemplate;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.content.template.card.player.warrior.DefendCardTemplate;
import com.kartoflane.spiresim.template.entity.EnemyEntityTemplate;

import java.util.Arrays;
import java.util.List;

public class SlaverEntityTemplate extends EnemyEntityTemplate<EntityState> {

    private static SlaverEntityTemplate INSTANCE;


    public static SlaverEntityTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlaverEntityTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<EntityState> getStateType() {
        return EntityState.class;
    }

    @Override
    public String getName() {
        return "Slaver";
    }

    @Override
    public int getHealth() {
        return 26;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck() {
        return Arrays.asList(
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                DefendCardTemplate.getInstance()
        );
    }
}
