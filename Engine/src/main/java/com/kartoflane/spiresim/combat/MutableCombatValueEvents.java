package com.kartoflane.spiresim.combat;

public enum MutableCombatValueEvents implements MutableCombatValueEvent {
    ENTITY_INCOMING_DAMAGE_FLAT("ENTITY_INCOMING_DAMAGE_FLAT"),
    ENTITY_INCOMING_DAMAGE_PERCENT("ENTITY_INCOMING_DAMAGE_PERCENT"),
    ENTITY_OUTGOING_DAMAGE_FLAT("ENTITY_OUTGOING_DAMAGE_FLAT"),
    ENTITY_OUTGOING_DAMAGE_PERCENT("ENTITY_OUTGOING_DAMAGE_PERCENT"),
    ENTITY_INCOMING_ARMOR_FLAT("ENTITY_INCOMING_ARMOR_FLAT"),
    ENTITY_INCOMING_ARMOR_PERCENT("ENTITY_INCOMING_ARMOR_PERCENT"),
    ENTITY_OUTGOING_ARMOR_FLAT("ENTITY_OUTGOING_ARMOR_FLAT"),
    ENTITY_OUTGOING_ARMOR_PERCENT("ENTITY_OUTGOING_ARMOR_FLAT"),
    ENTITY_INCOMING_HEAL_FLAT("ENTITY_INCOMING_HEAL_FLAT"),
    ENTITY_INCOMING_HEAL_PERCENT("ENTITY_INCOMING_HEAL_PERCENT"),
    ENTITY_OUTGOING_HEAL_FLAT("ENTITY_OUTGOING_HEAL_FLAT"),
    ENTITY_OUTGOING_HEAL_PERCENT("ENTITY_OUTGOING_HEAL_PERCENT");

    private String identifier;


    MutableCombatValueEvents(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public static MutableCombatValueEvents identifierFor(String identifier) {
        return MutableCombatValueEvents.valueOf(identifier);
    }
}
