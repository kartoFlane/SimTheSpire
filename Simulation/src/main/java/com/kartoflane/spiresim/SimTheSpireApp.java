package com.kartoflane.spiresim;

import com.kartoflane.spiresim.controller.SimulationController;
import com.kartoflane.spiresim.state.SimulationState;

public class SimTheSpireApp {

    private SimTheSpireApp() {
    }

    public static void main(String[] args) {
        SimulationState simulationState = new SimulationState();
        SimulationController simulationController = new SimulationController(simulationState);

        simulationController.start();
    }
}
