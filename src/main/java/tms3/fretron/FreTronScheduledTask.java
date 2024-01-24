package tms3.fretron;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import tms3.fretron.model.RequestPayload;
import tms3.tc.DeviceStore;
import tms3.tc.PositionsStore;
import tms3.tc.model.Position;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class FreTronScheduledTask {

    @Autowired
    private PositionsStore positionsStore;

    @Autowired
    private DeviceStore deviceStore;

    @Autowired
    private FreTronClient freTronClient;

    @Autowired
    private Set<Integer> freTronDevices;

    @Value("${fre.tron.api.server.vendor}")
    private String vendorName;


    @Scheduled(fixedDelayString = "${fre.tron.api.data.send.interval}")
    public void sendData() {
        List<Position> positions = getPositionsForFreTron();
        log.info("{} positions found for FreTron API", CollectionUtils.size(positions));
        List<RequestPayload> requestPayloadList = positions.stream().map(createPayload).toList();
        freTronClient.sendData(requestPayloadList);
    }

    private List<Position> getPositionsForFreTron() {
        return positionsStore.getCurrentPositionList().stream()
                .filter(doesPositionHasValidDeviceId)
                .toList();
    }

    private final Predicate<Position> doesPositionHasValidDeviceId =
            (position) -> freTronDevices.contains(position.deviceId());

    private final Function<Position, RequestPayload> createPayload = (position) -> {
        final String imeiNo = deviceStore.getImeiForDeviceId(position.deviceId());
        return RequestPayload.getInstance(position, imeiNo, vendorName);
    };
}
