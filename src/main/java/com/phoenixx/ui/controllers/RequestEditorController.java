package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 3:01 PM [26-04-2023]
 */
public class RequestEditorController {
    public JFXScrollPane scrollPane;
    public VBox lineColumn;
    public VBox textColumn;

    @FXML
    public void initialize() {
        for(int i = 0; i < 100; i++) {
            Label label = new Label();
            label.setText(String.valueOf(i));
            label.setFont(new Font("System",16));
            label.setAlignment(Pos.CENTER);
            label.setContentDisplay(ContentDisplay.CENTER);
            label.setTextFill(Paint.valueOf("#ababab"));
            HBox.setHgrow(label, Priority.ALWAYS);
            VBox.setVgrow(label, Priority.ALWAYS);

            lineColumn.getChildren().add(label);
        }
    }
}
