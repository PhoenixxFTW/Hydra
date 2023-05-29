package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.*;
import com.phoenixx.core.script.Action;
import com.phoenixx.core.script.IScript;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.Transaction;
import com.phoenixx.core.snapshots.HTTPObject;
import com.phoenixx.core.snapshots.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import com.phoenixx.ui.components.slider.CustomSliderOption;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public HBox searchHBox;
    public JFXButton searchButton;
    public VBox correlationTimeLine;

    @FXML
    public void initialize() {

    }

    public void setup(String label, HTTPObject httpObject, Snapshot snapshot, IScript script, Action action, Transaction transaction, Step step) throws IOException {
        this.tabNameLabel.setText(label);
        this.bodyArea.setText(httpObject.getBody());

        this.setupTable(this.headersTable, httpObject.getHeaders());
        this.setupTable(this.cookiesTable, httpObject.getCookies());

        Map<Snapshot, Map<String, String>> matchingData = script.getSnapshotManager().getPreReqs(snapshot.getID());

        Comparator<Snapshot> comparator = (Snapshot snapshot1, Snapshot snapshot2) -> {
            return (snapshot1.getID() < snapshot2.getID()) ? 1 : 0;
        };

        SortedSet<Snapshot> keys = new TreeSet<>(comparator);
        keys.addAll(matchingData.keySet());

        this.correlationTimeLine.getChildren().clear();

        StringBuilder stringBuilder = new StringBuilder();
        for(Snapshot snapshotMatch: keys) {
            stringBuilder.append("Snapshot #").append(snapshotMatch.getID()).append("\n");

            Map<String, String> items = matchingData.get(snapshotMatch);
            for(String key: items.keySet()) {
                stringBuilder.append("\t Matched '").append(key).append("' value: ").append(items.get(key)).append("\n");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/CorrelationElement.fxml"));
            Parent editorScene = loader.load();
            CorrelationElementController correlationElementController = loader.getController();
            //TODO Change this, the action, transaction, and step are all refering to the current snapshot, not the correlation snapshot. Our correlation manager needs to return all this data
            correlationElementController.setup(script, action, transaction, step, snapshotMatch, items);
            this.correlationTimeLine.getChildren().add(editorScene);
        }

        CustomSliderOption sliderOption = new CustomSliderOption();
        sliderOption.setStyle("-fx-background-color: #1f2329");
        sliderOption.setSliderOption(new CustomSliderOption.SliderOption("Similarity Threshold", 0, 0, 10, 1, 2, 1, 662, true));
        this.searchHBox.getChildren().setAll(searchButton, sliderOption);

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
