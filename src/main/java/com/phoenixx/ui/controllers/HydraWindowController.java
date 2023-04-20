package com.phoenixx.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:28 AM [19-04-2023]
 */
public class HydraWindowController {

    @FXML
    public Label info;

    public void initialize() {
        try {
            //Manifest manifest = new JarInputStream(new URL("http://localhost:1337/client.jar").openStream()).getManifest();
            info.setText("Build Date: " + "\n" + "Build Version: ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void inject() {

        exit();
    }

    public void exit() {
        System.exit(0);
    }
}
