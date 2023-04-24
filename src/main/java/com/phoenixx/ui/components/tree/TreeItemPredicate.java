package com.phoenixx.ui.components.tree;

import javafx.scene.control.TreeItem;

import java.util.function.Predicate;

/**
 * Adapted from <a href="https://github.com/sshahine/JFoenix/blob/master/demo/src/main/java/demos/components/TreeViewDemo.java">JFoenix Tree View Demo</a>
 *
 * @author JFoenix
 * @project Hydra
 * @since 11:15 AM [24-04-2023]
 */
@FunctionalInterface
public interface TreeItemPredicate<T> {

    boolean test(TreeItem<T> parent, T value);

    static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
        return (parent, value) -> predicate.test(value);
    }
}