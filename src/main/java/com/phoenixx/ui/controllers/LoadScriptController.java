package com.phoenixx.ui.controllers;

import com.phoenixx.HydraApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 1:20 PM [21-04-2023]
 */
public class LoadScriptController {

    @FXML
    public Label info;

    /**
     * Called on menu initialization
     */
    @FXML
    public void initialize() {
        this.info.setText("Build Date: " + HydraApp.BUILD_DATE + "\n" + "Build Version: " + HydraApp.BUILD_VERSION);
    }

    /**
     * Called when the user clicks on the "Load Script Button". Allows the user to open a .usr (vugen) file to be loaded into the main Hydra client.
     * @param actionEvent The action which occurred on click
     */
    public void loadVugenScript(ActionEvent actionEvent) throws IOException {
        System.out.println("LOADING FILE @@@@");

        //FileChooser fileChooser = new FileChooser();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        //directoryChooser.setInitialDirectory(new File("src"));

        File selectedFile = directoryChooser.showDialog(null);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            HydraApp.getInstance().loadScript(selectedFile);
        }
    }
}
