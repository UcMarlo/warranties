package hire.me.warranties.infrastructure.ipgeolocation;

import hire.me.warranties.domain.geolocation.GeolocationData;
import hire.me.warranties.domain.geolocation.IpGeolocationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Slf4j
@ConditionalOnProperty(name = "infra.geolocation.enabled:true", havingValue = "false")
public class IpGeolocationMockService implements IpGeolocationPort {
    @Override
    public GeolocationData findCountryByIP(String ip) {
        log.warn("Geolocation mock is enabled - returning the default value");
        return new GeolocationData("Poland");
    }
}
