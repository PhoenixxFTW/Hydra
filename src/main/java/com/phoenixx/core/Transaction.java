package com.phoenixx.core;

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

    public Transaction(String transactionName) {
        this.transactionName = transactionName;
        this.steps = new ArrayList<>();
    }

    public void buildTransaction() {

    }

    public String getTransactionName() {
        return transactionName;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
