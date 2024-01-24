package tms3.aditi;

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
public class AditiTrackingConfig {

    @Value("${aditi.tracking.api.url}")
    private String aditiTrackingApiUrl;

    @Value("${aditi.tracking.devices}")
    private String aditiTrackingDevicesString;


    @Bean(name = "aditiTrackingApiClient")
    public RestClient aditiTrackingApiClient(){
        return RestClient.builder()
                .baseUrl(aditiTrackingApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "aditiTrackingDevices")
    public Set<Integer> aditiTrackingDevices(){
        return Utilities.createSetFromCsvString(aditiTrackingDevicesString);
    }


    @Bean
    @ConditionalOnProperty(value = "aditi.tracking.send.data")
    public AditiTrackingScheduledTask aditiTrackingScheduledTask(){
        return new AditiTrackingScheduledTask();
    }
}
