package com.kartoflane.spiresim.controller.targeting;

public enum TargetingType {
    SINGLE(new SingleTargetingController()),
    ALL(new AllTargetingController()),
    NONE(new NoneTargetingController());


    private TargetingController controller;

    TargetingType(TargetingController controller) {
        this.controller = controller;
    }

    public TargetingController getController() {
        return controller;
    }
}
