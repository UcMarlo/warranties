package hire.me.warranties.infrastructure.ipgeolocation;

import hire.me.warranties.domain.geolocation.GeolocationData;
import hire.me.warranties.domain.geolocation.IpGeolocationPort;

import hire.me.warranties.infrastructure.ipgeolocation.client.IpGeolocationClient;
import hire.me.warranties.infrastructure.ipgeolocation.client.dto.IpGeolocationApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("IpGeolocationService")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "infra.geolocation.enabled", havingValue = "true")
public class IpGeolocationAdapter implements IpGeolocationPort {

    private final IpGeolocationClient ipGeolocationClient;

    @Override
    public GeolocationData getGeolocationDataByIp(String ip) {
        IpGeolocationApiResponse response = ipGeolocationClient.findGeolocationDataByIp(ip);
        return new GeolocationData(response.getCountry());
    }
}
