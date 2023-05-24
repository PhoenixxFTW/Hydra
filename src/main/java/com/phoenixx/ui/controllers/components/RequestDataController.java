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
import javafx.scene.layout.AnchorPane;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 7:19 PM [20-05-2023]
 */
public class RequestDataController {
    public JFXTabPane requestTabPane;
    public JFXTreeTableView jfxTable;

    public AnchorPane cookiesPane;

    public JFXTextArea bodyArea;
    public Tab tabNameLabel;

    @FXML
    public void initialize() {

    }

    public void setup(String label, HTTPObject httpObject, Snapshot snapshot) {
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

        tabNameLabel.setText(label);

        // Fake root
        TreeItem<QueryObj> root = new TreeItem<>(new QueryObj("testKey", "testVal"));

        // Add child items
        httpObject.getHeaders().forEach((key, queryObj) -> root.getChildren().add(new TreeItem<>(queryObj)));

        jfxTable.setRoot(root);
        // Hides the dummy root
        jfxTable.setShowRoot(false);

        bodyArea.setText(httpObject.getBody());

        System.out.println("SETTING LABEL TO : " + tabNameLabel);
    }
}
