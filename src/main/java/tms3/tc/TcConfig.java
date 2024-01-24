package tms3.tc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class TcConfig {

    @Value("${tc.api.url}")
    private String tcApiUrl;

    @Value("${tc.api.username}")
    private String tcApiUsername;

    @Value("${tc.api.password}")
    private String tcApiPassword;

    @Bean(name = "tcApiClient")
    public WebClient tcApiClient(){
        return WebClient.builder()
                .baseUrl(tcApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(tcApiUsername, tcApiPassword))
                .build();
    }

    @Bean
    public PositionsStore positionsStore(){
        return PositionsStore.getInstance();
    }

    @Bean
    public DeviceStore deviceStore(){
        return DeviceStore.getInstance();
    }
}
