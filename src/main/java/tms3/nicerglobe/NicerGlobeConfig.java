package tms3.nicerglobe;


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
public class NicerGlobeConfig {

    @Value("${nicer.globe.api.url}")
    private String nicerGlobeApiUrl;

    @Value("${nicer.globe.devices}")
    private String nicerGlobeDevicesString;

    @Bean(name = "nicerGlobeApiClient")
    public RestClient nicerGlobeApiClient(){
        return RestClient.builder()
                .baseUrl(nicerGlobeApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "nicerGlobeDevices")
    public Set<Integer> nicerGlobeDevices(){
        return Utilities.createSetFromCsvString(nicerGlobeDevicesString);
    }

    @Bean
    @ConditionalOnProperty(value = "nicer.globe.send.data")
    public NicerGlobeScheduledTask nicerGlobeScheduledTask(){
        return new NicerGlobeScheduledTask();
    }

}
