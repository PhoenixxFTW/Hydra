package com.phoenixx.core.snapshots.data;

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
    public Map<String, String> keyPairData = new HashMap<>();
}
