package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "routes")
public class Route {
    @PrimaryKey
    private long id;
    @SerializedName("start_time_plan")
    @ColumnInfo(name = "start_time_plan")
    private String startTimePlan;
    @SerializedName("start_time_fact")
    @ColumnInfo(name = "start_time_fact")
    private String startTimeFact;
    @SerializedName("finish_time_plan")
    @ColumnInfo(name = "finish_time_plan")
    private String finishTimePlan;
    @SerializedName("finish_time_fact")
    @ColumnInfo(name = "finish_time_fact")
    private String finishTimeFact;
    @SerializedName("total_distance")
    @ColumnInfo(name = "total_distance")
    private float totalDistance;
    @SerializedName("start_point_latitude")
    @ColumnInfo(name = "start_point_latitude")
    private double startPointLat;
    @SerializedName("start_point_longitude")
    @ColumnInfo(name = "start_point_longitude")
    private double startPointLon;

    @SerializedName("finish_point_address")
    private String finishPointAddress;
    @SerializedName("finish_point_latitude")
    private double finishPointLatitude;
    @SerializedName("finish_point_longitude")
    private double finishPointLongitude;

    @NonNull
    @SerializedName("job_status_id")
    @ColumnInfo(name = "job_status_id")
    private long statusId;

    public Route(long id, String startTimePlan, String startTimeFact, String finishTimePlan, String finishTimeFact,
                 float totalDistance, long statusId, double startPointLat, double startPointLon,
                 String finishPointAddress, double finishPointLatitude, double finishPointLongitude) {
        this.id = id;
        this.startTimePlan = startTimePlan;
        this.startTimeFact = startTimeFact;
        this.finishTimePlan = finishTimePlan;
        this.finishTimeFact = finishTimeFact;
        this.totalDistance = totalDistance;
        this.statusId = statusId;
        this.startPointLat = startPointLat;
        this.startPointLon = startPointLon;
        this.finishPointAddress = finishPointAddress;
        this.finishPointLatitude = finishPointLatitude;
        this.finishPointLongitude = finishPointLongitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartTimePlan() {
        return startTimePlan;
    }

    public void setStartTimePlan(String startTimePlan) {
        this.startTimePlan = startTimePlan;
    }

    public String getStartTimeFact() {
        return startTimeFact;
    }

    public void setStartTimeFact(String startTimeFact) {
        this.startTimeFact = startTimeFact;
    }

    public String getFinishTimePlan() {
        return finishTimePlan;
    }

    public void setFinishTimePlan(String finishTimePlan) {
        this.finishTimePlan = finishTimePlan;
    }

    public String getFinishTimeFact() {
        return finishTimeFact;
    }

    public void setFinishTimeFact(String finishTimeFact) {
        this.finishTimeFact = finishTimeFact;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getStartPointLon() {
        return startPointLon;
    }

    public void setStartPointLon(double startPointLon) {
        this.startPointLon = startPointLon;
    }

    public String getFinishPointAddress() {
        return finishPointAddress;
    }

    public void setFinishPointAddress(String finishPointAddress) {
        this.finishPointAddress = finishPointAddress;
    }

    public double getFinishPointLatitude() {
        return finishPointLatitude;
    }

    public void setFinishPointLatitude(double finishPointLatitude) {
        this.finishPointLatitude = finishPointLatitude;
    }

    public double getFinishPointLongitude() {
        return finishPointLongitude;
    }

    public void setFinishPointLongitude(double finishPointLongitude) {
        this.finishPointLongitude = finishPointLongitude;
    }

    public static List<Route> getRoutes() {
        List<Route> e = new ArrayList<>();
        e.add(new Route(1, "2018-03-27 00:00:00", "2018-03-27 00:00:00",
                "2018-03-27 00:00:00", "2018-03-27 00:00:00",
                50, 1, 57.003784, 40.920857,
                "г. Иваново, ул. Красных Зорь, 36",57.003784, 40.920857));
        return e;
    }
}
