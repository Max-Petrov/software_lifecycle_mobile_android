package ru.kenguru.driverassistant.controllers;

import ru.kenguru.driverassistant.DAL.entities.RouteStatus;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;

public interface IRouteController {
    void addRouteControllerEventListener(IRouteControllerEventListener listener);
    int getStatment();
    boolean nonCompletedRouteIsExist();
    RouteWithWaypoints getCurrentRoute();
    RouteWithWaypoints loadCurrentRouteFromLocalRepository();
    void loadNewRoute();
    void startRoute();
    void finishRoute();
    void successFinish();
    void noSuccessFinish();
    int changeRouteStatus(long id, RouteStatus status);
    Waypoint getCurrentWaypoint();
}
