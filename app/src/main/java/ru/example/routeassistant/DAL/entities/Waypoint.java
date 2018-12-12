package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "waypoints",
        foreignKeys = { @ForeignKey(entity = Route.class, parentColumns = "id", childColumns = "route_job_id") })
public class Waypoint {
    @PrimaryKey
    private long id;
    @SerializedName("point_address")
    @ColumnInfo(name = "point_address")
    private String pointAddress;
    @SerializedName("point_latitude")
    @ColumnInfo(name = "point_latitude")
    private double pointLatitude;
    @SerializedName("point_longitude")
    @ColumnInfo(name = "point_longitude")
    private double pointLongitude;
    private String cargo;
    @SerializedName("time_stop_expected")
    @ColumnInfo(name = "time_stop_expected")
    private int stopMinutes;
    @SerializedName("arrive_time_plan")
    @ColumnInfo(name = "arrive_time_plan")
    private String arriveTimePlan;
    @SerializedName("arrive_time_fact")
    @ColumnInfo(name = "arrive_time_fact")
    private String arriveTimeFact;
    @SerializedName("leave_time_plan")
    @ColumnInfo(name = "leave_time_plan")
    private String leaveTimePlan;
    @SerializedName("leave_time_fact")
    @ColumnInfo(name = "leave_time_fact")
    private String leaveTimeFact;
    @SerializedName("num_seq")
    @ColumnInfo(name = "num_seq")
    private int sequenceNumber;
    @NonNull
    @SerializedName("route_job_id")
    @ColumnInfo(name = "route_job_id")
    private long routeId;
    @NonNull
    @SerializedName("waypoint_type_id")
    @ColumnInfo(name = "waypoint_type_id")
    private long typeId;
    @NonNull
    @SerializedName("waypoint_status_id")
    @ColumnInfo(name = "waypoint_status_id")
    private long statusId;

    public Waypoint(long id, long routeId, String pointAddress, double pointLatitude, double pointLongitude, String cargo, int stopMinutes, String arriveTimePlan, String arriveTimeFact,
                    String leaveTimePlan, String leaveTimeFact, int sequenceNumber, long typeId, long statusId) {
        this.id = id;
        this.routeId = routeId;
        this.pointAddress = pointAddress;
        this.pointLatitude = pointLatitude;
        this.pointLongitude = pointLongitude;
        this.cargo = cargo;
        this.stopMinutes = stopMinutes;
        this.arriveTimePlan = arriveTimePlan;
        this.arriveTimeFact = arriveTimeFact;
        this.leaveTimePlan = leaveTimePlan;
        this.leaveTimeFact = leaveTimeFact;
        this.sequenceNumber = sequenceNumber;
        this.typeId = typeId;
        this.statusId = statusId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getPointAddress() {
        return pointAddress;
    }

    public void setPointAddress(String pointAddress) {
        this.pointAddress = pointAddress;
    }

    public double getPointLatitude() {
        return pointLatitude;
    }

    public void setPointLatitude(double pointLatitude) {
        this.pointLatitude = pointLatitude;
    }

    public double getPointLongitude() {
        return pointLongitude;
    }

    public void setPointLongitude(double pointLongitude) {
        this.pointLongitude = pointLongitude;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getStopMinutes() {
        return stopMinutes;
    }

    public void setStopMinutes(int stopMinutes) {
        this.stopMinutes = stopMinutes;
    }

    public String getArriveTimePlan() {
        return arriveTimePlan;
    }

    public void setArriveTimePlan(String arriveTimePlan) {
        this.arriveTimePlan = arriveTimePlan;
    }

    public String getArriveTimeFact() {
        return arriveTimeFact;
    }

    public void setArriveTimeFact(String arriveTimeFact) {
        this.arriveTimeFact = arriveTimeFact;
    }

    public String getLeaveTimePlan() {
        return leaveTimePlan;
    }

    public void setLeaveTimePlan(String leaveTimePlan) {
        this.leaveTimePlan = leaveTimePlan;
    }

    public String getLeaveTimeFact() {
        return leaveTimeFact;
    }

    public void setLeaveTimeFact(String leaveTimeFact) {
        this.leaveTimeFact = leaveTimeFact;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public static List<Waypoint> getWaypoints() {
        List<Waypoint> e = new ArrayList<>();
        e.add(new Waypoint(1, 1, "г. Иваново, ул. Красных Зорь, 36", 57.003784, 40.920857,
                "Обои, тес", 10,
                "2018-03-27 00:00:00", "2018-03-27 00:00:00", "2018-03-27 00:00:00",
                "2018-03-27 00:00:00", 1, 1, 1));
        e.add(new Waypoint(2, 1,"г. Иваново, ул. Куконковых, 104", 56.965918, 41.018989,
                "Обои", 15,
                "2018-03-27 00:00:00", "2018-03-27 00:00:00", "2018-03-27 00:00:00",
                "2018-03-27 00:00:00", 2, 2, 1));
        e.add(new Waypoint(3, 1,"г. Иваново, проспект Текстильщиков, 30", 56.957413, 41.001903,
                "тес", 15,
                "2018-03-27 00:00:00", "2018-03-27 00:00:00", "2018-03-27 00:00:00",
                "2018-03-27 00:00:00", 3, 2, 1));
        return e;
    }
}
