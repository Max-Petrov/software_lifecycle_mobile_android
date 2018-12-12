package ru.kenguru.driverassistant.DAL.remoteDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.kenguru.driverassistant.DAL.entities.Location;

public interface ILocationApi {
    @POST("device_locations")
    Call<Void> sendLocation(@Body Location location, @Query("device_id") long deviceId);
    @POST("device_locations?list=true")
    Call<Void> sendLocations(@Body List<Location> locations, @Query("device_id") long deviceId);
}
