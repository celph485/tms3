package tms3.fretron;

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
public class FreTronConfig {
    @Value("${fre.tron.api.url}")
    private String freTronApiUrl;

    @Value("${fre.tron.devices}")
    private String freTronDevicesString;

    @Bean(name = "freTronApiClient")
    public RestClient freTronApiClient(){
        return RestClient.builder()
                .baseUrl(freTronApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "freTronDevices")
    public Set<Integer> freTronDevices(){
        return Utilities.createSetFromCsvString(freTronDevicesString);
    }

    @Bean
    @ConditionalOnProperty(value = "fre.tron.send.data")
    public FreTronScheduledTask freTronScheduledTask(){
        return new FreTronScheduledTask();
    }

}
