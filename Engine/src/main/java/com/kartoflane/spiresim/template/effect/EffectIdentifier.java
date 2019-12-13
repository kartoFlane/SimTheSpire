package com.kartoflane.spiresim.template.effect;

public interface EffectIdentifier {
    String getEffectIdentifier();


    enum EffectIdentifiers implements EffectIdentifier {
        ARMOR_RECEIVED_REDUCTION("ARMOR_RECEIVED_REDUCTION"),
        ARMOR_GRANTED_REDUCTION("ARMOR_GRANTED_REDUCTION"),
        DAMAGE_RECEIVED_INCREASE("DAMAGE_RECEIVED_INCREASE"),
        DAMAGE_DEALT_DECREASE("DAMAGE_DEALT_DECREASE");


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
