package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.phoenixx.script.Step;
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
    }
}
