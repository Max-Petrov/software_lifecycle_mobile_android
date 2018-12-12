package ru.kenguru.driverassistant.DAL.remoteDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.kenguru.driverassistant.DAL.entities.RouteEvent;

public interface IRouteEventApi {
    @POST("route_events")
    Call<Void> sendEvent(@Body RouteEvent routeEvent, @Query("device_id") long deviceId);
    @POST("route_events?list=true")
    Call<Void> sendEvents(@Body List<RouteEvent> routeEvents, @Query("device_id") long deviceId);
}
