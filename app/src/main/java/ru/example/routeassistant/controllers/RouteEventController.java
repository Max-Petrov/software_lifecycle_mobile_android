package ru.kenguru.driverassistant.controllers;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kenguru.driverassistant.DAL.SQLRepository;
import ru.kenguru.driverassistant.DAL.entities.RouteEvent;
import ru.kenguru.driverassistant.DAL.remoteDAO.IRouteEventApi;
import ru.kenguru.driverassistant.DAL.remoteDAO.RetrofitProvider;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.others.DateController;

public class RouteEventController implements IRouteEventController {

    private static RouteEventController instance = null;

    private RouteEventController() {

    }

    public static void initInstance() {
        if (instance == null) {
            instance = new RouteEventController();
        }
    }

    public static RouteEventController getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    private RouteEvent createEvent(long eventTypeId, long waypointId) {
        Calendar calendar = Calendar.getInstance();
        DateController dateController = new DateController();
        String eventTime = dateController.format(calendar.getTime());
        return new RouteEvent(-1, eventTime, eventTypeId, waypointId);
    }

    @Override
    public void sendEvent(long eventTypeId, long waypointId) {
        final RouteEvent event = createEvent(eventTypeId, waypointId);
        IRouteEventApi routeEventApi = RetrofitProvider.getInstance().getRouteEventApi();
        Call<Void> request = routeEventApi.sendEvent(event, DriverAssistantApp.getDeviceId());
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                return;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    event.setId(-1);
                    SQLRepository.getInstance().getDatabase().getEventLocalDao().insertEvent(event);
                } catch (Exception e) {

                }

            }
        });
    }
}
