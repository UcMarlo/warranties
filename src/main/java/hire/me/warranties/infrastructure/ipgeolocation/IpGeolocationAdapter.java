package hire.me.warranties.infrastructure.ipgeolocation;

import hire.me.warranties.domain.geolocation.GeolocationData;
import hire.me.warranties.domain.geolocation.IpGeolocationPort;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("IpGeolocationService")
@ConditionalOnProperty(name = "infra.geolocation.enabled", havingValue = "true")
public class IpGeolocationAdapter implements IpGeolocationPort {
    @Override
    public GeolocationData findCountryByIP(String ip) {
        throw new NotImplementedException();
    }
}
