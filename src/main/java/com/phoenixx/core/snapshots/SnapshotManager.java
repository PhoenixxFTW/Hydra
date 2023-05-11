package com.phoenixx.core.snapshots;

import com.phoenixx.core.loader.FileLoader;
import com.phoenixx.core.loader.parser.impl.XMLParser;
import com.phoenixx.core.script.IScript;
import com.phoenixx.core.snapshots.impl.Snapshot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:56 a.m. [09-05-2023]
 */
public class SnapshotManager {

    private final Map<Integer,ISnapshot> snapshotMap;

    private final static String SNAPSHOT_REGEX = "snapshot_\\d+\\.xml";

    public SnapshotManager(IScript script) {
        this.snapshotMap = new TreeMap<>();

        // Load the snapshots from the data folder
        this.loadSnapshots(new File(script.getScriptFile().getScriptFolder(), "data"));
    }

    private void loadSnapshots(File folder) {
        System.out.println("Snapshot path location: " + folder.getAbsolutePath());

        List<String> snapshotFiles = new ArrayList<>();
        for (File file: Objects.requireNonNull(folder.listFiles())) {
            if(file.getName().matches(SNAPSHOT_REGEX)) {
                //System.out.println("Found snapshot file: " + file.getName());
                snapshotFiles.add(folder.getAbsolutePath() + "\\" + file.getName());
            }
        }
        System.out.println("Total snapshots found: " + snapshotFiles.size());

        String[] fileArray = snapshotFiles.toArray(new String[0]);
        System.out.println(Arrays.toString(fileArray));

        FileLoader<XMLParser> loader = new FileLoader<>(new XMLParser(), 1);

        loader.setOnFinish(xmlParser -> {
            Document document = xmlParser.getDataObject();

            //System.out.println("Parsed and loaded: " + xmlParser.getFileName());
            if(document == null) {
                System.out.println("FAILURE! " + xmlParser.getFileName() + " DOCUMENT WAS NULL @@@@@ ");
                return;
            }

            Element snapshotElement = (Element) document.getElementsByTagName("HTTPSnapshot").item(0);
            int snapshotID = Integer.parseInt(snapshotElement.getAttribute("id"));

            NodeList httpTaskList = document.getElementsByTagName("HTTPTask");
            for (int i = 0; i < httpTaskList.getLength(); i++) {
                Node httpTaskNode = httpTaskList.item(i);
                if (httpTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element httpTaskElement = (Element) httpTaskNode;
                    String hostname = httpTaskElement.getAttribute("hostname");
                    String url = httpTaskElement.getAttribute("url");

                    NodeList httpRequestList = httpTaskElement.getElementsByTagName("HTTPRequest");
                    NodeList httpResponseList = httpTaskElement.getElementsByTagName("HTTPResponse");

                    HTTPObject requestObj = HTTPObject.buildObject(hostname, url, httpRequestList);
                    HTTPObject responseObj = HTTPObject.buildObject(hostname, url, httpResponseList);

                    Snapshot snapshot = new Snapshot(snapshotID, requestObj, responseObj);
                    this.snapshotMap.put(snapshotID, snapshot);

                    //System.out.println("SNAPSHOT #" + snapshotID + " LOADED!");
                    //System.out.println(snapshot);
                }
            }
        });

        loader.loadFiles(fileArray);

        /*System.out.println("OUTPUTTING SNAPSHOT 30: ");
        System.out.println("SNAPSHOT: " + this.snapshotMap.get(30));*/
    }

    /**
     * Retrieves and returns the {@link Snapshot} with the given ID
     *
     * @param id The id of the {@link Snapshot} to look for
     * @return {@link Snapshot}
     */
    public Snapshot getSnapshot(int id) {
        return (Snapshot) this.getSnapshotMap().getOrDefault(id, null);
    }

    public Map<Integer, ISnapshot> getSnapshotMap() {
        return this.snapshotMap;
    }
}
