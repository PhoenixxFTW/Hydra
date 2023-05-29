package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.phoenixx.core.script.Action;
import com.phoenixx.core.script.IScript;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.Transaction;
import com.phoenixx.core.snapshots.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:12 AM [29-05-2023]
 */
public class CorrelationElementController {

    public Label snapshotIDLabel;
    public Label snapshotNameLabel;
    public Label snapshotLocLabel;

    public Snapshot snapshot;
    public JFXTreeTableView correlationTable;

    @FXML
    public void initialize() {

    }

    public void setup(IScript script, Action action, Transaction transaction, Step step, Snapshot snapshot, Map<String, String> correlationData) {
        this.snapshot = snapshot;

        this.snapshotIDLabel.setText(snapshot.getID() + "");

        this.snapshotNameLabel.setText(step.getStepName());
        this.snapshotNameLabel.setFont(new Font(16));

        this.snapshotLocLabel.setText(action.getActionName() + "/" + transaction.getTransactionName() + "/" + step.getStepName());
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
        correlationData.forEach((key, val) -> root.getChildren().add(new TreeItem<>(new QueryObj(key, val))));

        this.correlationTable.setRoot(root);
        // Hides the dummy root
        this.correlationTable.setShowRoot(false);

    }

    public static void autoResizeColumns(JFXTreeTableView<?> table ) {
        //Set the right policy
        table.setColumnResizePolicy(TreeTableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getCurrentItemsCount(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth( max + 10.0d );
        } );
    }
}
