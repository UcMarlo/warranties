package hire.me.warranties.domain.geolocation;

public interface IpGeolocationPort {
    GeolocationData findCountryByIP(String ip);
}
