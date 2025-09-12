package com.ftn.dan.airhermes.model.entity;

public class Location {
    private Long id;
    private String city, state, continent;
    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Location(Long location_id_departure, String location_city_departure, String location_state_departure, String location_continent_departure) {
        this.id = location_id_departure;
        this.city = location_city_departure;
        this.state = location_state_departure;
        this.continent = location_continent_departure;
//        todo
        this.imagePath = null;
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return 29 + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", continent='" + continent + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
