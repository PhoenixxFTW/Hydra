package com.phoenixx.core.snapshots.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 10:55 AM [15-05-2023]
 */
public class TreeNode {
    public final String value;
    public final List<TreeNode> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
}
