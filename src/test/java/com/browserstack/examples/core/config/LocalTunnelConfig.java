package com.browserstack.examples.core.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocalTunnelConfig {

    @JsonProperty("local_options")
    private final Map<String, String> localOptions = new LinkedHashMap<>();
    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Map<String, String> getLocalOptions() {
        return localOptions;
    }

    @JsonAnySetter
    public void setLocalOption(String key, String value) {
        this.localOptions.put(key, value);
    }

}
