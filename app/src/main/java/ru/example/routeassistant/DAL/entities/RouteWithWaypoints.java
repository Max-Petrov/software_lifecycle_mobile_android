package ru.kenguru.driverassistant.DAL.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.List;

import ru.kenguru.driverassistant.DAL.SQLRepository;

public class RouteWithWaypoints {
    @Embedded
    private Route route;

    @Relation(parentColumn = "id", entityColumn = "route_job_id")
    private List<Waypoint> waypoints;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
