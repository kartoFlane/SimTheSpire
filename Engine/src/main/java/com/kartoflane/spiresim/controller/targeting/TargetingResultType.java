package com.kartoflane.spiresim.controller.targeting;

public enum TargetingResultType {
    NO_VALID_TARGETS,
    UNABLE_TO_ATTACK,
    TARGET_SINGLE,
    TARGET_ALL,
    TARGET_NONE;

    public boolean isValidTarget() {
        return this == TARGET_SINGLE || this == TARGET_ALL || this == TARGET_NONE;
    }
}
