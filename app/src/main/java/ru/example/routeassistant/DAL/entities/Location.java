package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "locations")
public class Location {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private double longitude;
    private double latitude;
    @SerializedName("location_time")
    private String time;

    public Location(long id, double latitude, double longitude, String time) {
        this.id = id;
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setTime(time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static List<Location> getTest() {
        List<Location> l = new ArrayList<>();
        l.add(new Location(1, 15, 15, "2018-03-27 00:00:00"));
        l.add(new Location(2, 17, 15, "2018-03-27 00:00:00"));
        return l;
    }
}
