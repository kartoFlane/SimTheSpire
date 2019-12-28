package com.kartoflane.spiresim.controller.targeting;

public enum TargetingType {
    /**
     * This targeting type selects a single target for the card
     */
    SINGLE(new SingleTargetingController()),
    /**
     * This targeting type selects all available valid targets
     */
    ALL(new AllTargetingController()),
    /**
     * This targeting type doesn't select any target. Useful for
     * cards that apply to the caster, such as buffs, or cards
     * that don't target anything directly.
     */
    NONE(new NoneTargetingController());


    private TargetingController controller;

    TargetingType(TargetingController controller) {
        this.controller = controller;
    }

    public TargetingController getController() {
        return controller;
    }
}
