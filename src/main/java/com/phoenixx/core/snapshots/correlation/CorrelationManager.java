package com.phoenixx.core.snapshots.correlation;

import com.google.gson.*;
import com.phoenixx.core.snapshots.SnapshotManager;
import com.phoenixx.core.snapshots.data.CorrelationContext;
import com.phoenixx.core.snapshots.data.QueryObj;
import com.phoenixx.core.snapshots.impl.Snapshot;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 1:39 PM [29-05-2023]
 */
public class CorrelationManager {

    private final SnapshotManager snapshotManager;

    // Number of edits a value can have. 0 being none. Anything larger than this will skew the similarity search
    public final static int THRESHOLD = 0;

    // TODO Change this so we can change this in the scan settings
    private final int MIN_THRESHOLD = 0;
    private final int MAX_THRESHOLD = 10;

    // Map of snapshots ID's with their corresponding correlation's
    private final Map<Integer, Map<Snapshot, List<CorrelationContext>>> correlationCache;
    // Any of the values in this list will be ignored from the correlation proccess
    private final List<String> correlationFilter = new ArrayList<>();

    public CorrelationManager(SnapshotManager snapshotManager) {
        this.snapshotManager = snapshotManager;
        this.correlationCache = new HashMap<>();

        this.correlationFilter.add("Content-Type");
        this.correlationFilter.add("language");
        this.correlationFilter.add("Accept-Language");
    }

    /**
     * Scans for any similar header, cookie or body values found in any other snapshots from the one provided.
     *
     * @param snapshot The given {@link Snapshot} to find correlations for
     * @param useCache Bool value which decides whether this method should look for correlations in the cache or search for them.
     *                 (Disable this if you want to do a fresh scan for correlations)
     */
    public Map<Snapshot, List<CorrelationContext>> scanCorrelations(Snapshot snapshot, boolean useCache) {
        // If the cache contains the data, we return from it
        if(useCache && this.correlationCache.containsKey(snapshot.getID())) {
            return this.correlationCache.get(snapshot.getID());
        }


        Gson gson = new Gson();
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        Map<Snapshot, List<CorrelationContext>> allCorrelations = new HashMap<>();

        List<JsonObject> firstObjects = new ArrayList<>();
        String jsonData = snapshot.getRequest().getBody();
        //System.out.println("CONVERTING MAIN @@@@@@@@@@ JSON DATA TO OBJ: " + jsonData);
        //JsonObject firstObj = gson.fromJson(currentSnapshot.getRequest().getBody(), JsonObject.class);

        if(this.isValidJSON(jsonData)) {
            firstObjects = this.jsonStringToObjects(jsonData, gson);
        } else {
            System.out.println("Snapshot: " + snapshot.getID() + " claimed to have JSON body, but could not convert data to Json!");
        }

        // Loop through all snapshots before the current one
        for(int i = 0; i < this.snapshotManager.getSnapshotMap().size(); i++) {
            Snapshot otherSnapshot = this.snapshotManager.getSnapshot(i);
            List<CorrelationContext> correlations = new ArrayList<>();

            if ((otherSnapshot == null || snapshot.getRequest() == null) || snapshot.getID() == otherSnapshot.getID()) {
                continue;
            }

            // Correlate Headers
            correlations.addAll(correlateQueryObjs(snapshot, otherSnapshot, snapshot.getRequest().getHeaders().values(), otherSnapshot.getResponse().getHeaders().values(), levenshteinDistance, CorrelationType.HEADERS));

            // Correlate Cookies
            correlations.addAll(correlateQueryObjs(snapshot, otherSnapshot, snapshot.getRequest().getCookies().values(), otherSnapshot.getResponse().getCookies().values(), levenshteinDistance, CorrelationType.COOKIE));

            allCorrelations.put(otherSnapshot, correlations);

            // TODO This only support json data types, we need to add support for normal data bodies
            if(snapshot.getRequest().contentType == null || snapshot.getRequest().getBody() == null || !snapshot.getRequest().contentType.equalsIgnoreCase("application/json")) {
                continue;
            }

            // TODO This only support json data types, we need to add support for normal data bodies
            if ((otherSnapshot.getResponse() == null || otherSnapshot.getResponse().contentType == null || otherSnapshot.getResponse().getBody() == null || !otherSnapshot.getResponse().contentType.equalsIgnoreCase("application/json"))) {
                continue;
            }

            jsonData = otherSnapshot.getResponse().getBody();
            //System.out.println("#" + i + " ID: " + otherSnapshot.getID() + ") CONVERT JSON DATA TO OBJ: " + jsonData);
            //JsonObject jsonObject = gson.fromJson(otherSnapshot.getResponse().getBody(), JsonObject.class);
            List<JsonObject> jsonObjects;

            if(this.isValidJSON(jsonData)) {
                jsonObjects = this.jsonStringToObjects(jsonData, gson);
            } else {
                System.out.println("Snapshot: " + otherSnapshot.getID() + " claimed to have JSON body, but could not convert data to Json!");
                continue;
            }

            //TODO This only supports JSON bodies

            // Loop through the snapshot that will be used to compare to the others
            for (JsonObject firstObj: firstObjects) {
                // Since the body data can have an array element, we loop for multiple json objects just in case
                for (JsonObject secondObj : jsonObjects) {
                    // Now the fun begins, this is where the elements / values from the first snapshot are looped over
                    for (Map.Entry<String, JsonElement> entry1 : firstObj.entrySet()) {
                        for (Map.Entry<String, JsonElement> entry2 : secondObj.entrySet()) {
                            if (entry1.getKey().equals(entry2.getKey())) {
                                if (entry1.getValue().isJsonPrimitive() && entry2.getValue().isJsonPrimitive()) {
                                    String value1 = entry1.getValue().getAsString();
                                    String value2 = entry2.getValue().getAsString();

                                    int distance = levenshteinDistance.apply(value1, value2);
                                    if(distance <= THRESHOLD) {
                                        int accuracy = 100 - ((distance - MIN_THRESHOLD) * 100) / (MAX_THRESHOLD - MIN_THRESHOLD);
                                        correlations.add(new CorrelationContext(entry1.getKey(), entry2.getKey(), value1, value2, accuracy, CorrelationType.BODY));
                                        System.out.println("\nSNAPSHOTS #" + snapshot.getID() + " -> #" + otherSnapshot.getID() + " | Key: " + entry1.getKey() + ", Levenshtein Distance: " + distance + " val1: " + value1 + " val2: " + value2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            allCorrelations.put(otherSnapshot, correlations);
        }

        return allCorrelations;
    }

    private List<CorrelationContext> correlateQueryObjs(Snapshot snapshot, Snapshot otherSnapshot, Collection<QueryObj> queryObjsOne, Collection<QueryObj> queryObjsTwo, LevenshteinDistance levenshteinDistance, CorrelationType correlationType) {
        List<CorrelationContext> correlations = new ArrayList<>();

        // Loop through request headers/cookies of original snapshot
        for(QueryObj requestQuery: queryObjsOne) {
            // Loop through response headers/cookies of other snapshot we're checking
            for(QueryObj responseQuery: queryObjsTwo) {
                // Skip any correlations if the key is in the filter list
                if(this.correlationFilter.contains(requestQuery.getKey()) || this.correlationFilter.contains(responseQuery.getKey())) {
                    continue;
                }

                int distance = levenshteinDistance.apply(requestQuery.getVal(), responseQuery.getVal());
                if(distance <= THRESHOLD) {
                    int accuracy = 100 - ((distance - MIN_THRESHOLD) * 100) / (MAX_THRESHOLD - MIN_THRESHOLD);
                    correlations.add(new CorrelationContext(requestQuery.getKey(), responseQuery.getKey(), requestQuery.getVal(), responseQuery.getVal(), accuracy, correlationType));
                    System.out.println("SNAPSHOTS #" + snapshot.getID() + " -> #" + otherSnapshot.getID() + " | Key: " + requestQuery.getKey() + ", Levenshtein Distance: " + distance + " val1: " +  requestQuery.getVal() + " val2: " +  responseQuery.getVal());
                }
            }
        }
        return correlations;
    }

    private List<JsonObject> jsonStringToObjects(String jsonData, Gson gson) {
        List<JsonObject> jsonObjects = new ArrayList<>();

        JsonElement jsonElement = gson.fromJson(jsonData, JsonElement.class);
        //System.out.println("JSON OBJ " + jsonElement);
        if (jsonElement.isJsonObject()) {
            jsonObjects.add(jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            jsonArray.forEach(element -> jsonObjects.add(element.getAsJsonObject()));
        } else {
            System.out.println("ERROR: The JSON is neither an object nor an array");
        }

        return jsonObjects;
    }

    public boolean isValidJSON(String json) {
        try {
            JsonParser.parseString(json);
        } catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

    public enum CorrelationType {
        HEADERS, COOKIE, BODY
    }
}
