package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.phoenixx.core.script.Action;
import com.phoenixx.core.script.IScript;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.Transaction;
import com.phoenixx.core.snapshots.data.CorrelationContext;
import com.phoenixx.core.snapshots.data.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:12 AM [29-05-2023]
 */
public class CorrelationElementController {

    public Label snapshotIDLabel;
    public Label snapshotNameLabel;
    public Label snapshotLocLabel;

    public Snapshot snapshotMatched;
    public JFXTreeTableView correlationTable;

    @FXML
    public void initialize() {

    }

    public void setup(IScript script, Snapshot snapshotMatched, List<CorrelationContext> correlationData) {
        String actionName = "NULL";
        String transactionName = "NULL";
        String stepName = "NULL";

        Step step = script.getStepFromID(snapshotMatched.getID());

       // System.out.println("STARTING STEP SEARCH @@@@@");
        for(Action action: script.getActions()) {
            for(Transaction transaction: action.getTransactions()) {
                for(Step step2: transaction.getSteps()) {
                    //System.out.println("Found step: " + step.getSnapshotId() + " NAME: " + step.getStepName());
                    if(step2.getSnapshotId() == snapshotMatched.getID()) {
                        step = step2;
                    }
                }
            }
        }
        //System.out.println("FOUND TOTAL STEPS: " + counter);
        System.out.println("SNAPSHOT: " + snapshotMatched.getID() + " Had Total of " + correlationData.size() + " CORRELATIONS!");

        if(step != null) {
            stepName = step.getStepName();
            transactionName = step.getTransaction().getTransactionName();
            actionName = step.getTransaction().getAction().getActionName();
        } else {
            System.out.println("FAILED TO SET STEP DATA, STEP WAS NULL WITH ID: " + snapshotMatched.getID());
        }

        this.snapshotMatched = snapshotMatched;

        this.snapshotIDLabel.setText(snapshotMatched.getID() + "");

        this.snapshotNameLabel.setText(stepName);
        this.snapshotNameLabel.setFont(new Font(16));

        this.snapshotLocLabel.setText(actionName + "/" + transactionName + "/" + stepName);
        this.snapshotLocLabel.setStyle("-fx-text-fill: lightgray");

        HBox.setHgrow(this.correlationTable, Priority.ALWAYS);

        JFXTreeTableColumn<QueryObj, String> correlationParamColumn = new JFXTreeTableColumn<>("Correlation Parameter");
        correlationParamColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getKey()));

        JFXTreeTableColumn<QueryObj, String> valColumn = new JFXTreeTableColumn<>("Value");
        valColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getVal()));

        this.correlationTable.getColumns().setAll(correlationParamColumn, valColumn);

        // Fake root
        TreeItem<QueryObj> root = new TreeItem<>(new QueryObj("testKey", "testVal"));

        // Add child items
        correlationData.forEach(correlationContext -> {
            root.getChildren().add(new TreeItem<>(new QueryObj(correlationContext.correlationKeyOne, correlationContext.correlationValOne)));
        });

        this.correlationTable.setRoot(root);
        // Hides the dummy root
        this.correlationTable.setShowRoot(false);

    }
}
