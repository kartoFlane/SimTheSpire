package com.kartoflane.spiresim.template.entity.player;

import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.state.EntityState;
import com.kartoflane.spiresim.template.card.BashCardTemplate;
import com.kartoflane.spiresim.template.card.CardTemplate;
import com.kartoflane.spiresim.template.card.DefendCardTemplate;
import com.kartoflane.spiresim.template.card.StrikeCardTemplate;
import com.kartoflane.spiresim.template.entity.PlayerEntityTemplate;

import java.util.Arrays;
import java.util.List;

public class WarriorEntityTemplate extends PlayerEntityTemplate<EntityState> {

    private static WarriorEntityTemplate INSTANCE;


    public static WarriorEntityTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WarriorEntityTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<EntityState> getStateType() {
        return EntityState.class;
    }

    @Override
    public String getName() {
        return "Ironclad";
    }

    @Override
    public int getHealth() {
        return 60;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck() {
        return Arrays.asList(
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                BashCardTemplate.getInstance()
        );
    }
}
