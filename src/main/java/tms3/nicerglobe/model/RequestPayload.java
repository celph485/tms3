package tms3.nicerglobe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tms3.Utilities;
import tms3.nicerglobe.UpperCasePropertyNamingStrategy;
import tms3.tc.model.Position;

import java.util.HashMap;
import java.util.Map;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(UpperCasePropertyNamingStrategy.class)
public record RequestPayload(
        Map<String, DataElement> dataElements,
        String vehicleNo,
        String gpsProviderKey
) {

    public static RequestPayload getInstance(
            final Position position,
            final String vehicleRegistration,
            final String gpsProviderKey
    ){
        final String latitude = String.valueOf(position.latitude());
        final String longitude = String.valueOf(position.longitude());
        final String speed = String.valueOf(position.speed());
        final String heading = String.valueOf(position.course());
        final String datetime = Utilities.convertToGmtDateTimeString(position.deviceTime());
        final String ignStatus = position.attributes().ignition() ? "1" : "0";
        final String location = "";

        var dataElement = DataElement.builder()
                .latitude(latitude)
                .longitude(longitude)
                .speed(speed)
                .heading(heading)
                .datetime(datetime)
                .ignStatus(ignStatus)
                .location(location)
                .build();

        var map = new HashMap<String, DataElement>();
        map.put("DATAELEMENTS", dataElement);

        return RequestPayload.builder()
                .dataElements(map)
                .gpsProviderKey(gpsProviderKey)
                .vehicleNo(vehicleRegistration)
                .build();
    }
}
