package com.phoenixx.core.snapshots;

import com.phoenixx.core.loader.FileLoader;
import com.phoenixx.core.loader.parser.impl.XMLParser;
import com.phoenixx.core.script.IScript;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

        List<String> snapshotFiles = new ArrayList<>();
        for (File file: Objects.requireNonNull(folder.listFiles())) {
            if(file.getName().matches(regex)) {
                snapshotFiles.add(folder.getAbsolutePath() + "/" + file.getName());
            }
        }

        String[] fileArray = snapshotFiles.toArray(new String[0]);
        System.out.println(Arrays.toString(fileArray));

        FileLoader<XMLParser> loader = new FileLoader<>(new XMLParser(), 1);

        loader.setOnFinish(xmlParser -> {
            Document document = xmlParser.getDataObject();
            //String hostName = document.getElementsByTagName("HTTPTask").item(0).getTextContent();

            //System.out.println("Host name: " + hostName);
        });

        loader.loadFiles(fileArray);
    }
}
