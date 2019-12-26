package com.kartoflane.spiresim.test.entity.card;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.test.template.card.TestCardTemplate;

public class TestCardState extends CardState {
    public TestCardState(GameController gameController, TestCardTemplate template) {
        super(gameController, template);
    }
}
