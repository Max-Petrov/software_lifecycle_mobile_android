package ru.kenguru.driverassistant;

import android.app.Application;

import ru.kenguru.driverassistant.DAL.ILocalRepository;
import ru.kenguru.driverassistant.DAL.SQLRepository;
import ru.kenguru.driverassistant.DAL.remoteDAO.RetrofitProvider;
import ru.kenguru.driverassistant.controllers.ILocationController;
import ru.kenguru.driverassistant.controllers.IRouteController;
import ru.kenguru.driverassistant.controllers.IRouteEventController;
import ru.kenguru.driverassistant.controllers.IWaypointController;
import ru.kenguru.driverassistant.controllers.LocationController;
import ru.kenguru.driverassistant.controllers.RouteController;
import ru.kenguru.driverassistant.controllers.RouteEventController;
import ru.kenguru.driverassistant.controllers.WaypointController;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class DriverAssistantApp extends Application {

    private static long deviceId;
    private static ILocalRepository repository;
    private static RetrofitProvider retrofitProvider;
    private static IRouteController routeController;
    private static IWaypointController waypointController;
    private static IRouteEventController routeEventController;
    private static ILocationController locationController;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesEditor.initInstance(this);
        SQLRepository.initInstance(this);
        LocationController.initInstance();
        RouteController.initInstance();
        RouteEventController.initInstance();
        WaypointController.initInstance();
        RetrofitProvider.initInstance();
        repository = SQLRepository.getInstance();
        routeController = RouteController.getInstance();
        waypointController = WaypointController.getInstance();
        routeEventController = RouteEventController.getInstance();
        locationController = LocationController.getInstance();
        retrofitProvider = RetrofitProvider.getInstance();
        deviceId = PreferencesEditor.getInstance().getDeviceId();
    }

    public static long getDeviceId() {
        return deviceId;
    }

    public static void setDeviceId(long deviceId) {
        DriverAssistantApp.deviceId = deviceId;
    }

    public static ILocalRepository getRepository() {
        return repository;
    }

    public static RetrofitProvider getRetrofitProvider() {
        return retrofitProvider;
    }

    public static IRouteController getRouteController() {
        return routeController;
    }

    public static IWaypointController getWaypointController() {
        return waypointController;
    }

    public static IRouteEventController getRouteEventController() {
        return routeEventController;
    }

    public static ILocationController getLocationController() {
        return locationController;
    }
}
