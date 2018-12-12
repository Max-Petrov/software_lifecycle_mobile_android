package ru.kenguru.driverassistant.DAL.remoteDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;

public interface IWaypointApi {
    @GET("waypoints")
    Call<List<Waypoint>> loadWaypointByRouteId(@Query("route_job_id") long routeId, @Query("device_id") long deviceId);
    @PUT("waypoints/{id]")
    Call<Void> updateWaypoint(@Body Waypoint waypoint, @Path("id") long waypointId, @Query("device_id") long deviceId);
}