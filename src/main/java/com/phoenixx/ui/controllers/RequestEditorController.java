package com.phoenixx.ui.controllers;

import com.jfoenix.controls.*;
import com.phoenixx.HydraApp;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.core.snapshots.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.AnchorPane;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 3:01 PM [26-04-2023]
 */
public class RequestEditorController {

    public JFXComboBox requestTypeButton;
    public JFXTextField addressBar;
    public JFXButton sendButton;
    public Label requestNameLabel;
    public JFXTabPane requestTabPane;

    public AnchorPane cookiesPane;
    public AnchorPane bodyPane;
    public JFXTreeTableView jfxTable;
    public JFXTextArea bodyArea;

    @FXML
    public void initialize() {

    }

    public void updateRequestEditor(Step step) {
        System.out.println("CLICKED ON STEP: " + step.getStepName());
        addressBar.setText(step.getStepName());

        VugenScript vugenScript = HydraApp.getInstance().getScriptManager().getLoadedScript();
        Snapshot snapshot = vugenScript.getSnapshotManager().getSnapshot(step.getSnapshotId());

        //System.out.println("CLICKED ON SNAPSHOT DATA: \n" + snapshot);

        addressBar.setText(snapshot.getRequest().getPath());
        requestNameLabel.setText(step.getStepName() +"(ID: " + snapshot.getID() + ")");
        //headersPane.setText(snapshot.getRequest().getBody());

        JFXTreeTableColumn<QueryObj, Boolean> enabledColumn = new JFXTreeTableColumn<>("Enabled");
        enabledColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getValue().isEnabled()).asObject());
        enabledColumn.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(enabledColumn));
        enabledColumn.setEditable(true);

        JFXTreeTableColumn<QueryObj, String> keyColumn = new JFXTreeTableColumn<>("Key");
        keyColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getKey()));

        JFXTreeTableColumn<QueryObj, String> valColumn = new JFXTreeTableColumn<>("Value");
        valColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getVal()));
        valColumn.prefWidthProperty().set(700);

        jfxTable.getColumns().setAll(enabledColumn, keyColumn, valColumn);

        bodyArea.setText(snapshot.getRequest().getBody());

        // Fake root
        TreeItem<QueryObj> root = new TreeItem<>(new QueryObj("testKey", "testVal"));

        // Add child items
        snapshot.getRequest().getHeaders().forEach((key, queryObj) -> root.getChildren().add(new TreeItem<>(queryObj)));

        jfxTable.setRoot(root);
        // Hides the dummy root
        jfxTable.setShowRoot(false);
    }
}
