package hire.me.warranties.domain.geolocation;

public interface IpGeolocationPort {
    GeolocationData getGeolocationDataByIp(String ip);
}
