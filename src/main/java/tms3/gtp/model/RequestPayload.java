package tms3.gtp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import tms3.Utilities;
import tms3.gtp.UpperCasePropertyNamingStrategy;
import tms3.tc.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(UpperCasePropertyNamingStrategy.class)
public record RequestPayload(
        DataElement[] dataElements,
        String vehicleNo,
        String gpsProviderKey,
        String gpsType
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
        final String vehicleNumber = vehicleRegistration.replaceAll("\\s","");

        var dataElement = DataElement.builder()
                .latitude(latitude)
                .longitude(longitude)
                .speed(speed)
                .heading(heading)
                .datetime(datetime)
                .ignStatus(ignStatus)
                .build();


        final String gpsType = "Wired";
        var array = new DataElement[]{dataElement};

        return RequestPayload.builder()
                .dataElements(array)
                .gpsProviderKey(gpsProviderKey)
                .vehicleNo(vehicleNumber)
                .gpsType(gpsType)
                .build();
    }
}
