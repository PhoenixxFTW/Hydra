package com.phoenixx.core.snapshots.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phoenixx.core.snapshots.HTTPObject;
import com.phoenixx.core.snapshots.ISnapshot;
import com.phoenixx.core.snapshots.data.TreeNode;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 3:08 p.m. [09-05-2023]
 */
public class Snapshot extends ISnapshot {

    private final int id;
    private final HTTPObject request;
    private final HTTPObject response;

    public Snapshot(int id, HTTPObject request, HTTPObject response) {
        this.id = id;
        this.request = request;
        this.response = response;

        //this.test();
    }

    private void test() {
        // Read JSON file
        String jsonString = ""; // JSON content as a string
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        // Construct tree from JSON data
        TreeNode tree = constructTree(jsonObject);

     /*   // Compare multiple trees
        TreeNode tree1 = constructTree(jsonObject1);
        TreeNode tree2 = constructTree(jsonObject2);

        LevenshteinDistance distance = new LevenshteinDistance();
        int similarityScore = calculateSimilarity(tree1, tree2, distance);
        System.out.println("Similarity score: " + similarityScore);*/
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public HTTPObject getRequest() {
        return this.request;
    }

    @Override
    public HTTPObject getResponse() {
        return this.response;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("------ Request #").append(this.getID()).append(" ------").append("\n");
        builder.append("Request: \n").append(this.getRequest()).append("\n\n");
        builder.append("Response: \n").append(this.getResponse()).append("\n");
        return builder.toString();
    }

    public static TreeNode constructTree(JsonObject jsonObject) {
        TreeNode root = new TreeNode(null);
        buildTree(root, jsonObject);
        return root;
    }

    public static void buildTree(TreeNode node, JsonObject jsonObject){
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            TreeNode child = new TreeNode(value.toString());
            node.children.add(child);
            if (value instanceof JsonObject) {
                buildTree(child, (JsonObject) value);
            }
        }
    }

    public static int calculateSimilarity(TreeNode node1, TreeNode node2, LevenshteinDistance distance) {
        if (node1.value == null || node2.value == null) {
            return 0; // Not comparable
        }

        int similarity = 0;
        if (distance.apply(node1.value, node2.value) < 3) { // Adjust the threshold as needed
            similarity += 1;
        }

        for (int i = 0; i < node1.children.size(); i++) {
            TreeNode child1 = node1.children.get(i);
            TreeNode child2 = node2.children.get(i);
            similarity += calculateSimilarity(child1, child2, distance);
        }

        return similarity;
        /*if (node1.value == null || node2.value == null) {
            return 0; // Not comparable
        }

        return distance.apply(node1.value, node2.value);*/
    }
}
