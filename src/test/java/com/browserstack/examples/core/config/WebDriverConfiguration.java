package com.browserstack.examples.core.config;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebDriverConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfiguration.class);

    private String testEndpoint;

    private DriverType driverType;

    @JsonProperty("onPremDriver")
    private OnPremDriverConfig onPremDriverConfig;

    @JsonProperty("onPremGridDriver")
    private RemoteDriverConfig onPremGridDriverConfig;

    @JsonProperty("cloudDriver")
    private RemoteDriverConfig cloudDriverConfig;

    @JsonProperty("specific_capabilities")
    private SpecificCapabilities specificCapabilities;

    public List<Platform> getActivePlatforms() {
        List<Platform> activePlatforms = Collections.emptyList();
        switch (driverType) {
            case onPremDriver:
                activePlatforms = onPremDriverConfig.getPlatforms();
                break;
            case onPremGridDriver:
                activePlatforms = onPremGridDriverConfig.getPlatforms();
                break;
            case cloudDriver:
                activePlatforms = cloudDriverConfig.getPlatforms();
                break;
        }
        return activePlatforms;
    }

    public String getTestEndpoint() {
        return testEndpoint;
    }

    public void setTestEndpoint(String testEndpoint) {
        this.testEndpoint = testEndpoint;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public OnPremDriverConfig getOnPremDriverConfig() {
        return onPremDriverConfig;
    }

    public void setOnPremDriverConfig(OnPremDriverConfig onPremDriverConfig) {
        this.onPremDriverConfig = onPremDriverConfig;
    }

    public RemoteDriverConfig getOnPremGridDriverConfig() {
        return onPremGridDriverConfig;
    }

    public void setOnPremGridDriverConfig(RemoteDriverConfig onPremGridDriverConfig) {
        this.onPremGridDriverConfig = onPremGridDriverConfig;
    }

    public RemoteDriverConfig getCloudDriverConfig() {
        return cloudDriverConfig;
    }

    public void setCloudDriverConfig(RemoteDriverConfig cloudDriverConfig) {
        this.cloudDriverConfig = cloudDriverConfig;
    }

    public SpecificCapabilities getSpecificCapabilities() {
        return specificCapabilities;
    }

    public void setSpecificCapabilities(SpecificCapabilities specificCapabilities) {
        this.specificCapabilities = specificCapabilities;
    }
}
