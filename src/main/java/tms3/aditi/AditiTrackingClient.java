package tms3.aditi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tms3.Utilities;
import tms3.aditi.model.RequestPayload;


@Slf4j
@Component
public class AditiTrackingClient {

    @Autowired
    private RestClient aditiTrackingApiClient;

    public void sendData(final RequestPayload requestPayload){
        log.info("sending data");
        log.info("Request: {}", Utilities.getJsonPayload(requestPayload));
        String response = aditiTrackingApiClient
                .post()
                .body(requestPayload)
                .retrieve()
                .body(String.class);
        log.info("Response: {}", response);
    }
}
