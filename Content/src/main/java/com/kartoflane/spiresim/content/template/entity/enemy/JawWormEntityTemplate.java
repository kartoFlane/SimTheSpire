package com.kartoflane.spiresim.content.template.entity.enemy;

import com.kartoflane.spiresim.content.state.entity.base.SimpleEntityState;
import com.kartoflane.spiresim.content.template.card.enemy.BellowCardTemplate;
import com.kartoflane.spiresim.content.template.card.enemy.ChompCardTemplate;
import com.kartoflane.spiresim.content.template.card.enemy.ThrashCardTemplate;
import com.kartoflane.spiresim.content.template.entity.base.EnemyEntityTemplate;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

import java.util.Arrays;
import java.util.List;

public class JawWormEntityTemplate extends EnemyEntityTemplate<SimpleEntityState> {

    @Override
    public Class<SimpleEntityState> getStateType() {
        return SimpleEntityState.class;
    }

    @Override
    public String getName() {
        return "Jaw Worm";
    }

    @Override
    public int getHealth(GameController gameController) {
        return gameController.getState().getRandom().nextInt(40, 44);
    }

    @Override
    public List<? extends CardTemplate<? extends CardState>> getStartingDeck(GameController gameController) {
        return Arrays.asList(
                gameController.getTemplateInstance(ThrashCardTemplate.class),
                gameController.getTemplateInstance(BellowCardTemplate.class),
                gameController.getTemplateInstance(ChompCardTemplate.class)
        );
    }
}
