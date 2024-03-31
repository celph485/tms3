package tms3.nicerglobe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tms3.nicerglobe.UpperCasePropertyNamingStrategy;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(UpperCasePropertyNamingStrategy.class)
public record DataElement(
        String latitude,
        String longitude,
        String speed,
        String heading,
        String datetime,
        String ignStatus,
        String location
) {
}
