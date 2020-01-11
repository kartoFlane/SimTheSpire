package com.kartoflane.spiresim.content.template.entity.enemy;

import com.kartoflane.spiresim.content.state.entity.base.SimpleEntityState;
import com.kartoflane.spiresim.content.template.card.player.warrior.DefendCardTemplate;
import com.kartoflane.spiresim.content.template.card.player.warrior.StrikeCardTemplate;
import com.kartoflane.spiresim.content.template.entity.base.EnemyEntityTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.Arrays;
import java.util.List;

public class SlaverEntityTemplate extends EnemyEntityTemplate<SimpleEntityState> {

    @Override
    public Class<SimpleEntityState> getStateType() {
        return SimpleEntityState.class;
    }

    @Override
    public String getName() {
        return "Slaver";
    }

    @Override
    public int getHealth(GameController gameController) {
        return 26;
    }

    @Override
    public List<CardTemplate<? extends CardState>> getStartingDeck(GameController gameController) {
        return Arrays.asList(
                gameController.getTemplateInstance(StrikeCardTemplate.class),
                gameController.getTemplateInstance(StrikeCardTemplate.class),
                gameController.getTemplateInstance(DefendCardTemplate.class)
        );
    }
}
