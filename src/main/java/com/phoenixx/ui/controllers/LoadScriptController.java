package com.phoenixx.ui.controllers;

import com.phoenixx.HydraApp;
import com.phoenixx.core.VugenScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

    // Content area for the hydra client
    private Pane mainContentArea;

    /**
     * Called on menu initialization
     */
    @FXML
    public void initialize() {
        this.info.setText("Build Version: " + HydraApp.BUILD_VERSION + "\nBuild Date: " + HydraApp.BUILD_DATE);
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

            VugenScript loadedScript = HydraApp.getInstance().loadScript(selectedFile);

            if(loadedScript != null) {
                // Load the script editing scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/ScriptViewScene.fxml"));
                Parent editorScene = loader.load();

                ScriptViewController scriptViewController = loader.getController();
                scriptViewController.setVugenScript(loadedScript);
                mainContentArea.getChildren().setAll(editorScene);

            } else {
                // TODO Add some sort of error message popup?
            }
        }
    }

    public void setMainContentArea(Pane mainContentArea) {
        this.mainContentArea = mainContentArea;
    }

    public void loadHydraScript(ActionEvent actionEvent) {

    }
}
