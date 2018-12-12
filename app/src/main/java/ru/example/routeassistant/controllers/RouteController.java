package ru.kenguru.driverassistant.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kenguru.driverassistant.DAL.IDatabase;
import ru.kenguru.driverassistant.DAL.entities.Route;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DAL.localDAO.IRouteLocalDao;
import ru.kenguru.driverassistant.DAL.SQLDatabase;
import ru.kenguru.driverassistant.DAL.SQLRepository;
import ru.kenguru.driverassistant.DAL.entities.RouteStatus;
import ru.kenguru.driverassistant.DAL.remoteDAO.IRouteApi;
import ru.kenguru.driverassistant.DAL.remoteDAO.RetrofitProvider;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.others.DateController;

public class RouteController implements IRouteController {

    public static final int NO_ROUTE_STATE = 1;
    public static final int START_ROUTE_STATE = 2;
    public static final int ARRIVE_POINT_STATE = 3;
    public static final int LEAVE_POINT_STATE = 4;
    public static final int FINISH_ROUTE_STATE = 5;

    private IRouteLocalDao routeDao;
    private IRouteApi routeApi;
    private RouteWithWaypoints currentRoute = null;

    private static RouteController instance = null;

    private List<IRouteControllerEventListener> listeners = null;

    private RouteController() {
        SQLDatabase db = SQLRepository.getInstance().getDatabase();
        routeDao = db.getRouteLocalDao();
        currentRoute= routeDao.getCurrentRoute();
        routeApi = RetrofitProvider.getInstance().getRouteApi();
    }

    public static void initInstance() {
        if (instance == null) {
            instance = new RouteController();
        }
    }

    public static RouteController getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    private RouteStatus getStatusByTimeDif(Date timePlan, Date timeFact) {
        DateController dateController = new DateController();
        long dif = dateController.getDifDate(timeFact, timePlan);
        if (dif <= 60 * 60 * 10000) {
            return RouteStatus.getRouteStatusById(3);
        }
        else {
            return RouteStatus.getRouteStatusById(4);
        }
    }

    private RouteStatus getStatusByTimeDif(String timePlanString, String timeFactString) {
        DateController dateController = new DateController();
        Date timePlan = dateController.parseString(timePlanString);
        Date timeFact = dateController.parseString(timeFactString);
        return getStatusByTimeDif(timePlan, timeFact);
    }

    @Override
    public void addRouteControllerEventListener(IRouteControllerEventListener listener) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(listener);
    }

    @Override
    public RouteWithWaypoints loadCurrentRouteFromLocalRepository() {
        currentRoute = routeDao.getCurrentRoute();
        return currentRoute;
    }

    @Override
    public int getStatment() {
        if (currentRoute == null) return NO_ROUTE_STATE;
        else if (currentRoute.getRoute().getStartTimeFact() == null) return START_ROUTE_STATE;
        else if (getCurrentWaypoint() == null) return FINISH_ROUTE_STATE;
        else if (getCurrentWaypoint().getArriveTimeFact() == null) return ARRIVE_POINT_STATE;
        else return LEAVE_POINT_STATE;
    }

    @Override
    public boolean nonCompletedRouteIsExist() {
        long count = routeDao.getCountNonCompletedRoutes();
        return count > 0;
    }

    @Override
    public RouteWithWaypoints getCurrentRoute() {
        if (currentRoute == null) loadCurrentRouteFromLocalRepository();
        return currentRoute;
    }

    @Override
    public int changeRouteStatus(long id, RouteStatus status) {
        int result = routeDao.changeStatusOfRoute(id, status.getId());
        return result;
    }

    @Override
    public void startRoute() {
        if (currentRoute != null) {
            Route route = currentRoute.getRoute();
            DateController dateController = new DateController();
            Calendar calendar = Calendar.getInstance();
            String timeFactString = dateController.format(calendar.getTime());
            route.setStartTimePlan(timeFactString);
            route.setStartTimeFact(timeFactString);
            route.setStatusId(2);
            routeDao.updateRoute(route);

            RouteEventController.getInstance().sendEvent(1, getCurrentWaypoint().getId());
        }
    }

    @Override
    public void finishRoute() {
        if (currentRoute != null) {
            Route route = currentRoute.getRoute();
            DateController dateController = new DateController();
            Calendar calendar = Calendar.getInstance();
            String timeFactString = dateController.format(calendar.getTime());
            route.setFinishTimeFact(timeFactString);
            route.setStatusId(getStatusByTimeDif(route.getFinishTimePlan(), timeFactString).getId());
            routeDao.updateRoute(route);
            //routeDao.deleteRoute(route);

            RouteEventController.getInstance().sendEvent(2, currentRoute.getWaypoints().get(currentRoute.getWaypoints().size() - 1).getId());
            currentRoute = null;
        }
    }

    @Override
    public void successFinish() {
        if (currentRoute != null) {
            currentRoute.getRoute().setFinishTimeFact(currentRoute.getRoute().getFinishTimePlan());
            currentRoute.getRoute().setStatusId(2);

            for (Waypoint w :
                    currentRoute.getWaypoints()) {

            }

        }
    }

    @Override
    public void noSuccessFinish() {
        if (currentRoute != null) {
            Route route = currentRoute.getRoute();
            DateController dateController = new DateController();
            Calendar calendar = Calendar.getInstance();
            String timeFactString = dateController.format(calendar.getTime());
            route.setFinishTimeFact(timeFactString);
            route.setStatusId(4);
            routeDao.updateRoute(route);

            //RouteEventController.getInstance().sendEvent(7, getCurrentWaypoint().getId());
        }
    }

    @Override
    public Waypoint getCurrentWaypoint() {
        for (int i = 0; i < currentRoute.getWaypoints().size(); i++) {
            if (currentRoute.getWaypoints().get(i).getStatusId() == 2) return currentRoute.getWaypoints().get(i);
        }
        return null;
    }

    @Override
    public void loadNewRoute() {
        RetrofitProvider retrofitProvider = RetrofitProvider.getInstance();
        Call<RouteWithWaypoints> route = routeApi.loadNewRoute(DriverAssistantApp.getDeviceId());
        route.enqueue(new Callback<RouteWithWaypoints>() {
            @Override
            public void onResponse(Call<RouteWithWaypoints> call, Response<RouteWithWaypoints> response) {
                RouteWithWaypoints r = response.body();
                if (r == null) {
                    for (IRouteControllerEventListener l :
                            listeners) {
                        l.onNoRoute();
                    }
                }
                else {
                    for (IRouteControllerEventListener l :
                            listeners) {
                        IDatabase db = SQLRepository.getInstance().getDatabase();
                        db.getRouteLocalDao().insertRoute(r.getRoute());
                        db.getWaypointLocalDao().insertWaypoints(r.getWaypoints());
                        currentRoute = r;
                        l.onLoadRoute(r);
                    }
                }
            }

            @Override
            public void onFailure(Call<RouteWithWaypoints> call, Throwable t) {
                for (IRouteControllerEventListener l :
                        listeners) {
                    l.onErrorLoad();
                }
            }
        });
    }
}
