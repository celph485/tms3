package tms3.fretron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tms3.Utilities;
import tms3.fretron.model.RequestPayload;

import java.util.List;

@Slf4j
@Component
public class FreTronClient {

    @Autowired
    private RestClient freTronApiClient;

    public void sendData(List<RequestPayload> requestPayloads){
        log.info("sending data to FreTron API");
        log.info("Request: {}", Utilities.getJsonPayload(requestPayloads));
        String response = freTronApiClient
                .post()
                .body(requestPayloads)
                .retrieve()
                .body(String.class);
        log.info("Response: {}", response);
    }
}
