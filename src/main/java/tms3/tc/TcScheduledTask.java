package tms3.tc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TcScheduledTask {

    @Autowired
    private PositionClient positionClient;

    @Autowired
    private DeviceClient deviceClient;

    @Autowired
    private PositionsStore positionsStore;

    @Autowired
    private DeviceStore deviceStore;

    @Scheduled(fixedDelayString = "${tc.api.data.fetch.interval}")
    public void updateTrackingData(){
        log.info("Updating stores");
        updateDeviceData();
        updatePositionData();
    }

    private void updatePositionData(){
        log.info("Updating position store");
        try{
            positionsStore.updatePositions(positionClient.getPositions());
        }catch (Exception e){
            log.error("Error while updating position store", e);
        }
    }

    private void updateDeviceData(){
        log.info("Updating device store");
        try{
            deviceStore.updateDeviceStore(deviceClient.getDevices());
        }catch (Exception e){
            log.error("Error while updating position store", e);
        }
    }
}