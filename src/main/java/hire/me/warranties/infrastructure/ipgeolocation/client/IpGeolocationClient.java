package hire.me.warranties.infrastructure.ipgeolocation.client;

import hire.me.warranties.infrastructure.ipgeolocation.client.dto.IpGeolocationApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class IpGeolocationClient {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public IpGeolocationClient(
            RestTemplate restTemplate,
            @Value("${infra.geolocation.api.url}") String apiUrl,
            @Value("${infra.geolocation.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public IpGeolocationApiResponse findGeolocationDataByIp(String ip){
        String url = String.format("%s?apiKey=%s&ip=%s", apiUrl, apiKey, ip);
        try {
            return restTemplate.getForObject(url, IpGeolocationApiResponse.class);
        } catch (RestClientException exception){
            log.error("A IpGeolocation client error occured: ", exception);
            throw exception;
        }
    }

}
