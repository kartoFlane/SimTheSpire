package com.kartoflane.spiresim.controller.targeting;

import com.kartoflane.spiresim.controller.EntityController;

import java.util.List;

public final class TargetingResult {

    private final TargetingResultType type;
    private final List<EntityController> targets;


    TargetingResult(TargetingResultType type, List<EntityController> targets) {
        this.type = type;
        this.targets = targets;
    }

    public TargetingResultType getType() {
        return type;
    }

    public List<EntityController> getTargets() {
        return targets;
    }
}
