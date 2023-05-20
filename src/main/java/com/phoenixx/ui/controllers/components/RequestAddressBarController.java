package com.phoenixx.ui.controllers.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.phoenixx.core.snapshots.impl.Snapshot;
import javafx.fxml.FXML;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 7:19 PM [20-05-2023]
 */
public class RequestAddressBarController {
    public JFXComboBox requestTypeButton;
    public JFXTextField addressBar;
    public JFXButton sendButton;

    @FXML
    public void initialize() {

    }

    public void setup(Snapshot snapshot) {
        addressBar.setText(snapshot.getRequest().getPath());
    }
}
