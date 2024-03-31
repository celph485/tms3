package tms3.gtp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import tms3.gtp.model.RequestPayload;
import tms3.tc.DeviceStore;
import tms3.tc.PositionsStore;
import tms3.tc.model.Position;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class GlobalTrackingPlatformScheduledTask {

    @Autowired
    private PositionsStore positionsStore;

    @Autowired
    private DeviceStore deviceStore;

    @Autowired
    private GlobalTrackingPlatformClient globalTrackingPlatformClient;

    @Autowired
    private Set<Integer> globalTrackingPlatformDevices;

    @Value("${global.tracking.platform.api.secret.key}")
    private String gpsProviderKey;

    @Scheduled(fixedDelayString = "${global.tracking.platform.api.data.send.interval}")
    public void sendData() {
        List<Position> positions = getPositionsForNicerGlobe();
        log.info("{} positions found for Global Tracking Platform API", CollectionUtils.size(positions));
        positions.stream().map(createPayload).forEach(globalTrackingPlatformClient::sendData);
    }

    private List<Position> getPositionsForNicerGlobe() {
        return positionsStore.getCurrentPositionList().stream()
                .filter(doesPositionHasValidDeviceId)
                .toList();
    }

    private final Predicate<Position> doesPositionHasValidDeviceId =
            (position) -> globalTrackingPlatformDevices.contains(position.deviceId());

    private final Function<Position, RequestPayload> createPayload = (position) -> {
        final String vehicleRegNum = deviceStore.getRegistrationNumberForDeviceId(position.deviceId());
        return RequestPayload.getInstance(position, vehicleRegNum, gpsProviderKey);
    };
}
