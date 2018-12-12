package ru.kenguru.driverassistant.DAL.localDAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Route;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;

@Dao
public interface IRouteLocalDao {
    @Query("SELECT * FROM routes")
    List<Route> getRoutes();
    @Query("SELECT * FROM routes")
    List<RouteWithWaypoints> getRoutesWithWaypoints();
    @Query("SELECT * FROM routes WHERE job_status_id = 1 OR job_status_id = 2 ORDER BY job_status_id DESC LIMIT 1")
    RouteWithWaypoints getCurrentRoute();
    @Query("SELECT COUNT(id) FROM routes WHERE job_status_id = 1 OR job_status_id = 2")
    long getCountNonCompletedRoutes();
    @Insert
    long insertRoute(Route route);
    @Update
    int updateRoute(Route route);
    @Query("UPDATE routes SET job_status_id = :status_id WHERE id = :route_id")
    int changeStatusOfRoute(long route_id, long status_id);
    @Delete
    void deleteRoute(Route route);
}
