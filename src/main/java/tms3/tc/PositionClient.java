package tms3.tc;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tms3.tc.model.Position;

import java.util.List;

@Slf4j
@Component
public class PositionClient {

    @Value("${tc.api.url}")
    private String tcApiUrl;

    @Value("${tc.api.endpoint.position}")
    private String positionEndPoint;

    @Autowired
    private WebClient tcApiClient;

    public List<Position> getPositions(){
        log.info("Fetching positions from TC");
        final String endPointUrl = tcApiUrl+positionEndPoint;
        Mono<List<Position>> response = tcApiClient
                .get()
                .uri(endPointUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Position>>() {});
        List<Position> positions = response.block();
        log.info("Received {} positions from TC", CollectionUtils.size(positions));
        positions.forEach(p -> log.debug("{}",p));
        return positions;
    }
}
