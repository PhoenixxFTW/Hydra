package com.phoenixx.core.snapshots.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:01 AM [16-05-2023]
 */
public class DataObj {
    public String fullBody;
    // FIXME Will not support key / value pair with a data object as the pair
    public Map<String, Object> keyPairData = new HashMap<>();

    public JsonObject jsonObject;

    public DataObj(String fullBody) {
        this.setBody(fullBody);
    }

    public DataObj() {
        super();
    }

    public void setBody(String fullBody) {
        this.fullBody = fullBody;
        this.convertToMap();
    }

    public void convertToMap() {
        Type mapType = new TypeToken<Map<String, Map>>(){}.getType();
        Gson gson = new Gson();
        keyPairData = gson.fromJson(this.fullBody, new TypeToken<HashMap<String, Object>>() {}.getType());
        jsonObject = gson.fromJson(this.fullBody, JsonObject.class);
    }
}
