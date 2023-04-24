package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
    public Label footerLeftLabel;
    public Label footerRightLabel;
    //TODO Add proper menu bar (Saving, exit, export, find/replace etc)
    public MenuBar menuBar;
    //public JFXButton exitButton;

    public Stage primaryStage;
    public ImageView maximizeImage;

    private static Image MAX_IMAGE;
    private static Image MIN_IMAGE;

    public void initialize() throws IOException {
        // Start off with the loading page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/LoadScriptPage.fxml"));
        Parent loadScriptScene = loader.load();
        LoadScriptController loadScriptController = loader.getController();
        loadScriptController.setMainContentArea(this.mainContentArea);

        footerLeftLabel.setText("Hydra - PROTOTYPE");
        footerRightLabel.setText("v1.0.0a");

        MAX_IMAGE = new Image("hydra/fxml/images/window/Icon_Maximize.png");
        MIN_IMAGE = new Image("hydra/fxml/images/window/Icon_Minimize.png");

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

    public void onMaximize(ActionEvent actionEvent) {
        if(!primaryStage.isMaximized()) {
            maximizeImage.setImage(MIN_IMAGE);
        } else {
            maximizeImage.setImage(MAX_IMAGE);
        }

        primaryStage.setMaximized(!primaryStage.isMaximized());
    }

    public void onMinimize(ActionEvent actionEvent) {
        primaryStage.setIconified(true);
    }
}
