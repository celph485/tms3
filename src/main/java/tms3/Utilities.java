package tms3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public final class Utilities {

    public static Set<Integer> createSetFromCsvString(final String csvString){
        if(StringUtils.isEmpty(csvString)){
            return Collections.emptySet();
        }
        return Arrays.stream(StringUtils.split(csvString, ","))
                .map(Integer::valueOf)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static String convertToGmtDateTimeString(final String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);
        ZonedDateTime gmtDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("GMT"));
        return formatter.format(gmtDateTime);
    }

    public static String convertToIstDateTimeString(final String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);
        ZonedDateTime istDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
        return formatter.format(istDateTime);
    }

    public static long convertToIstDateTimeEpoch(final String dateString){
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);
        ZonedDateTime istDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
        return istDateTime.toInstant().toEpochMilli();
    }

    public static String getJsonPayload(Object payload){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
