package com.kartoflane.spiresim.content.template.card.base;

import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingType;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.template.card.CardTemplate;

public abstract class TargetAllCardTemplate<S extends CardState> extends CardTemplate<S> {

    @Override
    public TargetingType getTargetingType() {
        return TargetingType.ALL;
    }

    @Override
    public void onDiscard(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onExhaust(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onRetain(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onTurnStart(GameController gameController, EntityController caster, S state) {
    }

    @Override
    public void onTurnEnd(GameController gameController, EntityController caster, S state) {
    }
}
