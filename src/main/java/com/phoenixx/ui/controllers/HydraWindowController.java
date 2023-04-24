package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:28 AM [19-04-2023]
 */
public class HydraWindowController {

    public Pane mainContentArea;
    public JFXButton exitButton;
    public VBox vBox;
    public Rectangle separator;
    //public JFXButton exitButton;

    public void initialize() throws IOException {
        // Start off with the loading page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/LoadScriptPage.fxml"));
        Parent loadScriptScene = loader.load();
        LoadScriptController loadScriptController = loader.getController();
        loadScriptController.setMainContentArea(this.mainContentArea);

        mainContentArea.getChildren().setAll(loadScriptScene);
    }

    /**
     * Called when the user clicks on the X in the top right of the popup
     * @param actionEvent
     */
    public void exitPressed(ActionEvent actionEvent) {
        //TODO Add popup to ask user if they would like to exit / save the script before closing
        System.exit(0);
    }
}
