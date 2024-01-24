package tms3.tc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tms3.tc.model.Device;

import java.util.List;

@Slf4j
@Component
public class DeviceClient {
    @Value("${tc.api.url}")
    private String tcApiUrl;

    @Value("${tc.api.endpoint.device}")
    private String deviceEndPoint;

    @Autowired
    private WebClient tcApiClient;

    public List<Device> getDevices(){
        log.info("Fetching devices from TC");
        final String endPointUrl = tcApiUrl+deviceEndPoint;
        Mono<List<Device>> response = tcApiClient
                .get()
                .uri(endPointUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Device>>() {});
        List<Device> devices = response.block();
        log.info("Received {} devices from TC", CollectionUtils.size(devices));
        devices.forEach(d -> log.debug("{}",d));
        return devices;
    }
}
