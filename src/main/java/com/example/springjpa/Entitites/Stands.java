package com.example.springjpa.Entitites;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stands")
public class Stands {

    @Id
    @Column(nullable = false, unique = true, length = 45)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double[] getCoords(){
        double[] coords = {longitude, latitude};
        return coords;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stands stands = (Stands) o;
        return Objects.equals(name, stands.name) &&
                Objects.equals(latitude, stands.latitude) &&
                Objects.equals(longitude, stands.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Stands{" +
                "standName='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}