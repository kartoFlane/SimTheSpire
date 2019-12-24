package com.kartoflane.spiresim.content.template.effect;

import com.kartoflane.spiresim.template.effect.EffectIdentifier;

enum EffectIdentifiers implements EffectIdentifier {
    INCOMING_ARMOR_INCREASE_FLAT("INCOMING_ARMOR_INCREASE_FLAT"),
    INCOMING_ARMOR_INCREASE_PERCENT("INCOMING_ARMOR_INCREASE_PERCENT"),
    INCOMING_ARMOR_DECREASE_FLAT("INCOMING_ARMOR_DECREASE_FLAT"),
    INCOMING_ARMOR_DECREASE_PERCENT("INCOMING_ARMOR_DECREASE_PERCENT"),
    INCOMING_DAMAGE_INCREASE_FLAT("INCOMING_DAMAGE_INCREASE_FLAT"),
    INCOMING_DAMAGE_INCREASE_PERCENT("INCOMING_DAMAGE_INCREASE_PERCENT"),
    OUTGOING_ARMOR_DECREASE_FLAT("OUTGOING_ARMOR_DECREASE_FLAT"),
    OUTGOING_ARMOR_DECREASE_PERCENT("OUTGOING_ARMOR_DECREASE_PERCENT"),
    OUTGOING_DAMAGE_DECREASE_FLAT("OUTGOING_DAMAGE_DECREASE_FLAT"),
    OUTGOING_DAMAGE_DECREASE_PERCENT("OUTGOING_DAMAGE_DECREASE_PERCENT"),
    OUTGOING_DAMAGE_INCREASE_FLAT("OUTGOING_DAMAGE_INCREASE_FLAT"),
    OUTGOING_DAMAGE_INCREASE_PERCENT("OUTGOING_DAMAGE_INCREASE_PERCENT");

    private String identifier;


    EffectIdentifiers(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public static EffectIdentifiers identifierFor(String identifier) {
        return EffectIdentifiers.valueOf(identifier);
    }
}