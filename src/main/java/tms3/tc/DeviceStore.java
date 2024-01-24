package tms3.tc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import tms3.tc.model.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DeviceStore {

    private Map<Integer, Device> deviceById;

    private DeviceStore(){
        log.info("Initializing position store");
        deviceById = new HashMap<>();
    }

    public static DeviceStore getInstance(){
        return new DeviceStore();
    }

    public void updateDeviceStore(final List<Device> devices){
        log.info("Updating device store with {} devices", CollectionUtils.size(devices));
        if(CollectionUtils.isNotEmpty(devices)){
            this.deviceById = devices.stream()
                    .collect(Collectors.toUnmodifiableMap(Device::id, Function.identity()));
            log.info("Device store updated with {} devices", CollectionUtils.size(devices));
        }
    }

    public String getImeiForDeviceId(final Integer deviceId){
        return MapUtils.getObject(deviceById, deviceId, Device.defaultDevice()).uniqueId();
    }

    public String getRegistrationNumberForDeviceId(final Integer deviceId){
        return MapUtils.getObject(deviceById, deviceId, Device.defaultDevice()).name();
    }
}