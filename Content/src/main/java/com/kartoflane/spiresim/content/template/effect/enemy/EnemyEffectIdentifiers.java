package com.kartoflane.spiresim.content.template.effect.enemy;

import com.kartoflane.spiresim.template.effect.EffectIdentifier;

enum EnemyEffectIdentifiers implements EffectIdentifier {
    POWER_RITUAL("POWER_RITUAL");

    private String identifier;


    EnemyEffectIdentifiers(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public static EnemyEffectIdentifiers identifierFor(String identifier) {
        return EnemyEffectIdentifiers.valueOf(identifier);
    }
}