package tms3.gtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tms3.Utilities;
import tms3.gtp.model.RequestPayload;

@Slf4j
@Component
public class GlobalTrackingPlatformClient {

    @Autowired
    private RestClient globalTrackingPlatformApiClient;

    public void sendData(RequestPayload requestPayload){
        log.info("sending data to Global Tracking Platform API");
        log.info("Request: {}", Utilities.getJsonPayload(requestPayload));
        String response = globalTrackingPlatformApiClient
                .post()
                .body(requestPayload)
                .retrieve()
                .body(String.class);
        log.info("Response: {}", response);
    }
}
