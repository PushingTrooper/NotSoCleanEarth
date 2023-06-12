package al.fshn.notsocleanearth.data.model;

public class City {
    private final String name;
    private final Double latitude;
    private final Double longitude;
    private final String country;

    public City(String name, Double latitude, Double longitude, String country) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }
}
