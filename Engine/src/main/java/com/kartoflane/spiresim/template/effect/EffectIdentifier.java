package com.kartoflane.spiresim.template.effect;

public interface EffectIdentifier {
    String getEffectIdentifier();


    enum EffectIdentifiers implements EffectIdentifier {
        ARMOR_RECEIVED_REDUCE("ARMOR_RECEIVED_REDUCE"),
        ARMOR_GRANTED_REDUCE("ARMOR_GRANTED_REDUCE"),
        DAMAGE_RECEIVED_INCREASE("DAMAGE_RECEIVED_INCREASE"),
        DAMAGE_DEALT_INCREASE("DAMAGE_DEALT_INCREASE");


        private String identifier;


        EffectIdentifiers(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String getEffectIdentifier() {
            return this.identifier;
        }

        public static EffectIdentifiers identifierFor(String identifier) {
            return EffectIdentifiers.valueOf(identifier);
        }
    }
}
