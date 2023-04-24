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
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
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

    private VugenScript vugenScript;

    public void initialize() {
        // Start off with the loading page
    }

    public void setVugenScript(VugenScript vugenScript) {
        this.vugenScript = vugenScript;

        final JFXTreeView projectManagerTree = new JFXTreeView<>();

        FilterableTreeItem<String> rootNode = new FilterableTreeItem<>(this.vugenScript.getScriptFolder().getName());
        rootNode.setExpanded(true);

        // TODO Load all script data into window
        for (Action action : this.vugenScript.getActions()) {
            FilterableTreeItem<String> actionNode = new FilterableTreeItem<>(action.getActionName());

            for(Transaction transaction: action.getTransactions()) {
                FilterableTreeItem<String> transactionNode = new FilterableTreeItem<>(transaction.getTransactionName());

                for(Step step: transaction.getSteps()) {
                    FilterableTreeItem<String> stepNode = new FilterableTreeItem<>(transaction.getTransactionName());
                }

                actionNode.getInternalChildren().add(transactionNode);
            }

            rootNode.getInternalChildren().add(actionNode);
        }

        TextField filterField = new JFXTextField();
        rootNode.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            if (filterField.getText() == null || filterField.getText().isEmpty())
                return null;
            return TreeItemPredicate.create(actor -> actor.contains(filterField.getText()));
        }, filterField.textProperty()));

        projectManagerTree.setRoot(rootNode);

        treeVBox.getChildren().addAll(new JFXTreeViewPath(projectManagerTree), projectManagerTree, filterField);
        VBox.setVgrow(projectManagerTree, Priority.ALWAYS);
    }

    private void setActionFile() {

    }
}
