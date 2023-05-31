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
    private final Transaction transaction;

    private int snapshotID = -1;

    public Step(String stepType, String stepName, Transaction transaction) {
        this.stepType = stepType;
        this.stepName = stepName;
        this.stepData = new ArrayList<>();
        this.transaction = transaction;
    }

    //FIXME This is only for testing purposes
    public int getSnapshotId() {
        if(this.snapshotID == -1) {
            for (String line : this.stepData) {
                if (line.startsWith("Snapshot=")) {
                    String snapshotVal = line.split("=")[1];
                    // Remove the "t" character in front of the number
                    snapshotVal = snapshotVal.substring(1);
                    // Remove the file extension
                    snapshotVal = snapshotVal.substring(0, snapshotVal.length() - 4);
                    this.snapshotID = Integer.parseInt(snapshotVal);
                }
            }
        }
        return this.snapshotID;
    }

    public String getStepName() {
        return stepName;
    }

    public List<String> getStepData() {
        return stepData;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
