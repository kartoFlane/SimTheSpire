package com.kartoflane.spiresim.content.template.effect.common;

import com.kartoflane.spiresim.template.effect.EffectIdentifier;

enum CommonEffectIdentifiers implements EffectIdentifier {
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


    CommonEffectIdentifiers(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public static CommonEffectIdentifiers identifierFor(String identifier) {
        return CommonEffectIdentifiers.valueOf(identifier);
    }
}