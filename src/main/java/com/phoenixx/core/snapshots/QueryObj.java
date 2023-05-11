package com.phoenixx.core.snapshots;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 2:56 PM [11-05-2023]
 */
public class QueryObj {
    private final String key;
    private String val;

    private boolean enabled;

    public QueryObj(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
