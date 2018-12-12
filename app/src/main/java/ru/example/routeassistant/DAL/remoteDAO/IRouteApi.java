package ru.kenguru.driverassistant.DAL.remoteDAO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.kenguru.driverassistant.DAL.entities.Route;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;

public interface IRouteApi {
    @GET("route_jobs?current=true&with_waypoints=true")
    Call<RouteWithWaypoints> loadNewRoute(@Query("device_id") long deviceId);
    @PUT("route_jobs/{id}")
    Call<Void> updateRoute(@Body Route route, @Path("id") long routeId, @Query("device_id") long deviceId);
}
