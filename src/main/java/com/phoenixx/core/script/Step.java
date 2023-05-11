package com.phoenixx.core.script;

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

    //FIXME This is only for testing purposes
    public int getSnapshotId() {
        for(String line: this.stepData) {
            if(line.startsWith("Snapshot=")) {
                String snapshotVal = line.split("=")[1];
                // Remove the "t" character in front of the number
                snapshotVal = snapshotVal.substring(1);
                // Remove the file extension
                snapshotVal = snapshotVal.substring(0, snapshotVal.length() - 4);
                return Integer.parseInt(snapshotVal);
            }
        }
        return -1;
    }

    public String getStepName() {
        return stepName;
    }

    public List<String> getStepData() {
        return stepData;
    }
}
