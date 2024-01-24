package tms3.aditi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import tms3.aditi.model.RequestPayload;
import tms3.tc.DeviceStore;
import tms3.tc.PositionsStore;
import tms3.tc.model.Position;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class AditiTrackingScheduledTask {

    @Autowired
    private PositionsStore positionsStore;

    @Autowired
    private DeviceStore deviceStore;

    @Autowired
    private AditiTrackingClient aditiTrackingClient;

    @Autowired
    private Set<Integer> aditiTrackingDevices;


    @Scheduled(fixedDelayString = "${aditi.tracking.api.data.send.interval}")
    public void sendData() {
        List<Position> positions = getPositionsForAditiTracking();
        log.info("{} positions found for Aditi Tracking API", CollectionUtils.size(positions));
        positions.stream().map(createPayload).forEach(aditiTrackingClient::sendData);
    }

    private List<Position> getPositionsForAditiTracking() {
        return positionsStore.getCurrentPositionList().stream()
                .filter(doesPositionHasValidDeviceId)
                .toList();
    }

    private final Predicate<Position> doesPositionHasValidDeviceId =
            (position) -> aditiTrackingDevices.contains(position.deviceId());

    private final Function<Position, RequestPayload> createPayload = (position) -> {
        final String imeiNo = deviceStore.getImeiForDeviceId(position.deviceId());
        return RequestPayload.getInstance(position, imeiNo);
    };
}
