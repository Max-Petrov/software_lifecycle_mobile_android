package ru.kenguru.driverassistant.controllers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kenguru.driverassistant.DAL.SQLRepository;
import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.remoteDAO.RetrofitProvider;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class LocationController implements ILocationController {

    private static LocationController instance = null;

    private LocationController() {

    }

    public static void initInstance() {
        if (instance == null) {
            instance = new LocationController();
        }
    }

    public static LocationController getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    @Override
    public void changePeriodSettings(long period) {
        PreferencesEditor editor = PreferencesEditor.getInstance();
        editor.writeLocationTimePeriod(period);
    }

    @Override
    public long getPeriodSettings() {
        PreferencesEditor editor = PreferencesEditor.getInstance();
        return editor.readLocationTimePeriod();
    }

    @Override
    public Location getCurrentLocation() {
        return new Location(1, 56.971767, 41.012539, "fdfrdf");
    }

    @Override
    public void sendLocation(final Location location) {
        RetrofitProvider retrofitProvider = RetrofitProvider.getInstance();
        Call<Void> response = retrofitProvider.getLocationAPI().sendLocation(location, DriverAssistantApp.getDeviceId());
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                return;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                location.setId(-1);
                SQLRepository.getInstance().getDatabase().getLocationLocalDao().insertLocation(location);
            }
        });
    }
}
