package com.ftn.dan.airhermes.model.entity;

public class Airport {
    private String airportCodeName;
    private Location location;

    public Airport(String airport_code_name_departure, Location locationDeparture) {
        this.airportCodeName = airport_code_name_departure;
        this.location = locationDeparture;
    }

    @Override
    public int hashCode() {
        final int prime = 11;
        int result = 1;
        result = prime*result + ((airportCodeName == null) ? 0 : airportCodeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Airport other = (Airport) obj;
        if (airportCodeName == null) {
            if (other.airportCodeName != null)
                return false;
        } else if (!airportCodeName.equals(other.airportCodeName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportCodeName='" + airportCodeName + '\'' +
                ", location=" + location +
                '}';
    }

    public String getAirportCodeName() {
        return airportCodeName;
    }

    public void setAirportCodeName(String airportCodeName) {
        this.airportCodeName = airportCodeName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
