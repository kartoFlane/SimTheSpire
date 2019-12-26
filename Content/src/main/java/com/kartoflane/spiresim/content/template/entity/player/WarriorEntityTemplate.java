package com.kartoflane.spiresim.content.template.entity.player;

import com.kartoflane.spiresim.content.state.entity.base.SimpleEntityState;
import com.kartoflane.spiresim.content.template.card.player.warrior.BashCardTemplate;
import com.kartoflane.spiresim.content.template.card.player.warrior.DefendCardTemplate;
import com.kartoflane.spiresim.content.template.card.player.warrior.StrikeCardTemplate;
import com.kartoflane.spiresim.content.template.entity.base.PlayerEntityTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.Arrays;
import java.util.List;

public class WarriorEntityTemplate extends PlayerEntityTemplate<SimpleEntityState> {

    private static WarriorEntityTemplate INSTANCE;


    public static WarriorEntityTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WarriorEntityTemplate();
        }

        return INSTANCE;
    }

    @Override
    public Class<SimpleEntityState> getStateType() {
        return SimpleEntityState.class;
    }

    @Override
    public String getName() {
        return "Ironclad";
    }

    @Override
    public int getHealth(GameController gameController) {
        return 80;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck() {
        return Arrays.asList(
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                StrikeCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                DefendCardTemplate.getInstance(),
                BashCardTemplate.getInstance()
        );
    }
}
