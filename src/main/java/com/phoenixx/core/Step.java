package com.phoenixx.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the request that was recorded in vugen, along with its recording and replay snapshots
 *
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:51 a.m. [18/04/2023]
 */
public class Step {

    private final String stepType;
    private final String stepName;
    private final List<String> stepData;
    public Step(String stepType, String stepName) {
        this.stepType = stepType;
        this.stepName = stepName;
        this.stepData = new ArrayList<>();
    }

    public String getStepName() {
        return stepName;
    }

    public List<String> getStepData() {
        return stepData;
    }
}
