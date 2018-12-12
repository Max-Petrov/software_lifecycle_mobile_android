package ru.kenguru.driverassistant.DAL.remoteDAO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class RetrofitProvider {
    private static RetrofitProvider instance = null;

    private final String BASE_URL = "http://192.168.1.114/api/";

    private Retrofit retrofit;

    private ILocationApi locationAPI;
    private IRouteApi routeApi;
    private IRouteEventApi routeEventApi;
    private IWaypointApi waypointApi;

    private RetrofitProvider() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        locationAPI = retrofit.create(ILocationApi.class);
        routeApi = retrofit.create(IRouteApi.class);
        routeEventApi = retrofit.create(IRouteEventApi.class);
        waypointApi = retrofit.create(IWaypointApi.class);
    }

    public static void initInstance() {
        if (instance == null) {
            instance = new RetrofitProvider();
        }
    }

    public static RetrofitProvider getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    public ILocationApi getLocationAPI() {
        return locationAPI;
    }

    public IRouteApi getRouteApi() {
        return routeApi;
    }

    public IRouteEventApi getRouteEventApi() {
        return routeEventApi;
    }

    public IWaypointApi getWaypointApi() {
        return waypointApi;
    }
}
