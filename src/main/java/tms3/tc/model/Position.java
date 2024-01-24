package tms3.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Position (
    int id,
    Attribute attributes,
    int deviceId,
    String protocol,
    String serverTime,
    String deviceTime,
    String fixTime,
    boolean outdated,
    boolean valid,
    float latitude,
    float longitude,
    int altitude,
    float speed,
    float course,
    String address,
    int accuracy
){}
