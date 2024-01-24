package tms3.tc.model;

import org.apache.commons.lang3.StringUtils;


public record Device(
        int id,
        String name,
        String uniqueId
) {
    public static Device defaultDevice(){
        return new Device(-1, StringUtils.EMPTY, StringUtils.EMPTY);
    }
}
