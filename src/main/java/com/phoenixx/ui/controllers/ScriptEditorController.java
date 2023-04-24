package com.phoenixx.ui.controllers;

import com.phoenixx.script.Action;
import com.phoenixx.script.VugenScript;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:46 PM [23-04-2023]
 */
public class ScriptEditorController {
    public ScrollPane projectManagerScroll;
    public AnchorPane projectManagerPane;
    public ScrollPane requestEditorScroll;
    public AnchorPane requestEditorPane;
    public ScrollPane responseEditorScroll;
    public AnchorPane responseEditorPane;

    private VugenScript vugenScript;

    public void initialize() {
        // Start off with the loading page


    }

    public void setVugenScript(VugenScript vugenScript) {
        this.vugenScript = vugenScript;

        // TODO Load all script data into window
        for(Action action: this.vugenScript.getActions()) {
            Label label = new Label();
            label.setText(action.getActionName());

            projectManagerPane.getChildren().add(label);
        }
    }
}
