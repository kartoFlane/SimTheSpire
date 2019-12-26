package com.kartoflane.spiresim.test.template.effect;

import com.kartoflane.spiresim.combat.MutableCombatValue;
import com.kartoflane.spiresim.combat.MutableCombatValueEvent;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.template.effect.EffectIdentifier;
import com.kartoflane.spiresim.template.effect.EffectTemplate;
import com.kartoflane.spiresim.template.effect.EffectUpdateEvent;
import com.kartoflane.spiresim.test.entity.effect.TestEffectState;

public class TestEffectTemplate extends EffectTemplate<TestEffectState> {
    private static TestEffectTemplate INSTANCE;


    public static TestEffectTemplate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestEffectTemplate();
        }
        return INSTANCE;
    }

    @Override
    public EffectIdentifier getEffectIdentifier() {
        return new EffectIdentifier() {
            @Override
            public String getIdentifier() {
                return "DUMMY_EFFECT";
            }
        };
    }

    @Override
    public String getName() {
        return "Dummy Effect";
    }

    @Override
    public void onApply(GameController gameController, EntityController target, TestEffectState effectState, TestEffectState newInstance) {
    }

    @Override
    public void onRemove(GameController gameController, EntityController target, TestEffectState effectState) {
    }

    @Override
    public void onUpdate(GameController gameController, EntityController target, TestEffectState effectState, EffectUpdateEvent updateEvent) {
    }

    @Override
    public void preprocessCombatValue(
            GameController gameController,
            EntityController target,
            TestEffectState effectState,
            MutableCombatValue mutableCombatValue,
            MutableCombatValueEvent updateEvent
    ) {
    }

    @Override
    public Class<TestEffectState> getStateType() {
        return TestEffectState.class;
    }
}
