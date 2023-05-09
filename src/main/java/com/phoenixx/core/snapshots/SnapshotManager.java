package com.phoenixx.core.snapshots;

import com.phoenixx.core.script.IScript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:56 a.m. [09-05-2023]
 */
public class SnapshotManager {

    private final List<ISnapshot> snapshotList;

    public SnapshotManager(IScript script) {
        this.snapshotList = new ArrayList<>();

        // Load the snapshots from the data folder
        this.loadSnapshots(new File(script.getScriptFile().getScriptFolder(), "data"));
    }

    private void loadSnapshots(File folder) {
        System.out.println("Snapshot path locatoin: " + folder.getAbsolutePath());
        final String regex = "snapshot_\\d+\\.xml";

        for (File file: Objects.requireNonNull(folder.listFiles())) {
            if(file.getName().matches(regex)) {
                System.out.println("Found snapshot file: " + file.getName());
            }
        }
    }
}
