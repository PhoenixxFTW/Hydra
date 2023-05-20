package com.phoenixx.ui.controllers;

import com.jfoenix.controls.*;
import com.phoenixx.HydraApp;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.core.snapshots.impl.Snapshot;
import com.phoenixx.ui.controllers.components.RequestAddressBarController;
import com.phoenixx.ui.controllers.components.RequestDataController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
    public VBox mainVBox;

    public RequestAddressBarController requestAddressBarController;
    public RequestDataController requestDataController;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader;
        Parent editorScene;

        // Load the address bar
        loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/RequestAddressBar.fxml"));
        editorScene = loader.load();
        mainVBox.getChildren().add(editorScene);
        requestAddressBarController = loader.getController();

        // Load the request data viewer
        loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/RequestDataViewer.fxml"));
        editorScene = loader.load();
        mainVBox.getChildren().add(editorScene);
        requestDataController = loader.getController();

        // Load the response data viewer
    }

    public void updateRequestEditor(Step step) {
        System.out.println("CLICKED ON STEP: " + step.getStepName());

        VugenScript vugenScript = HydraApp.getInstance().getScriptManager().getLoadedScript();
        Snapshot snapshot = vugenScript.getSnapshotManager().getSnapshot(step.getSnapshotId());

        System.out.println("CLICKED ON SNAPSHOT DATA: \n" + snapshot);
        this.requestNameLabel.setText(step.getStepName() +"(ID: " + snapshot.getID() + ")");

        this.requestAddressBarController.setup(snapshot);
        this.requestDataController.setup(snapshot.getRequest());
    }
}
