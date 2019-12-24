package com.kartoflane.spiresim.template.effect;

public enum StandardEffectUpdateEvents implements EffectUpdateEvent {
    TURN_START("TURN_START"),
    TURN_END("TURN_END"),
    CARD_DRAW("CARD_DRAW"),
    CARD_DISCARD("CARD_DISCARD"),
    CARD_PLAY("CARD_PLAY"),
    ENTITY_INCOMING_DAMAGE("ENTITY_INCOMING_DAMAGE"),
    ENTITY_OUTGOING_DAMAGE("ENTITY_OUTGOING_DAMAGE"),
    ENTITY_INCOMING_ARMOR("ENTITY_INCOMING_ARMOR"),
    ENTITY_OUTGOING_ARMOR("ENTITY_OUTGOING_ARMOR"),
    ENTITY_INCOMING_HEAL("ENTITY_INCOMING_HEAL"),
    ENTITY_OUTGOING_HEAL("ENTITY_OUTGOING_HEAL");

    private String identifier;


    StandardEffectUpdateEvents(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public static StandardEffectUpdateEvents identifierFor(String identifier) {
        return StandardEffectUpdateEvents.valueOf(identifier);
    }
}
