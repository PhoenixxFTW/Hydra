package com.phoenixx.core.protocol;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 9:11 a.m. [09-05-2023]
 */
public enum ProtocolTypes {

    WEB(0, "Web - HTTP/HTML"),
    ORACLE_NCA(1, "Oracle NCA");

    private final int ID;
    private final String name;

    ProtocolTypes(int id, String name) {
        this.ID = id;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    /**
     * Retrieves the {@link ProtocolTypes} with the specified name value
     * @param name The name to search for
     *
     * @return {@link ProtocolTypes}
     */
    public static ProtocolTypes getFromName(String name) {
        for (ProtocolTypes value : ProtocolTypes.values()) {
            if(value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
