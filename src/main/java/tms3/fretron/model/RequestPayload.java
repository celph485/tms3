package tms3.fretron.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tms3.tc.model.Position;

import static tms3.Utilities.convertToIstDateTimeEpoch;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestPayload(
        String imei,
        float latitude,
        float longitude,
        String vendor,
        long time,
        int speed
) {
    public static RequestPayload getInstance(final Position position, final String imeiNo, final String vendor){
        return RequestPayload.builder()
                .imei(imeiNo)
                .vendor(vendor)
                .latitude(position.latitude())
                .longitude(position.longitude())
                .time(convertToIstDateTimeEpoch(position.deviceTime()))
                .speed(Math.round(position.speed()))
                .build();
    }
}
