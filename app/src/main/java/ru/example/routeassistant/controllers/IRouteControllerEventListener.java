package ru.kenguru.driverassistant.controllers;

import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;

public interface IRouteControllerEventListener {
    void onLoadRoute(RouteWithWaypoints route);
    void onNoRoute();
    void onErrorLoad();
}
