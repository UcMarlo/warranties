package hire.me.warranties.infrastructure.ipgeolocation.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IpGeolocationConfiguration {

    @Bean
    public RestTemplate ipGeolocationRestTemplate(){
        return new RestTemplate();
    }

}
