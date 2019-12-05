package com.kartoflane.spiresim;

import com.kartoflane.spiresim.controller.SimulationController;
import com.kartoflane.spiresim.state.SimulationState;

public class SimTheSpireApp {

    private SimTheSpireApp() {
    }

    public static void main(String[] args) {
        SimulationController simulationController = new SimulationController(new SimulationState());

        simulationController.start();
    }
}
