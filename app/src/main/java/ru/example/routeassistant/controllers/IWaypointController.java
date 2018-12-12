package ru.kenguru.driverassistant.controllers;

import com.yandex.mapkit.geometry.Point;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Waypoint;

public interface IWaypointController {
    void leave(Waypoint waypoint);
    void arrive(Waypoint waypoint);
    void successFinish(Waypoint waypoint);
    void noSuccessFinish(Waypoint waypoint);
}
