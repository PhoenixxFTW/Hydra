package com.phoenixx.script;

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
    private final List<HTTPRequest> requests;

    public Transaction(String transactionName) {
        this.transactionName = transactionName;
        this.requests = new ArrayList<>();
    }

    public String getTransactionName() {
        return transactionName;
    }
}
