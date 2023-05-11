package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.phoenixx.HydraApp;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    public JFXTextArea textArea;

    @FXML
    public void initialize() {

    }

    public void updateRequestEditor(Step step) {
        System.out.println("CLICKED ON STEP: " + step.getStepName());
        addressBar.setText(step.getStepName());
        requestNameLabel.setText(step.getStepName());
        textArea.setText(step.getStepData().toString());

        VugenScript vugenScript = HydraApp.getInstance().getScriptManager().getLoadedScript();
        Snapshot snapshot = vugenScript.getSnapshotManager().getSnapshot(step.getSnapshotId());

        System.out.println("CLICKED ON SNAPSHOT DATA: \n" + snapshot);

        addressBar.setText(snapshot.getRequest().getPath());
        textArea.setText(snapshot.getRequest().getBody());
    }
}
