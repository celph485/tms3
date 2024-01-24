package tms3.aditi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tms3.Utilities;
import tms3.tc.model.Position;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RequestPayload(
        String imeiNo,
        String time,
        String lattitude,
        String longitude,
        String lattitudeDirection,
        String longitudeDirection,
        String speed,
        String digitalPort1,
        String digitalPort2,
        String digitalPort3,
        String digitalPort4,
        String analogPort1,
        String analogPort2,
        String angle,
        String satellite,
        String batteryVoltage,
        String gpsValidity
) {
    public static RequestPayload getInstance(final Position position, final String imeiNo){
        final String latitude = String.format("%.4f", position.latitude());
        final String longitude = String.format("%.4f", position.longitude());
        return RequestPayload.builder()
                .imeiNo(imeiNo)
                .time(Utilities.convertToIstDateTimeString(position.deviceTime()))
                .lattitude(latitude)
                .longitude(longitude)
                .speed(String.valueOf(position.speed()))
                .lattitudeDirection("N")
                .longitudeDirection("E")
                .angle("0")
                .digitalPort1("0")
                .digitalPort2("0")
                .digitalPort3("0")
                .digitalPort4("0")
                .analogPort1("0")
                .analogPort2("0")
                .satellite("0")
                .batteryVoltage("0")
                .gpsValidity("A")
                .build();
    }
}
