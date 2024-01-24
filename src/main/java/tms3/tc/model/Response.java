package tms3.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record Response(
        String imei,
        String registration,
        String time,
        float latitude,
        float longitude,
        int altitude,
        float speed,
        float course
) {
}
