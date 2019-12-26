package com.kartoflane.spiresim.test.entity.effect;

import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.state.effect.EffectState;
import com.kartoflane.spiresim.test.template.effect.TestEffectTemplate;

public class TestEffectState extends EffectState {
    public TestEffectState(GameController gameController, TestEffectTemplate template) {
        super(gameController, template);
    }
}
