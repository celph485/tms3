package tms3.nicerglobe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tms3.Utilities;
import tms3.nicerglobe.model.RequestPayload;

@Slf4j
@Component
public class NicerGlobeClient {

    @Autowired
    private RestClient nicerGlobeApiClient;

    public void sendData(RequestPayload requestPayload){
        log.info("sending data to NicerGlobe API");
        log.info("Request: {}", Utilities.getJsonPayload(requestPayload));
        String response = nicerGlobeApiClient
                .post()
                .body(requestPayload)
                .retrieve()
                .body(String.class);
        log.info("Response: {}", response);
    }
}
