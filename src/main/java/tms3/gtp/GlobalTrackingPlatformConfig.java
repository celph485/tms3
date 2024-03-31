package tms3.gtp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import tms3.Utilities;

import java.util.Set;

@Slf4j
@Configuration
public class GlobalTrackingPlatformConfig {

    @Value("${global.tracking.platform.test.api.url}")
    private String globalTrackingPlatformTestApiUrl;

    @Value("${global.tracking.platform.live.api.url}")
    private String globalTrackingPlatformLiveApiUrl;

    @Value("${global.tracking.platform.devices}")
    private String globalTtrackingDevicesString;

    @Bean(name = "globalTrackingPlatformApiClient")
    public RestClient globalTrackingPlatformApiClient(){
        final String apiUrl = globalTrackingPlatformTestApiUrl;
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "globalTrackingPlatformDevices")
    public Set<Integer> globalTrackingPlatformDevices(){
        return Utilities.createSetFromCsvString(globalTtrackingDevicesString);
    }

    @Bean
    @ConditionalOnProperty(value = "global.tracking.platform.send.data")
    public GlobalTrackingPlatformScheduledTask globalTrackingPlatformScheduledTask(){
        return new GlobalTrackingPlatformScheduledTask();
    }

}
