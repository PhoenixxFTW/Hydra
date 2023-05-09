package com.phoenixx.core.snapshots;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 2:58 p.m. [09-05-2023]
 */
public class HTTPObject {

    private String host;
    private String path;

    private String body;

    private final Map<String, String> headers;
    private final Map<String, String> cookies;

    public HTTPObject(String host, String path) {
        this(host, path, new HashMap<>(), new HashMap<>());
    }

    public HTTPObject(String host, String path, Map<String, String> headers, Map<String, String> cookies) {
        this.host = host;
        this.path = path;
        this.headers = headers;
        this.cookies = cookies;
    }

    public HTTPObject setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }
}
