package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.*;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.core.snapshots.HTTPObject;
import com.phoenixx.core.snapshots.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import com.phoenixx.ui.components.slider.CustomSliderOption;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.HBox;

import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 7:19 PM [20-05-2023]
 */
public class RequestDataController {

    public Tab tabNameLabel;
    public JFXTabPane requestTabPane;
    public JFXTreeTableView headersTable;
    public JFXTreeTableView cookiesTable;
    public JFXTextArea bodyArea;
    public JFXTextArea analysisTextArea;

    public HBox searchHBox;
    public JFXButton searchButton;

    @FXML
    public void initialize() {

    }

    public void setup(String label, HTTPObject httpObject, Snapshot snapshot, VugenScript vugenScript) {
        this.tabNameLabel.setText(label);
        this.bodyArea.setText(httpObject.getBody());

        this.setupTable(this.headersTable, httpObject.getHeaders());
        this.setupTable(this.cookiesTable, httpObject.getCookies());

        Map<Snapshot, Map<String, String>> matchingData = vugenScript.getSnapshotManager().getPreReqs(snapshot.getID());

        StringBuilder stringBuilder = new StringBuilder();
        for(Snapshot snapshotMatch: matchingData.keySet()) {
            stringBuilder.append("Snapshot #").append(snapshotMatch.getID()).append("\n");

            Map<String, String> items = matchingData.get(snapshotMatch);
            for(String key: items.keySet()) {
                stringBuilder.append("\t Matched '").append(key).append("' value: ").append(items.get(key)).append("\n");
            }
        }

        CustomSliderOption sliderOption = new CustomSliderOption();
        sliderOption.setStyle("-fx-background-color: #1f2329");
        sliderOption.setSliderOption(new CustomSliderOption.SliderOption("Similarity Threshold", 0, 0, 10, 1, 2, 1, 662, true));
        this.searchHBox.getChildren().setAll(searchButton, sliderOption);

        this.analysisTextArea.setEditable(false);
        this.analysisTextArea.setText(stringBuilder.toString());
    }

    private void setupTable(JFXTreeTableView tableView, Map<String, QueryObj> data) {
        JFXTreeTableColumn<QueryObj, Boolean> enabledColumn = new JFXTreeTableColumn<>("Enabled");
        enabledColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getValue().isEnabled()).asObject());
        enabledColumn.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(enabledColumn));
        enabledColumn.getStyleClass().add("enabledColumn");
        enabledColumn.setEditable(true);

        JFXTreeTableColumn<QueryObj, String> keyColumn = new JFXTreeTableColumn<>("Key");
        keyColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getKey()));
        keyColumn.getStyleClass().add("keyColumn");

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
