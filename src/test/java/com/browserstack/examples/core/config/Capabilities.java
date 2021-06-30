package com.browserstack.examples.core.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;


public class Capabilities {

    private final Map<String, Object> capabilityMap = new LinkedHashMap<>();

    @JsonAnySetter
    public void setCapabilities(String key, Object value) {
        this.capabilityMap.put(key, value);
    }

    public Map<String, Object> getCapabilityMap() {
        return capabilityMap;
    }
}
