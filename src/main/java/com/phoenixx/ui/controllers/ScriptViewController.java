package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXTreeViewPath;
import com.phoenixx.core.script.Action;
import com.phoenixx.core.script.Step;
import com.phoenixx.core.script.Transaction;
import com.phoenixx.core.script.impl.VugenScript;
import com.phoenixx.ui.components.tree.FilterableTreeItem;
import com.phoenixx.ui.components.tree.TreeItemPredicate;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:46 PM [23-04-2023]
 */
public class ScriptViewController {
    public Node scriptViewRoot;
    public VBox treeVBox;
    public JFXTreeView projectManagerTree;
    public StackPane codeTab;

    public RequestEditorController editorController;

    private VugenScript vugenScript;

    public void initialize() throws IOException {
        // Start off with the loading page
    }

    public void setVugenScript(VugenScript vugenScript) throws IOException {
        this.vugenScript = vugenScript;

        //final JFXTreeView projectManagerTree = new JFXTreeView<>();
        FilterableTreeItem<String> rootNode = new FilterableTreeItem<>(this.vugenScript.getScriptFile().getScriptFolder().getName());
        rootNode.setExpanded(true);

        // TODO Load all script data into window
        for (Action action : this.vugenScript.getActions()) {
            FilterableTreeItem<String> actionNode = new FilterableTreeItem<>(action.getActionName());

            for(Transaction transaction: action.getTransactions()) {
                FilterableTreeItem<String> transactionNode = new FilterableTreeItem<>(transaction.getTransactionName());

                for(Step step: transaction.getSteps()) {
                    FilterableTreeItem<String> stepNode = new FilterableTreeItem<>(step.getStepName() + " (ID: " + step.getSnapshotId() + ")");
                    transactionNode.getInternalChildren().add(stepNode);
                }
                actionNode.getInternalChildren().add(transactionNode);
            }

            rootNode.getInternalChildren().add(actionNode);
        }

        TextField filterField = new JFXTextField();
        filterField.setPromptText("Search...");
        filterField.getStyleClass().add("FilterField");
        filterField.getStyleClass().add("/hydra/fxml/css/fullpackstyling.css");

        rootNode.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            if (filterField.getText() == null || filterField.getText().isEmpty() || filterField.getText().equals("Search...")) {
                return null;
            }
            return TreeItemPredicate.create(actor -> actor.contains(filterField.getText()));
        }, filterField.textProperty()));

        projectManagerTree.setRoot(rootNode);

        JFXTreeViewPath jfxTreeViewPath = new JFXTreeViewPath(projectManagerTree);
        jfxTreeViewPath.getStylesheets().add("/hydra/fxml/css/fullpackstyling.css");

        jfxTreeViewPath.setFocusTraversable(false);
        jfxTreeViewPath.setFitToWidth(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/RequestEditor.fxml"));
        Parent editorScene = loader.load();
        codeTab.getChildren().setAll(editorScene);
        editorController = loader.getController();

        // Add event filter for double-click events
        projectManagerTree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                FilterableTreeItem<String> stepItem = (FilterableTreeItem<String>) projectManagerTree.getSelectionModel().getSelectedItem();
                if (stepItem != null) {
                    // Do something when item1 is double-clicked
                    System.out.println("Clicked on request: " + stepItem.getValue());

                    // Get parent folders
                    List<String> parentFolders = new ArrayList<>();
                    TreeItem<String> parent = stepItem.getParent();
                    while (parent != null) {
                        parentFolders.add(parent.getValue());
                        parent = parent.getParent();
                    }
                    Collections.reverse(parentFolders);

                    if(parentFolders.size() < 3) {
                        return;
                    }
                    String actionFileName = parentFolders.get(1);
                    String transactionName = parentFolders.get(2);

                    for(Action action: vugenScript.getActions()) {
                        if(action.getActionName().equalsIgnoreCase(actionFileName)) {
                            for(Transaction transaction: action.getTransactions()) {
                                if(transaction.getTransactionName().equalsIgnoreCase(transactionName)) {
                                    for(Step step: transaction.getSteps()) {
                                        String stepName = stepItem.getValue().substring(0, stepItem.getValue().indexOf("(")).trim();
                                        if(step.getStepName().equalsIgnoreCase(stepName)) {
                                            try {
                                                requestUpdate(step);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Do something with parent folders
                    System.out.println("Item 1 double-clicked in folders: " + parentFolders);
                }
            }
        });

        // Add event listener for selection changes
       /* projectManagerTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old val: " + oldValue + " New val: " + newValue);
        });
*/
        // change color + size for the path
        projectManagerTree.getSelectionModel().selectedItemProperty().addListener(observable -> {
            for (Node node: ((HBox) jfxTreeViewPath.getContent()).getChildren()) {
                if (node instanceof StackPane) {
                    for (int i = 0; i < ((StackPane) node).getChildren().size(); i++) {
                        Node path = ((StackPane) node).getChildren().get(i);
                        if (path instanceof Button) {
                            // modify css all buttons, can't use .button directly.
                            // Because set traversable for .button affects all buttons
                            path.getStyleClass().add("tv-path-button");
                        }
                    }
                }
            }
        });

        treeVBox.getChildren().add(0, jfxTreeViewPath);
        treeVBox.getChildren().add(1, filterField);

        // fix the padding issue
        Label tvPathLabel = (Label) ((HBox) jfxTreeViewPath.getContent()).getChildren().get(0);
        tvPathLabel.getStyleClass().add("tv-path-init-label");

        // Load the editor
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/RequestEditor.fxml"));
        Parent editorScene = loader.load();
        //codeTab.getChildren().setAll(editorScene);

        RequestEditorController editorController = loader.getController();
        editorController.setupScript(this.vugenScript.getActions().get(1), codeTab);*/
    }

    private void requestUpdate(Step step) throws IOException {
        if(editorController == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hydra/fxml/RequestEditor.fxml"));
            Parent editorScene = loader.load();
            codeTab.getChildren().setAll(editorScene);
            editorController = loader.getController();
        }
        if(editorController != null) {
            editorController.updateRequestEditor(step);
        }
    }

    private void setActionFile() {

    }
}
