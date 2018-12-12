package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "route_events",
        foreignKeys = { @ForeignKey(entity = Waypoint.class, parentColumns = "id", childColumns = "waypoint_id") })
public class RouteEvent {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName("event_time")
    @ColumnInfo(name = "event_time")
    private String time;
    @NonNull
    @SerializedName("route_event_type_id")
    @ColumnInfo(name = "type_id")
    private long typeId;
    @NonNull
    @SerializedName("waypoint_id")
    @ColumnInfo(name = "waypoint_id")
    private long waypointId;

    public RouteEvent(long id, String time, long typeId, long waypointId) {
        this.id = id;
        this.time = time;
        this.typeId = typeId;
        this.waypointId = waypointId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getWaypointId() {
        return waypointId;
    }

    public void setWaypointId(long waypointId) {
        this.waypointId = waypointId;
    }

    public static List<RouteEvent> getEvents() {
        List<RouteEvent> e = new ArrayList<>();
        e.add(new RouteEvent(1, "2018-03-27 00:00:00", 1, 1));
        e.add(new RouteEvent(1, "2018-03-27 00:00:00", 2, 1));
        e.add(new RouteEvent(2, "2018-03-27 00:00:00", 1, 2));
        return e;
    }
}