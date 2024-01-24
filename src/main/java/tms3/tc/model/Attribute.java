package tms3.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Attribute(
    int status,
    boolean ignition,
    boolean charge,
    boolean blocked,
    int batteryLevel,
    int rssi,
    float distance,
    float totalDistance,
    boolean motion,
    long hours
){}
