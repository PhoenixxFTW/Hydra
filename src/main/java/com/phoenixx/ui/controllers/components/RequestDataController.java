package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.*;
import com.phoenixx.core.script.ScriptContext;
import com.phoenixx.core.snapshots.data.CorrelationContext;
import com.phoenixx.core.snapshots.data.HTTPObject;
import com.phoenixx.core.snapshots.data.QueryObj;
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
import java.util.*;

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

    public void setup(String label, HTTPObject httpObject, Snapshot snapshot, ScriptContext scriptContext) throws IOException {
        this.tabNameLabel.setText(label);
        this.bodyArea.setText(httpObject.getBody());

        this.setupTable(this.headersTable, httpObject.getHeaders());
        this.setupTable(this.cookiesTable, httpObject.getCookies());


        //Map<Snapshot, Map<String, String>> matchingData = scriptContext.getScript().getSnapshotManager().getPreReqs(snapshot.getID());
        Map<Snapshot, List<CorrelationContext>> correlationMap = scriptContext.getScript().getSnapshotManager().getCorrelationManager().scanCorrelations(snapshot, false);

        //System.out.println("Received total of: " + correlationMap.size() + " correlation map objects @");

        // Retrieve the keys (IDs) from the Map and store them in a List
        List<Snapshot> keys = new ArrayList<>(correlationMap.keySet());
        // Sort the List using a custom Comparator by each Snapshots ID. Smaller ID's go first, larger ones come after
        keys.sort(Comparator.comparingInt(Snapshot::getID));

        this.correlationTimeLine.getChildren().clear();

        for(Snapshot snapshotMatch: keys) {
            List<CorrelationContext> correlationContexts = correlationMap.get(snapshotMatch);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/CorrelationElement.fxml"));
            Parent editorScene = loader.load();
            CorrelationElementController correlationElementController = loader.getController();
            //TODO Change this, the action, transaction, and step are all referring to the current snapshot, not the correlation snapshot. Our correlation manager needs to return all this data
            correlationElementController.setup(scriptContext.getScript(), snapshotMatch, correlationContexts);

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
