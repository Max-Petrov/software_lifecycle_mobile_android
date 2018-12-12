package ru.kenguru.driverassistant.DAL.localDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Waypoint;

@Dao
public interface IWaypointLocalDao {
    @Query("SELECT * FROM waypoints")
    List<Waypoint> getWaypoints();
    @Query("SELECT * FROM waypoints WHERE route_job_id = :id ORDER BY num_seq ASC")
    List<Waypoint> getWaypointsByRouteId(long id);
    @Insert
    long[] insertWaypoints(List<Waypoint> waypoints);
    @Update
    int updateWaypoint(Waypoint waypoint);
}
