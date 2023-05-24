package com.phoenixx.core.snapshots;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.phoenixx.core.loader.FileLoader;
import com.phoenixx.core.loader.parser.impl.XMLParser;
import com.phoenixx.core.script.IScript;
import com.phoenixx.core.snapshots.impl.Snapshot;
import org.apache.commons.text.similarity.LevenshteinDistance;
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

                    System.out.println("SNAPSHOT #" + snapshotID + " LOADED!");
                    //System.out.println(snapshot);
                }
            }
        });

        loader.loadFiles(fileArray);

        //this.getPreReqs(30);

        /*System.out.println("OUTPUTTING SNAPSHOT 30: ");
        System.out.println("SNAPSHOT: " + this.snapshotMap.get(30));*/
    }

    /**
     * //TODO
     * This method will retrieve all pre-requisite requests that were made before this snapshot.
     * Using this, we can find the similarities between the requests in order to find correlations / parameterization.
     *
     * ------ THEORY ------
     * 1) Thread gets called to look through all snapshots before this one (i.e if current snapshot ID is 50, look for snapshots 49 and under)
     * 2) Check if snapshot has any header, or cookie values that match with the data in current snapshot.
     * 3) Since we will have a parser for JSON, and other data types. We can use that to our advantage when searching the body for the request
     *      -> The body will be seperated into key value pairs in order to find the values that match between the 2 snapshots
     * // FIXME Make this support searching headers, cookies and the request body separately
     *
     * searchType: 0 = body, 1 = headers, 2 = cookies
     */
    public Map<Snapshot, List<String>> getPreReqs(int snapshotID) {
        Map<Snapshot, List<String>> data = new HashMap<>();
        Snapshot currentSnapshot = this.getSnapshot(snapshotID);
        if(currentSnapshot == null || (currentSnapshot.getRequest() == null)) {
            return data;
        }

        if(currentSnapshot.getRequest().contentType == null || currentSnapshot.getRequest().getBody() == null || !currentSnapshot.getRequest().contentType.equalsIgnoreCase("application/json")) {
            return data;
        }

        List<JsonObject> firstObjects = new ArrayList<>();
        Map<Snapshot, List<JsonObject>> jsonObjects = new HashMap<>();

        Gson gson = new Gson();
        String jsonData = currentSnapshot.getRequest().getBody();
        System.out.println("CONVERTING MAIN @@@@@@@@@@ JSON DATA TO OBJ: " + jsonData);
        //JsonObject firstObj = gson.fromJson(currentSnapshot.getRequest().getBody(), JsonObject.class);

        JsonElement jsonElement = gson.fromJson(currentSnapshot.getRequest().getBody(), JsonElement.class);

        if (jsonElement.isJsonObject()) {
            firstObjects.add(jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            jsonArray.forEach(element -> firstObjects.add(element.getAsJsonObject()));
        } else {
            System.out.println("The JSON is neither an object nor an array");
            return data;
        }

        // Loop through all snapshots before the current one
        for(int i = snapshotID - 1; i >= 0; i--) {
            Snapshot otherSnapshot = this.getSnapshot(i);

            if (otherSnapshot == null || (otherSnapshot.getResponse() == null || otherSnapshot.getResponse().contentType == null || otherSnapshot.getResponse().getBody() == null || !otherSnapshot.getResponse().contentType.equalsIgnoreCase("application/json"))) {
                //System.out.println("Snapshot #"+i + " request was null @@");
                continue;
            }

            jsonData = otherSnapshot.getResponse().getBody();
            System.out.println("#" + i + ") CONVERT JSON DATA TO OBJ: " + jsonData);
            //JsonObject jsonObject = gson.fromJson(otherSnapshot.getResponse().getBody(), JsonObject.class);

            jsonElement = gson.fromJson(jsonData, JsonElement.class);

            System.out.println("JSON OBJ " + jsonElement);
            if (jsonElement.isJsonObject()) {
                jsonObjects.put(otherSnapshot, new ArrayList<>(Collections.singleton(jsonElement.getAsJsonObject())));
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                jsonArray.forEach(element -> {
                    List<JsonObject> listObjects = new ArrayList<>();
                    if(jsonObjects.containsKey(otherSnapshot)) {
                        listObjects = jsonObjects.get(otherSnapshot);
                    }
                    listObjects.add(element.getAsJsonObject());
                    jsonObjects.put(otherSnapshot, listObjects);
                });
            } else {
                System.out.println("The JSON is neither an object nor an array");
            }
            //System.out.println("FULL SNAPSHOT: " + otherSnapshot.toString());
        }

        int threshold = 3;
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        // Loop through the snapshot that will be used to compare to the others
        for (JsonObject firstObj : firstObjects) {
            // Loop through the searching snapshots (all snapshots that came before the one the user selected)
            for (Snapshot searchingSnapshot : jsonObjects.keySet()) {
                // Since the body data can have an array element, we loop for multiple json objects just in case
                for (JsonObject secondObj : jsonObjects.get(searchingSnapshot)) {
                    // Now the fun begins, this is where the elements / values from the first snapshot are looped over
                    for (Map.Entry<String, JsonElement> entry1 : firstObj.entrySet()) {
                        for (Map.Entry<String, JsonElement> entry2 : secondObj.entrySet()) {
                            if (entry1.getKey().equals(entry2.getKey())) {
                                if (entry1.getValue().isJsonPrimitive() && entry2.getValue().isJsonPrimitive()) {
                                    String value1 = entry1.getValue().getAsString();
                                    String value2 = entry2.getValue().getAsString();

                                    int distance = levenshteinDistance.apply(value1, value2);
                                    if(distance <= threshold) {
                                        if(!data.containsKey(searchingSnapshot)) {
                                            data.put(searchingSnapshot, new ArrayList<>());
                                        }
                                        List<String> matched = data.get(searchingSnapshot);
                                        matched.add(value1);
                                        data.put(searchingSnapshot, matched);
                                        System.out.println("\nSNAPSHOTS #" + currentSnapshot.getID() + " -> #" + searchingSnapshot.getID() + " | Key: " + entry1.getKey() + ", Levenshtein Distance: " + distance + " val1: " + value1 + " val2: " + value2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //System.out.println("ALL MATCHES: " + matches.size());
        //matches.forEach(s -> System.out.println(" -> " + s));

        return data;
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
