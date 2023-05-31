package com.phoenixx.core.snapshots.impl;

import com.phoenixx.core.snapshots.data.HTTPObject;
import com.phoenixx.core.snapshots.ISnapshot;

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
}
