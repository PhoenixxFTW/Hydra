package com.phoenixx.ui.controllers;

import com.phoenixx.HydraApp;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.core.snapshots.impl.Snapshot;
import com.phoenixx.ui.controllers.components.RequestAddressBarController;
import com.phoenixx.ui.controllers.components.RequestDataController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 3:01 PM [26-04-2023]
 */
public class RequestEditorController {

    public Label requestNameLabel;
    public VBox mainVBox;

    public RequestAddressBarController addressBarController;
    public RequestDataController requestDataController;

    public RequestDataController responseDataController;
    public StackPane requestPane;
    public StackPane responsePane;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader;
        Parent editorScene;

        // Store the StackPane before we replace its position in the mainVBox
        Node stackPane = this.mainVBox.getChildren().get(1);

        // Load the address bar
        loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/RequestAddressBar.fxml"));
        editorScene = loader.load();
        this.mainVBox.getChildren().set(1, editorScene);
        this.addressBarController = loader.getController();

        // Add StackPane after the address bar
        this.mainVBox.getChildren().add(stackPane);

        // Load the request data viewer
        loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/RequestDataViewer.fxml"));
        editorScene = loader.load();
        this.requestPane.getChildren().add(editorScene);
        this.requestDataController = loader.getController();

        // Load the response data viewer
        loader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/RequestDataViewer.fxml"));
        editorScene = loader.load();
        this.responsePane.getChildren().add(editorScene);
        this.responseDataController = loader.getController();
    }

    public void updateRequestEditor(Step step) {
        System.out.println("CLICKED ON STEP: " + step.getStepName());

        VugenScript vugenScript = HydraApp.getInstance().getScriptManager().getLoadedScript();
        Snapshot snapshot = vugenScript.getSnapshotManager().getSnapshot(step.getSnapshotId());

        System.out.println("CLICKED ON SNAPSHOT DATA: \n" + snapshot);
        this.requestNameLabel.setText(step.getStepName() +"(ID: " + snapshot.getID() + ")");

        this.addressBarController.setup(snapshot);

        this.requestDataController.setup("Request", snapshot.getRequest(), snapshot);
        this.responseDataController.setup("Response", snapshot.getResponse(), snapshot);
    }
}
