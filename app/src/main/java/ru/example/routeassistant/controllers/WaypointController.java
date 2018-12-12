package ru.kenguru.driverassistant.controllers;

import android.content.Context;

import com.yandex.mapkit.geometry.Point;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.kenguru.driverassistant.DAL.SQLRepository;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DAL.entities.WaypointStatus;
import ru.kenguru.driverassistant.DAL.localDAO.IWaypointLocalDao;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.others.DateController;

public class WaypointController implements IWaypointController {

    private static WaypointController instance = null;

    private WaypointController() {

    }

    public static void initInstance() {
        if (instance == null) {
            instance = new WaypointController();
        }
    }

    public static WaypointController getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    private WaypointStatus getStatusByTimeDif(Date timePlan, Date timeFact) {
        DateController dateController = new DateController();
        long dif = dateController.getDifDate(timeFact, timePlan);
        if (dif <= 60 * 60 * 10000) {
            return WaypointStatus.getStatuses().get(1);
        }
        else {
            return WaypointStatus.getStatuses().get(2);
        }
    }

    private WaypointStatus getStatusByTimeDif(String timePlanString, String timeFactString) {
        DateController dateController = new DateController();
        Date timePlan = dateController.parseString(timePlanString);
        Date timeFact = dateController.parseString(timeFactString);
        return getStatusByTimeDif(timePlan, timeFact);
    }

    @Override
    public void leave(Waypoint waypoint) {
        if (waypoint != null) {
            DateController dateController = new DateController();
            Calendar calendar = Calendar.getInstance();
            String timeFactString = dateController.format(calendar.getTime());
            waypoint.setLeaveTimeFact(timeFactString);
            waypoint.setStatusId(getStatusByTimeDif(waypoint.getLeaveTimePlan(), timeFactString).getId());
            IWaypointLocalDao waypointLocalDao = SQLRepository.getInstance().getDatabase().getWaypointLocalDao();
            waypointLocalDao.updateWaypoint(waypoint);

            RouteEventController.getInstance().sendEvent(4, waypoint.getId());
        }
    }

    @Override
    public void arrive(Waypoint waypoint) {
        if (waypoint != null) {
            DateController dateController = new DateController();
            Calendar calendar = Calendar.getInstance();
            String timeFactString = dateController.format(calendar.getTime());
            waypoint.setArriveTimeFact(timeFactString);
            IWaypointLocalDao waypointLocalDao = SQLRepository.getInstance().getDatabase().getWaypointLocalDao();
            waypointLocalDao.updateWaypoint(waypoint);

            RouteEventController.getInstance().sendEvent(3, waypoint.getId());
        }
    }

    @Override
    public void successFinish(Waypoint waypoint) {
        if (waypoint.getStatusId() == 1) {

        }

    }

    @Override
    public void noSuccessFinish(Waypoint waypoint) {

    }

    //    @Override
//    public Point getPoint(String address) {
//        if (address.equals("г. Иваново, ул. Красных Зорь, 36"))
//            return new Point(57.003784, 40.920857);
//        if (address.equals("г. Иваново, ул. Куконковых, 104"))
//            return new Point(56.965918, 41.018989);
//        if (address.equals("г. Иваново, проспект Текстильщиков, 30"))
//            return new Point(56.957413, 41.001903);
//        return null;
//    }
}
