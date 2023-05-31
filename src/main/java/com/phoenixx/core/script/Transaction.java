package com.phoenixx.core.script;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Transaction can store multiple HTTP requests along with their corresponding responses}
 *
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:49 a.m. [18/04/2023]
 */
public class Transaction {
    private final String transactionName;
    private final List<Step> steps;
    private final Action action;

    public Transaction(String transactionName, Action action) {
        this.transactionName = transactionName;
        this.action = action;
        this.steps = new ArrayList<>();
    }

    public void buildTransaction() {

    }

    public Step getStepByID(int ID) {
        for(Step step: this.steps) {
            if(step.getSnapshotId() == ID) {
                return step;
            }
        }
        return null;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Action getAction() {
        return action;
    }
}
