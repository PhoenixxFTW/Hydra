package com.phoenixx.ui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXTreeViewPath;
import com.phoenixx.script.Action;
import com.phoenixx.script.Step;
import com.phoenixx.script.Transaction;
import com.phoenixx.script.VugenScript;
import com.phoenixx.ui.components.tree.FilterableTreeItem;
import com.phoenixx.ui.components.tree.TreeItemPredicate;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 8:46 PM [23-04-2023]
 */
public class ScriptViewController {
/*    public ScrollPane projectManagerScroll;
    public AnchorPane projectManagerPane;
    public ScrollPane requestEditorScroll;
    public AnchorPane requestEditorPane;
    public ScrollPane responseEditorScroll;
    public AnchorPane responseEditorPane;
    public JFXComboBox<String> actionFileChooser;*/

    public Node scriptViewRoot;
    public VBox treeVBox;
    public JFXTreeView projectManagerTree;

    private VugenScript vugenScript;

    public void initialize() {
        // Start off with the loading page
    }

    public void setVugenScript(VugenScript vugenScript) {
        this.vugenScript = vugenScript;

        //final JFXTreeView projectManagerTree = new JFXTreeView<>();
        FilterableTreeItem<String> rootNode = new FilterableTreeItem<>(this.vugenScript.getScriptFolder().getName());
        rootNode.setExpanded(true);

        // TODO Load all script data into window
        for (Action action : this.vugenScript.getActions()) {
            FilterableTreeItem<String> actionNode = new FilterableTreeItem<>(action.getActionName());

            for(Transaction transaction: action.getTransactions()) {
                FilterableTreeItem<String> transactionNode = new FilterableTreeItem<>(transaction.getTransactionName());

                for(Step step: transaction.getSteps()) {
                    FilterableTreeItem<String> stepNode = new FilterableTreeItem<>(step.getStepName());
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
        //projectManagerTree.prefHeightProperty().bind(treeVBox.heightProperty());

/*        //TODO Redo this
        // Find the ScrollPane inside the treeView
        ScrollPane scrollPane = (ScrollPane) projectManagerTree.lookup(".scroll-pane");
        if (scrollPane != null) {
            // Bind the ScrollPane's prefHeight property to the vBox's height property
            scrollPane.prefHeightProperty().bind(treeVBox.heightProperty());
            // Set the fitToHeight property to true
            scrollPane.setFitToHeight(true);
        }*/

        JFXTreeViewPath jfxTreeViewPath = new JFXTreeViewPath(projectManagerTree);
        jfxTreeViewPath.getStylesheets().add("/hydra/fxml/css/fullpackstyling.css");

        jfxTreeViewPath.setFocusTraversable(false);
        jfxTreeViewPath.setFitToWidth(true);

        // change color + size for the path
        projectManagerTree
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        observable -> {
                            for (Node node : ((HBox) jfxTreeViewPath.getContent()).getChildren()) {
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

        //TODO Add filter field

        /*treeVBox.getChildren().clear();
        treeVBox.getChildren().addAll(jfxTreeViewPath, projectManagerTree, filterField);*/
        //VBox.setVgrow(projectManagerTree, Priority.ALWAYS);

    }

    private void setActionFile() {

    }
}
