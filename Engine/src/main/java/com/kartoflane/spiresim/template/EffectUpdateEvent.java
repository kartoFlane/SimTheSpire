package com.kartoflane.spiresim.template;

public interface EffectUpdateEvent {
    String getEventIdentifier();


    enum StandardEffectUpdateEvents implements EffectUpdateEvent {
        TURN_START("TURN_START"),
        TURN_END("TURN_END"),
        CARD_DRAW("CARD_DRAW"),
        CARD_DISCARD("CARD_DISCARD"),
        CARD_PLAY("CARD_PLAY"),
        ENTITY_INCOMING_DAMAGE("ENTITY_INCOMING_DAMAGE"),
        ENTITY_OUTGOING_DAMAGE("ENTITY_OUTGOING_DAMAGE"),
        ENTITY_ARMOR("ENTITY_ARMOR");

        private String identifier;


        StandardEffectUpdateEvents(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String getEventIdentifier() {
            return this.identifier;
        }

        public static StandardEffectUpdateEvents identifierFor(String identifier) {
            return StandardEffectUpdateEvents.valueOf(identifier);
        }
    }
}
