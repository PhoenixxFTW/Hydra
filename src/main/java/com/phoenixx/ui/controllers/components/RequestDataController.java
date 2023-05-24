package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.phoenixx.core.snapshots.HTTPObject;
import com.phoenixx.core.snapshots.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeTableCell;

import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 7:19 PM [20-05-2023]
 */
public class RequestDataController {
    public JFXTabPane requestTabPane;
    public JFXTreeTableView headersTable;
    public JFXTextArea bodyArea;
    public Tab tabNameLabel;
    public JFXTreeTableView cookiesTable;
    @FXML
    public void initialize() {

    }

    public void setup(String label, HTTPObject httpObject, Snapshot snapshot) {
        tabNameLabel.setText(label);
        bodyArea.setText(httpObject.getBody());

        this.setupTable(this.headersTable, httpObject.getHeaders());
        this.setupTable(this.cookiesTable, httpObject.getCookies());
    }

    private void setupTable(JFXTreeTableView tableView, Map<String, QueryObj> data) {
        JFXTreeTableColumn<QueryObj, Boolean> enabledColumn = new JFXTreeTableColumn<>("Enabled");
        enabledColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getValue().isEnabled()).asObject());
        enabledColumn.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(enabledColumn));
        enabledColumn.setEditable(true);

        JFXTreeTableColumn<QueryObj, String> keyColumn = new JFXTreeTableColumn<>("Key");
        keyColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getKey()));

        JFXTreeTableColumn<QueryObj, String> valColumn = new JFXTreeTableColumn<>("Value");
        valColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getVal()));
        valColumn.prefWidthProperty().set(700);

        tableView.getColumns().setAll(enabledColumn, keyColumn, valColumn);

        // Fake root
        TreeItem<QueryObj> root = new TreeItem<>(new QueryObj("testKey", "testVal"));

        // Add child items
        data.forEach((key, queryObj) -> root.getChildren().add(new TreeItem<>(queryObj)));

        tableView.setRoot(root);
        // Hides the dummy root
        tableView.setShowRoot(false);
    }
}
